package y.june.prestudy.auth.port.out

import y.june.prestudy.auth.model.Member

interface LoadMemberOutPort {
    fun findByUsername(username: String): Member?
}
