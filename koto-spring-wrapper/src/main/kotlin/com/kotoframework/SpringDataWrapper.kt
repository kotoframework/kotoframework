package com.kotoframework

import com.kotoframework.interfaces.KotoJdbcWrapper
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/19 23:24
 */
class SpringDataWrapper : KotoJdbcWrapper() {
    var namedJdbc: NamedParameterJdbcTemplate? = null
    var dynamic: (() -> NamedParameterJdbcTemplate)? = null
    fun getNamedJdbc(jdbc: NamedParameterJdbcTemplate? = null): NamedParameterJdbcTemplate {
        return jdbc ?: namedJdbc ?: dynamic?.invoke() ?: throw RuntimeException("NamedParameterJdbcTemplate is null")
    }

    private val dataSource: BasicDataSource
        get() = (namedJdbc ?: dynamic?.invoke())?.jdbcTemplate?.dataSource as BasicDataSource?
            ?: throw RuntimeException("dataSource is null")

    override fun queryForList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> {
        return getNamedJdbc().queryForList(sql, paramMap) ?: emptyList()
    }

    override fun <T> queryForObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? {
        return getNamedJdbc().queryForObject(sql, paramMap, clazz)
    }

    override fun update(sql: String, paramMap: Map<String, Any?>): Int {
        return getNamedJdbc().update(sql, paramMap)
    }

    override fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray {
        return getNamedJdbc().batchUpdate(sql, paramMaps)
    }

    override val url: String
        get() = dataSource.url

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
