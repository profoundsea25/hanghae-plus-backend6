package y.june.prestudy.post.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

const val POST_DATASOURCE = "postDatasource"
const val POST_ENTITY_MANAGER_FACTORY = "postEntityManagerFactory"
const val POST_TRANSACTION_MANAGER = "postTransactionManager"

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
@EnableJpaRepositories(
    basePackages = ["y.june.prestudy.post.persistence"],
    entityManagerFactoryRef = POST_ENTITY_MANAGER_FACTORY,
    transactionManagerRef = POST_TRANSACTION_MANAGER,
)
@EnableTransactionManagement
class PostJpaConfig : HikariConfig() {

    @Bean(name = [POST_DATASOURCE])
    fun dataSource(): DataSource {
        return LazyConnectionDataSourceProxy(HikariDataSource(this))
    }

    @Bean(name = [POST_ENTITY_MANAGER_FACTORY])
    fun entityManagerFactory(
        @Qualifier(POST_DATASOURCE) dataSource: DataSource
    ): EntityManagerFactory {
        return LocalContainerEntityManagerFactoryBean()
            .apply {
                this.dataSource = dataSource
                this.jpaVendorAdapter = HibernateJpaVendorAdapter()
                this.setPackagesToScan("y.june.prestudy.post.persistence.entity")
                this.jpaPropertyMap = mapOf(
                    // yaml 파일에 명시하면 적용되지 않으므로, 추가 프로퍼티가 필요한 경우 여기에 명시
                    AvailableSettings.HBM2DDL_AUTO to "create",
                    AvailableSettings.PHYSICAL_NAMING_STRATEGY to CamelCaseToUnderscoresNamingStrategy::class.java.name,
                    AvailableSettings.SHOW_SQL to true,
                    AvailableSettings.FORMAT_SQL to true,
                    AvailableSettings.HIGHLIGHT_SQL to true,
                )
                afterPropertiesSet()
            }
            .`object`!!
    }

    @Bean(name = [POST_TRANSACTION_MANAGER])
    fun transactionManager(
        @Qualifier(POST_ENTITY_MANAGER_FACTORY) entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager()
        .apply { this.entityManagerFactory = entityManagerFactory }

}
