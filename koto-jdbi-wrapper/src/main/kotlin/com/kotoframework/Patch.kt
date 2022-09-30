package com.kotoframework

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.defaultJdbcHandler
import org.apache.commons.dbcp2.BasicDataSource
import org.jdbi.v3.core.Jdbi

/**
 * Created by ousc on 2022/9/20 10:28
 */
object Patch {
    fun Jdbi.query(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<Map<String, Any>> {
        return Jdbc.query(this.wrapper(), sql, paramMap)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> Jdbi.queryForList(
        sql: String,
        paramMap: Map<String, Any?>
    ): List<T> {
        return defaultJdbcHandler!!.forList(this.wrapper(), sql, paramMap, T::class) as List<T>
    }

    inline fun <reified T> Jdbi.queryForObject(
        sql: String,
        paramMap: Map<String, Any?>
    ): T {
        return defaultJdbcHandler!!.forObject(this.wrapper(), sql, paramMap, false, T::class) as T
    }

    inline fun <reified T> Jdbi.queryForObjectOrNull(
        sql: String,
        paramMap: Map<String, Any?>
    ): T? {
        return defaultJdbcHandler!!.forObjectOrNull(this.wrapper(), sql, paramMap, T::class) as T?
    }

    val Jdbi.dbName: String
        get() = (this.withHandle<BasicDataSource, RuntimeException> { it.connection.unwrap(BasicDataSource::class.java) } as BasicDataSource).url.split(
            "?"
        ).first().split(
            "//"
        )[1].split("/")[1]

}
