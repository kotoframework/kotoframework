package com.kotoframework.interfaces

import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/19 23:33
 */
abstract class KotoJdbcWrapper {
    abstract fun queryForList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>>
    abstract fun <T> queryForObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T?
    abstract fun update(sql: String, paramMap: Map<String, Any?>): Int
    abstract fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray
    abstract val url: String
}
