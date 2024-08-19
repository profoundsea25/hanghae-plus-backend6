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
import y.june.prestudy.common.logger
import java.time.ZonedDateTime
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.duration}")
    private val duration: Long,
) {
    private val log = logger()
    
    companion object {
        private const val FIELD_USERNAME = "username"
    }

    private val key: SecretKey
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))


    fun createToken(member: Member): String {
        val now: ZonedDateTime = ZonedDateTime.now()
        return Jwts.builder()
            .claims(mapOf(FIELD_USERNAME to member.username))
            .issuedAt(Date.from(now.toInstant()))
            .expiration(Date.from(now.plusSeconds(duration).toInstant()))
            .signWith(key)
            .compact()
    }

    fun parseClaimsOrNull(token: String): Claims? {
        return runCatching {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        }
            .onFailure { log.debug("Error Occurred When JWT Parsing Claims", it) }
            .getOrNull()
            ?.payload
    }

    fun extractUsername(claims: Claims?): String {
        return claims?.get(FIELD_USERNAME) as String?
            ?: throw BadRequestException(ResponseCode.INVALID_JWT)
    }
}
