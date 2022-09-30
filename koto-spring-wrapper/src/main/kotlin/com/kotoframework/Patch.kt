package com.kotoframework

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.defaultJdbcHandler
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/20 10:28
 */
object Patch {
    fun NamedParameterJdbcTemplate.queryByKoto(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<Map<String, Any>> {
        return Jdbc.query(this.wrapper(), sql, paramMap)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> NamedParameterJdbcTemplate.queryForListByKoto(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<T> {
        return defaultJdbcHandler!!.forList(this.wrapper(), sql, paramMap, T::class) as List<T>
    }

    inline fun <reified T> NamedParameterJdbcTemplate.queryForObjectByKoto(
        sql: String,
        paramMap: Map<String, Any?>
    ): T {
        return defaultJdbcHandler!!.forObject(this.wrapper(), sql, paramMap, false, T::class) as T
    }

    inline fun <reified T> NamedParameterJdbcTemplate.queryForObjectOrNull(
        sql: String,
        paramMap: Map<String, Any?>
    ): T? {
        return defaultJdbcHandler!!.forObjectOrNull(this.wrapper(), sql, paramMap, T::class) as T?
    }
}
