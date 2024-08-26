package y.june.prestudy.auth.fixture

import org.springframework.security.crypto.password.PasswordEncoder
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.model.Role
import y.june.prestudy.auth.port.out.LoadMemberOutPort
import y.june.prestudy.auth.port.out.SaveMemberOutPort
import y.june.prestudy.auth.security.jwt.JwtProvider

const val fakeUsername = "test1"
const val fakePassword = "test1234"
val fakeMember = Member(
    id = 1L,
    username = fakeUsername,
    password = fakePassword,
    role = Role.MEMBER
)


val fakeSaveMemberOutPort: SaveMemberOutPort = object : SaveMemberOutPort {
    override fun save(member: Member) {
        // Do Nothing
    }
}

val fakeLoadMemberOutPort: LoadMemberOutPort = object : LoadMemberOutPort {
    override fun findByUsername(username: String): Member? {
        return if (username == fakeUsername) {
            return fakeMember
        } else {
            null
        }
    }
}

val jwtProviderFixture = JwtProvider(
    "jwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecretjwttestsecret",
    100L
)

val fakePasswordEncoder: PasswordEncoder = object : PasswordEncoder {
    override fun encode(rawPassword: CharSequence?): String {
        return rawPassword.toString()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return rawPassword == encodedPassword
    }
}
