package y.june.prestudy.auth.port.`in`

const val USERNAME_REGEX = "^[a-z0-9]{4,10}$"
const val USERNAME_VIOLATION_MESSAGE = "사용자이름은 4~10자, 알파벳 소문자와 숫자로만 구성할 수 있습니다."

const val PASSWORD_REGEX = "^[a-zA-Z0-9]{8,15}$"
const val PASSWORD_VIOLATION_MESSAGE = "비밀번호는 8~15자, 알파벳 대소문자와 숫자로만 구성할 수 있습니다."
