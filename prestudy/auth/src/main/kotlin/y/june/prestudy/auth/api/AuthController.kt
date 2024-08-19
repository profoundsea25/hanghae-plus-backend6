package y.june.prestudy.auth.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import y.june.prestudy.auth.service.AuthService
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ok

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/v1/sign-up")
    fun singUp(@RequestBody command: SignUpCommand): Response<Unit> {
        return ok(authService.singUp(command))
    }

    @PostMapping("/v1/login")
    fun login(@RequestBody command: LoginCommand) {

    }
}
