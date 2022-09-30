package com.kotoframework

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoSpringApp {
    fun KotoApp.setDynamicDataSource(namedJdbc: () -> NamedParameterJdbcTemplate): KotoApp {
        val springjdbcWrapper = SpringDataWrapper()
        springjdbcWrapper.dynamic = namedJdbc
        Jdbc.defaultJdbcWrapper = springjdbcWrapper
        Jdbc.defaultJdbcHandler = SpringDataHandler()
        return this
    }

    fun KotoApp.setDataSource(url: String, userName: String, password: String, driverClassName: String = "com.mysql.cj.jdbc.Driver"): KotoApp {
        val dataSource = BasicDataSource().apply {
            this.url = url
            this.username = userName
            this.password = password
            this.driverClassName = driverClassName
            this.maxTotal = 10
            this.maxIdle = 5
        }
        Jdbc.defaultJdbcWrapper = NamedParameterJdbcTemplate(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = SpringDataHandler()
        return this
    }

    fun KotoApp.setDataSource(dataSource: BasicDataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = NamedParameterJdbcTemplate(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = SpringDataHandler()
        return this
    }
}
