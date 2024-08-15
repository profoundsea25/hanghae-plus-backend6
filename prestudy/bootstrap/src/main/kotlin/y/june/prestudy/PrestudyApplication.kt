package y.june.prestudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["y.june.prestudy"]
)
class PrestudyApplication

fun main(args: Array<String>) {
    runApplication<PrestudyApplication>(*args)
}
