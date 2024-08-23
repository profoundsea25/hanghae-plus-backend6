package y.june.prestudy.auth.api

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import y.june.prestudy.auth.BEARER_PREFIX
import y.june.prestudy.auth.HEADER_AUTHORIZATION
import y.june.prestudy.auth.port.`in`.LoginCommand
import y.june.prestudy.auth.port.`in`.LoginUseCase
import y.june.prestudy.auth.port.`in`.SignUpCommand
import y.june.prestudy.auth.port.`in`.SignUpUseCase
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ok

@RestController
class AuthHttpApiAdapter(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
) {
    @PostMapping("/v1/sign-up")
    fun singUp(@RequestBody command: SignUpCommand): Response<Unit> {
        return ok(signUpUseCase.signUp(command))
    }

    @PostMapping("/v1/login")
    fun login(@RequestBody command: LoginCommand, response: HttpServletResponse): Response<Unit> {
        loginUseCase.login(command)
            .also { response.addHeader(HEADER_AUTHORIZATION, BEARER_PREFIX + it) }
        return ok()
    }
}
