package com.kotoframework.interfaces

import com.kotoframework.definition.Field

/**
 * Created by ousc on 2022/9/19 23:33
 */
abstract class KJdbcWrapper {
    abstract fun forList(sql: String, paramMap: Map<String, Any?> = mapOf()): List<Map<String, Any>>
    abstract fun forMap(sql: String, paramMap: Map<String, Any?> = mapOf()): Map<String, Any>?
    abstract fun <T> forObject(sql: String, paramMap: Map<String, Any?> = mapOf(), clazz: Class<T>): T?
    abstract fun update(sql: String, paramMap: Map<String, Any?> = mapOf()): Int
    abstract fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>> = arrayOf()): IntArray

    abstract val url: String
}
