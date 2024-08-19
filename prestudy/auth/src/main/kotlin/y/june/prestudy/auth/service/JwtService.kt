package y.june.prestudy.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import y.june.prestudy.auth.model.Member
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import java.time.ZonedDateTime
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    val secret: String,
    @Value("\${jwt.duration}")
    val duration: Long,
) {
    private val key: SecretKey
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))


    fun createToken(member: Member): String {
        val now: ZonedDateTime = ZonedDateTime.now()
        return Jwts.builder()
            .claims(
                mapOf(
                    "username" to member.username,
                    "password" to member.password,
                    "role" to member.role.name,
                )
            )
            .issuedAt(Date.from(now.toInstant()))
            .expiration(Date.from(now.plusSeconds(duration).toInstant()))
            .signWith(key)
            .compact()
    }

    fun parseClaims(token: String): Claims? {
        return runCatching {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        }
            .onFailure { throw BadRequestException(ResponseCode.INVALID_JWT) }
            .getOrNull()
            ?.payload
    }

    fun getUsername(token: String): String {
        return parseClaims(token)?.get("username") as String? ?: throw BadRequestException(ResponseCode.INVALID_JWT)
    }
}
