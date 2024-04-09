package com.kotoframework.interfaces

import com.kotoframework.definition.Field
import com.kotoframework.function.create.CreateWhere
import com.kotoframework.function.remove.RemoveAction
import com.kotoframework.function.update.UpdateAction

/**
 * Created by ousc on 2022/9/19 23:33
 */
interface KotoJdbcWrapper {
    fun forList(sql: String, paramMap: Map<String, Any?> = mapOf()): List<Map<String, Any>>
    fun forMap(sql: String, paramMap: Map<String, Any?> = mapOf()): Map<String, Any>?
    fun <T> forObject(sql: String, paramMap: Map<String, Any?> = mapOf(), clazz: Class<T>): T?
    fun update(sql: String, paramMap: Map<String, Any?> = mapOf()): Int
    fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>> = arrayOf()): IntArray

    val url: String
}
