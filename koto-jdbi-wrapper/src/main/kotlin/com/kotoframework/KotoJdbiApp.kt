package com.kotoframework

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import org.jdbi.v3.core.Jdbi
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoJdbiApp {
    fun KotoApp.setDynamicDataSource(jdbi: () -> Jdbi): KotoApp {
        val jdbiWrapper = JdbiWrapper()
        jdbiWrapper.dynamic = jdbi
        Jdbc.defaultJdbcWrapper = jdbiWrapper
        Jdbc.defaultJdbcHandler = JdbiHandler()
        return this
    }

    fun KotoApp.setDataSource(dataSource: DataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = Jdbi.create(dataSource).wrapper()
        Jdbc.defaultJdbcHandler = JdbiHandler()
        return this
    }
}
