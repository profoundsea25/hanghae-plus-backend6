package y.june.prestudy.common.api

enum class ResponseCode(
    val code: String,
    val message: String,
) {
    OK("200", "성공하였습니다."),
    BAD_REQUEST("400", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("500", "알 수 없는 에러가 발생하여 처리되지 못하였습니다."),

    DUPLICATED_USERNAME("400", "이미 등록된 username 입니다."),
    UNAUTHORIZED("401", "로그인이 필요합니다."),
    INVALID_JWT("401", "재로그인이 필요합니다."),
    FORBIDDEN("403", "접근할 수 없습니다."),
    NOT_FOUND_USER("400", "가입되지 않은 회원입니다.")
}
