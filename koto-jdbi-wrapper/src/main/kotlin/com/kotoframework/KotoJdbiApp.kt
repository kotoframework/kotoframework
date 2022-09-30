package com.kotoframework

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import org.apache.commons.dbcp2.BasicDataSource
import org.jdbi.v3.core.Jdbi

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoJdbiApp {
    fun KotoApp.setDynamicDataSource(jdbi: () -> Jdbi): KotoApp {
        val springjdbcWrapper = JdbiWrapper()
        springjdbcWrapper.dynamic = jdbi
        Jdbc.defaultJdbcWrapper = springjdbcWrapper
        Jdbc.defaultJdbcHandler = JdbiHandler()
        return this
    }

    fun KotoApp.setDataSource(
        url: String,
        userName: String,
        password: String,
        driverClassName: String = "com.mysql.cj.jdbc.Driver"
    ): KotoApp {
        val dataSource = BasicDataSource().apply {
            this.url = url
            this.username = userName
            this.password = password
            this.driverClassName = driverClassName
            this.maxTotal = 10
            this.maxIdle = 5
        }
        Jdbc.defaultJdbcWrapper = Jdbi.create(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = JdbiHandler()
        return this
    }

    fun KotoApp.setDataSource(dataSource: BasicDataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = Jdbi.create(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = JdbiHandler()
        return this
    }
}
