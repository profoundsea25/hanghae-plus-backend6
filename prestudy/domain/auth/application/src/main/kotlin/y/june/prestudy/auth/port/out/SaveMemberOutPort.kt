package y.june.prestudy.auth.port.out

import y.june.prestudy.auth.model.Member

interface SaveMemberOutPort {
    fun save(member: Member)
}
