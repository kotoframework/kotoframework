package com.kotoframework

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 09:44
 */
object KotoBasicJdbcApp {
    fun KotoApp.setDynamicDataSource(ds: () -> DataSource): KotoApp {
        val pureJdbcWrapper = BasicJdbcWrapper()
        pureJdbcWrapper.dynamic = ds
        Jdbc.defaultJdbcWrapper = pureJdbcWrapper
        Jdbc.defaultJdbcHandler = BasicJdbcHandler()
        return this
    }

    fun KotoApp.setDataSource(ds: DataSource): KotoApp {
        Jdbc.defaultJdbcWrapper = ds.wrapper()
        Jdbc.defaultJdbcHandler = BasicJdbcHandler()
        return this
    }
}
