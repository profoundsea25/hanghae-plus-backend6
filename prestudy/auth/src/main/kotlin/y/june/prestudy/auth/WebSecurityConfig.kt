package y.june.prestudy.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.intercept.AuthorizationFilter
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.logger
import java.nio.charset.StandardCharsets

@Configuration
class WebSecurityConfig(
    private val jwtValidateFilter: JwtValidateFilter
) {
    private val log = logger()

    companion object {
        val WHITE_LIST: Array<String> = arrayOf(
            "/v**/sign-up",
            "/v**/login",
        )
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*WHITE_LIST).permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtValidateFilter, AuthorizationFilter::class.java)
            .exceptionHandling {
                it
                    .accessDeniedHandler(customAccessDeniedHandler)
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .cors {}
            .headers {
                it
                    .frameOptions { options -> options.disable() }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .logout { it.invalidateHttpSession(true) }
            .build()
    }

    private val customAccessDeniedHandler: AccessDeniedHandler =
        AccessDeniedHandler { _, response, accessDeniedException ->
            log.debug("Forbidden Access", accessDeniedException)
            response.apply {
                this.contentType = MediaType.APPLICATION_JSON_VALUE
                this.status = HttpStatus.FORBIDDEN.value()
                this.characterEncoding = StandardCharsets.UTF_8.name()
                this.writer.write(
                    objectMapper().writeValueAsString(
                        Response<Unit>(responseCode = ResponseCode.FORBIDDEN)
                    )
                )
            }
        }

    private val customAuthenticationEntryPoint: AuthenticationEntryPoint =
        AuthenticationEntryPoint { _, response, authException ->
            log.debug("Unauthorized Access", authException)
            response.apply {
                this.contentType = MediaType.APPLICATION_JSON_VALUE
                this.status = HttpStatus.UNAUTHORIZED.value()
                this.characterEncoding = StandardCharsets.UTF_8.name()
                this.writer.write(
                    objectMapper().writeValueAsString(
                        Response<Unit>(responseCode = ResponseCode.UNAUTHORIZED)
                    )
                )
            }
        }
}
