package com.kotoframework.interfaces

import com.kotoframework.definition.Field
import com.kotoframework.function.remove.RemoveAction
import com.kotoframework.function.update.UpdateAction

/**
 * Created by ousc on 2022/9/19 23:33
 */
abstract class KotoJdbcWrapper {
    abstract fun queryForList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>>
    abstract fun <T> queryForObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T?
    abstract fun update(sql: String, paramMap: Map<String, Any?>): Int
    abstract fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray

    inline fun <reified T : KPojo> update(kPojo: T, vararg fields: Field): UpdateAction<T> {
        return com.kotoframework.function.update.update(kPojo, *fields, jdbcWrapper = this)
    }

    inline fun <reified T : KPojo> remove(KPojo: T): RemoveAction<T> {
        return com.kotoframework.function.remove.remove(KPojo, jdbcWrapper = this)
    }

    inline fun <reified T : KPojo> create(kPojo: T, vararg fields: Field): UpdateAction<T> {
        return com.kotoframework.function.update.update(kPojo, *fields, jdbcWrapper = this)
    }

    abstract val url: String
}
