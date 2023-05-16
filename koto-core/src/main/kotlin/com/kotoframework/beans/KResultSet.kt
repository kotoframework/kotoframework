package com.kotoframework.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KDataSet
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.utils.rmRedundantBlk
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.defaultJdbcHandler
import java.io.File
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/4/18 10:32
 */

/**
 * Koto result set
 *
 * @param T
 * @property sql
 * @property paramMap
 * @property wrapper
 * @property kClass
 * @constructor Create empty Koto result set
 * @author ousc
 */
class KResultSet<T>(
    override var sql: String,
    override val paramMap: Map<String, Any?>,
    val wrapper: KJdbcWrapper? = null,
    private val kClass: KClass<*>
) : KDataSet, KPojo {
    init {
        sql = sql.trim()
        if (sql.startsWith("koto_", true)) {
            val classResoucePath = this.javaClass.classLoader.getResource("")?.path
            sql = File("${classResoucePath}koto/sql/${sql.replace("koto_", "")}.sql").readLines().joinToString(" ")
                    .rmRedundantBlk()
        }
    }

    companion object {
        fun convertCountSql(sql: String): String {
            return "select count(*) from (${
                sql.replaceFirst("(?i)select.*?from".toRegex(), "select 1 from")
                        .replaceFirst("(?i)limit.*".toRegex(), "")
                        .replaceFirst("(?i)offset.*".toRegex(), "")
            }) as t"
        }

        fun getTotalCount(jdbcWrapper: KJdbcWrapper?, originalSql: String, map: Map<String, Any?>): Int {
            return defaultJdbcHandler!!.forObject(
                    jdbcWrapper,
                    convertCountSql(originalSql),
                    map,
                    false,
                    Int::class
            ) as Int
        }
    }

    fun <K> withTotal(action: KResultSet<T>.(KResultSet<T>) -> K): Pair<K, Int> {
        return this.action(this) to getTotalCount(wrapper, sql, paramMap)
    }

    /**
     * Query
     *
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    fun query(jdbcWrapper: KJdbcWrapper? = wrapper): List<Map<String, Any>> {
        return Jdbc.query(jdbcWrapper, sql, paramMap)
    }

    /**
     * Query for list
     *
     * @param K
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified K> queryForList(jdbcWrapper: KJdbcWrapper? = wrapper): List<K> {
        return defaultJdbcHandler!!.forList(jdbcWrapper, sql, paramMap, K::class) as List<K>
    }

    /**
     * Query for object
     *
     * @param K
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    inline fun <reified K> queryForObject(jdbcWrapper: KJdbcWrapper? = wrapper): K {
        return defaultJdbcHandler!!.forObject(jdbcWrapper, sql, paramMap, false, K::class) as K
    }

    /**
     * Query for object or null
     *
     * @param K
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    inline fun <reified K> queryForObjectOrNull(jdbcWrapper: KJdbcWrapper? = wrapper): K? {
        return defaultJdbcHandler!!.forObjectOrNull(jdbcWrapper, sql, paramMap, K::class) as K?
    }

    /**
     * Query for list
     *
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    @JvmName("queryForListK")
    @Suppress("UNCHECKED_CAST")
    fun queryForList(
            jdbcWrapper: KJdbcWrapper? = wrapper
    ): List<T> {
        return defaultJdbcHandler!!.forList(jdbcWrapper, sql, paramMap, kClass) as List<T>
    }

    /**
     * Query for object
     *
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    @JvmName("queryForObjectK")
    @Suppress("UNCHECKED_CAST")
    fun queryForObject(
            jdbcWrapper: KJdbcWrapper? = wrapper
    ): T {
        return defaultJdbcHandler!!.forObject(jdbcWrapper, sql, paramMap, false, kClass) as T
    }

    /**
     * Query for object or null
     *
     * @param jdbcWrapper
     * @return
     * @author ousc
     */
    @JvmName("queryForObjectOrNullK")
    @Suppress("UNCHECKED_CAST")
    fun queryForObjectOrNull(
            jdbcWrapper: KJdbcWrapper? = wrapper
    ): T? {
        return defaultJdbcHandler!!.forObjectOrNull(jdbcWrapper, sql, paramMap, kClass) as T?
    }
}
