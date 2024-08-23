package y.june.prestudy.auth.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import y.june.prestudy.auth.BEARER_PREFIX
import y.june.prestudy.auth.HEADER_AUTHORIZATION
import y.june.prestudy.auth.WHITE_LIST
import y.june.prestudy.auth.antPathMatcher
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Component
class JwtValidateFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return WHITE_LIST.any { antPathMatcher.match(it, request.requestURI.toString()) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        extractToken(request.getHeader(HEADER_AUTHORIZATION))
            .let { jwtProvider.getUsername(it) }
            .let { userDetailsService.loadUserByUsername(it) }
            .let { UsernamePasswordAuthenticationToken(it, null, it.authorities) }
            .also { SecurityContextHolder.getContext().authentication = it }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(bearerToken: String?): String {
        return validateAuthorizationHeaderStartsWithBearer(bearerToken).removePrefix(BEARER_PREFIX)
    }

    private fun validateAuthorizationHeaderStartsWithBearer(bearerToken: String?): String {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw BadRequestException(ResponseCode.UNAUTHORIZED)
        }
        return bearerToken
    }

}
