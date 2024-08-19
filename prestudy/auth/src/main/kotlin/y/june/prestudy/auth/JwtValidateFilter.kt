package y.june.prestudy.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import y.june.prestudy.auth.service.JwtService
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Component
class JwtValidateFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return WHITE_LIST.any { ANT_PATH_MATCHER.match(it, request.requestURI.toString()) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader(AUTHORIZATION)
            .also { checkBearerToken(it) }
            .let { it!!.substring(BEARER.length) }
            .let { jwtService.parseClaimsOrNull(it) }
            .let { jwtService.extractUsername(it) }
            .let { userDetailsService.loadUserByUsername(it) }
            .let { UsernamePasswordAuthenticationToken(it, null, it.authorities) }
            .also { SecurityContextHolder.getContext().authentication = it }

        filterChain.doFilter(request, response)
    }

    private fun checkBearerToken(bearerToken: String?) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER)) {
            throw BadRequestException(ResponseCode.INVALID_JWT)
        }
    }

}
