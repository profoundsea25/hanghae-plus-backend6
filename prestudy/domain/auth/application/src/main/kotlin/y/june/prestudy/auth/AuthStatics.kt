package y.june.prestudy.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.util.AntPathMatcher

const val HEADER_AUTHORIZATION = "Authorization"
const val BEARER_PREFIX = "Bearer "
const val FIELD_USERNAME = "username"

val WHITE_LIST: Array<String> = arrayOf(
    "/v**/sign-up",
    "/v**/login",
)

val antPathMatcher: AntPathMatcher = AntPathMatcher()
val objectMapper: ObjectMapper = ObjectMapper()
    .registerKotlinModule()
