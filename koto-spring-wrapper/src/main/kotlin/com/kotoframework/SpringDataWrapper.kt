package com.kotoframework

import com.kotoframework.beans.NoDataSourceSpecifiedException
import com.kotoframework.interfaces.KotoJdbcWrapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/19 23:24
 */
class SpringDataWrapper : KotoJdbcWrapper {
    var namedJdbc: NamedParameterJdbcTemplate? = null
    var dynamic: (() -> NamedParameterJdbcTemplate)? = null
    fun getNamedJdbc(jdbc: NamedParameterJdbcTemplate? = null): NamedParameterJdbcTemplate {
        return jdbc ?: namedJdbc ?: dynamic?.invoke()
        ?: throw NoDataSourceSpecifiedException("NamedParameterJdbcTemplate is null")
    }

    private val dataSource: DataSource
        get() = (namedJdbc ?: dynamic?.invoke())?.jdbcTemplate?.dataSource
            ?: throw NoDataSourceSpecifiedException("dataSource is null")

    override fun forList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> {
        return getNamedJdbc().queryForList(sql, paramMap) ?: emptyList()
    }

    @Suppress("UNSAFE_CALL_ON_PARTIALly_NULLABLE")
    override fun forMap(sql: String, paramMap: Map<String, Any?>): Map<String, Any>? {
        return getNamedJdbc().queryForMap(sql, paramMap)
    }

    override fun <T> forObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? {
        return getNamedJdbc().queryForObject(sql, paramMap, clazz)
    }

    override fun update(sql: String, paramMap: Map<String, Any?>): Int {
        return getNamedJdbc().update(sql, paramMap)
    }

    override fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray {
        return getNamedJdbc().batchUpdate(sql, paramMaps)
    }

    override val url: String
        get() {
            val conn = dataSource.connection
            val url = conn.metaData.url
            conn.close()
            return url
        }

    companion object {
        fun NamedParameterJdbcTemplate?.wrapper(): SpringDataWrapper? {
            return this?.let {
                SpringDataWrapper().apply {
                    namedJdbc = it
                }
            }
        }
    }
}
