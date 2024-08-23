package y.june.prestudy.auth.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import y.june.prestudy.auth.FIELD_USERNAME
import y.june.prestudy.auth.model.Member
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import y.june.prestudy.common.logger
import java.time.ZonedDateTime
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.duration}")
    private val duration: Long,
) {
    private val log = logger()

    private val key: SecretKey
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun generate(
        member: Member,
        now: ZonedDateTime = ZonedDateTime.now(),
    ): String {
        return Jwts.builder()
            .claim(FIELD_USERNAME, member.username)
            .issuedAt(Date.from(now.toInstant()))
            .expiration(Date.from(now.plusSeconds(duration).toInstant()))
            .signWith(key)
            .compact()
    }

    private fun parse(token: String): Claims {
        return runCatching {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        }
            .onFailure { log.info("Error Occurred When JWT Parsing Claims", it) }
            .getOrElse { throw BadRequestException(ResponseCode.INVALID_JWT) }
            .payload
    }

    fun getUsername(token: String): String {
        return parse(token)
            .get(FIELD_USERNAME, String::class.java)
            ?: throw BadRequestException(ResponseCode.INVALID_JWT)
    }

}
