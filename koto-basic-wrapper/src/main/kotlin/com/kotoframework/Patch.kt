package com.kotoframework

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.utils.lineToHump
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.defaultJdbcHandler
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 10:28
 */
object Patch {
    fun DataSource.query(
        sql: String,
        paramMap: Map<String, Any?> = mapOf(),
        lineToHump: Boolean = false
    ): List<Map<String, Any>> {
        return Jdbc.query(this.wrapper(), sql, paramMap).map { it.lineToHump(lineToHump) }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> DataSource.queryForList(
        sql: String,
        paramMap: Map<String, Any?> = mapOf()
    ): List<T> {
        return defaultJdbcHandler!!.forList(this.wrapper(), sql, paramMap, T::class) as List<T>
    }

    inline fun <reified T> DataSource.queryForObject(
        sql: String,
        paramMap: Map<String, Any?> = mapOf()
    ): T {
        return defaultJdbcHandler!!.forObject(this.wrapper(), sql, paramMap, false, T::class) as T
    }

    inline fun <reified T> DataSource.queryForObjectOrNull(
        sql: String,
        paramMap: Map<String, Any?> = mapOf()
    ): T? {
        return defaultJdbcHandler!!.forObjectOrNull(this.wrapper(), sql, paramMap, T::class) as T?
    }
}
