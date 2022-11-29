package com.kotoframework

import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.defaultJdbcHandler

/**
 * Created by ousc on 2022/9/20 10:28
 */
object Patch {
    fun SpringDataWrapper.query(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<Map<String, Any>> {
        return Jdbc.query(this, sql, paramMap)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> SpringDataWrapper.queryForList(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<T> {
        return defaultJdbcHandler!!.forList(this, sql, paramMap, T::class) as List<T>
    }

    inline fun <reified T> SpringDataWrapper.queryForObject(
        sql: String,
        paramMap: Map<String, Any?>
    ): T {
        return defaultJdbcHandler!!.forObject(this, sql, paramMap, false, T::class) as T
    }

    inline fun <reified T> SpringDataWrapper.queryForObjectOrNull(
        sql: String,
        paramMap: Map<String, Any?>
    ): T? {
        return defaultJdbcHandler!!.forObjectOrNull(this, sql, paramMap, T::class) as T?
    }
}
