package com.kotoframework.interfaces

import com.kotoframework.beans.KResultSet
import com.kotoframework.utils.Jdbc
import kotlin.reflect.KClass

/**
 * Created by sundaiyue on 2023/4/5 09:43
 */
abstract class Queryable<T : KPojo>(open val wrapper: KJdbcWrapper? = null, val kClass: KClass<*>): Buildable() {

    abstract override fun build(): KResultSet<T>
    fun query(jdbcWrapper: KJdbcWrapper? = wrapper): List<Map<String, Any>> {
        return build().query(jdbcWrapper)
    }

    inline fun <reified K> queryForList(jdbcWrapper: KJdbcWrapper? = wrapper): List<K> {
        return build().queryForList<K>(jdbcWrapper)
    }

    inline fun <reified K> queryForObject(jdbcWrapper: KJdbcWrapper? = wrapper): K {
        return build().queryForObject<K>(jdbcWrapper)
    }

    inline fun <reified K> queryForObjectOrNull(jdbcWrapper: KJdbcWrapper? = wrapper): K? {
        return build().queryForObjectOrNull<K>(jdbcWrapper)
    }

    @JvmName("queryForList1")
    @Suppress("UNCHECKED_CAST")
    fun queryForList(jdbcWrapper: KJdbcWrapper? = wrapper): List<T> {
        build().let {
            return Jdbc.defaultJdbcHandler!!.forList(
                    jdbcWrapper,
                    it.sql,
                    it.paramMap,
                    kClass
            ) as List<T>
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun queryForObject(jdbcWrapper: KJdbcWrapper? = wrapper): T {
        build().let {
            return Jdbc.defaultJdbcHandler!!.forObject(jdbcWrapper, it.sql, it.paramMap, false, kClass) as T
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun queryForObjectOrNull(jdbcWrapper: KJdbcWrapper? = wrapper): T? {
        build().let {
            return Jdbc.defaultJdbcHandler!!.forObjectOrNull(jdbcWrapper, it.sql, it.paramMap, kClass) as T?
        }
    }

    fun <K> withTotal(action: (Queryable<T>) -> K): Pair<K, Int> {
        return action(this) to build().let { KResultSet.getTotalCount(wrapper, it.sql, it.paramMap) }
    }
}
