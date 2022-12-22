package test.wrapper

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/8/2 19:57
 */
object DataSource {
    val dataSource = BasicDataSource().apply {
        url = "jdbc:mysql://localhost:3306/koto?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8"
        username = "root"
        password = "root"
        driverClassName = "com.mysql.cj.jdbc.Driver"
    }
    val namedJdbc = NamedParameterJdbcTemplate(dataSource)
}
