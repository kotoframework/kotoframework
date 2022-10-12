package com.kotoframework.function.select

import com.kotoframework.core.condition.Criteria
import com.kotoframework.core.where.Where
import com.kotoframework.definition.*
import com.kotoframework.ConditionType
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Extension.toMutableMap
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/4/18 13:22
 */
class SelectAction<T : KPojo>(
    private val sql: String,
    private val kPojo: T,
    val kotoJdbcWrapper: KotoJdbcWrapper? = null,
    private val kClass: KClass<T>,
) {
    var paramMap = mutableMapOf<String, Any?>()

    init {
        paramMap = kPojo.toMutableMap()
    }

    /**
     * Create a new Where object and pass it the current KPojo object.
     * Then prefix the SQL string with the where keyword
     *
     * @return Nothing.
     */
    fun where(addCondition: AddCondition<T>? = null): SelectWhere<T> {
        return Where(kPojo, kotoJdbcWrapper, kClass) { addCondition?.invoke(kPojo) }.prefix("$sql where ")
    }

    fun where(vararg condition: Criteria?): SelectWhere<T> {
        return Where(
            kPojo, kotoJdbcWrapper, kClass
        ) { Criteria(type = ConditionType.AND, collections = condition.toList()) }.prefix("$sql where ")
    }

    fun by(vararg fields: Field): SelectWhere<T> {
        return where(*fields.map { it.selectBy }.toTypedArray())
    }

    fun query(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<Map<String, Any>> {
        return where().query(jdbcWrapper)
    }

    inline fun <reified T> queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<T> {
        return where().queryForList<T>(jdbcWrapper)
    }

    inline fun <reified T> queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T {
        return where().queryForObject<T>(jdbcWrapper)
    }

    inline fun <reified T> queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T {
        return where().queryForObject<T>(jdbcWrapper)
    }

    @JvmName("queryForList1")
    fun queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<T> {
        return where().queryForList(jdbcWrapper)
    }

    fun queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T {
        return where().queryForObject(jdbcWrapper)
    }

    fun queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T? {
        return where().queryForObjectOrNull(jdbcWrapper)
    }
}
