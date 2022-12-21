package com.kotoframework

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoSpringApp {
    fun KotoApp.setDynamicDataSource(namedJdbc: () -> NamedParameterJdbcTemplate): KotoApp {
        val springJdbcWrapper = SpringDataWrapper()
        springJdbcWrapper.dynamic = namedJdbc
        Jdbc.defaultJdbcWrapper = springJdbcWrapper
        Jdbc.defaultJdbcHandler = SpringDataHandler()
        return this
    }
    fun KotoApp.setDataSource(dataSource: DataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = NamedParameterJdbcTemplate(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = SpringDataHandler()
        return this
    }
}
