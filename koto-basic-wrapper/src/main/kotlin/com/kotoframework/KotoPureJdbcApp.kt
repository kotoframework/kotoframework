package com.kotoframework

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import org.apache.commons.dbcp2.BasicDataSource
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoPureJdbcApp {
    fun KotoApp.setDynamicDataSource(ds: () -> DataSource): KotoApp {
        val pureJdbcWrapper = BasicJdbcWrapper()
        pureJdbcWrapper.dynamic = ds
        Jdbc.defaultJdbcWrapper = pureJdbcWrapper
        Jdbc.defaultJdbcHandler = BasicJdbcHandler()
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
        Jdbc.defaultJdbcWrapper = dataSource.wrapper()
        Jdbc.defaultJdbcHandler = BasicJdbcHandler()
        return this
    }

    fun KotoApp.setDataSource(ds: DataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = ds.wrapper()
        Jdbc.defaultJdbcHandler = BasicJdbcHandler()
        return this
    }
}
