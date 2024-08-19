package y.june.prestudy.auth

import org.springframework.util.AntPathMatcher

const val AUTHORIZATION = "Authorization"
const val BEARER = "Bearer "

val WHITE_LIST: Array<String> = arrayOf(
    "/v**/sign-up",
    "/v**/login",
)
val ANT_PATH_MATCHER = AntPathMatcher()
