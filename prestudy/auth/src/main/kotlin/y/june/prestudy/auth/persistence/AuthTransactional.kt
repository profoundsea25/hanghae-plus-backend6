package y.june.prestudy.auth.persistence

import org.springframework.transaction.annotation.Transactional
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention
@Inherited
@Transactional(
    transactionManager = AUTH_TRANSACTION_MANAGER,
    rollbackFor = [Throwable::class]
)
annotation class AuthTransactional
