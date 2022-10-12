package com.kotoframework.function.select

import com.kotoframework.beans.KotoResultSet
import com.kotoframework.definition.*
import com.kotoframework.core.where.Where
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Extension.isNullOrBlank
import com.kotoframework.utils.Jdbc.defaultJdbcHandler
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/5/10 02:31
 */
class SelectWhere<T : KPojo>(
    kPojo: T,
    kotoJdbcWrapper: KotoJdbcWrapper? = null,
    addCondition: AddCondition<T>? = null,
    override val kClass: KClass<*>
) : Where<T>(kPojo, kotoJdbcWrapper, kClass, addCondition) {

    override fun where(where: Where<T>): SelectWhere<T> {
        super.where(where)
        return this
    }

    /**
     * > If the prefix of the sql does not contain the word "select" (case insensitive), throw a runtime exception
     */
    fun validate() {
        if (!this.prefix.contains("select", true)
        ) {
            throw RuntimeException("The prefix of the sql is not correct.")
        }
    }

    /**
     * This function returns a Where object with the suffix set to the given sql
     *
     * @param sql The SQL statement to be appended to the end of the generated SQL statement.
     * @return Nothing.
     */
    override fun suffix(sql: String): SelectWhere<T> {
        this.suffix = sql
        return this
    }

    override fun first(): SelectWhere<T> {
        this.limitOne = true
        return this
    }

    override fun distinct(): SelectWhere<T> {
        this.distinct = true
        return this
    }

    private fun groupOrOrderBy(type: String, vararg field: Field?): String {
        if (field.none { !it.isNullOrBlank() }) {
            return ""
        }
        var str = " $type by "
        field.forEach {
            if(it.isNullOrBlank()) return@forEach
            val fieldName = if (it!!.columnName.contains(
                    " as ",
                    true
                ) || it.columnName.contains("(")
            ) it.columnName else "`${it.columnName}`"
            val direction = if (type === "order") it.direction else ""
            str += "$fieldName $direction,"
        }
        return str.substring(0, str.length - 1)
    }

    fun orderBy(vararg field: Field?): SelectWhere<T> {
        this.orderBy = groupOrOrderBy("order", *field)
        return this
    }

    fun groupBy(vararg field: Field?): SelectWhere<T> {
        this.groupBy = groupOrOrderBy("group", *field)
        return this
    }

    override fun map(vararg pairs: Pair<String, Any?>): SelectWhere<T> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    override fun allowNull(nullAllowed: Boolean): SelectWhere<T> {
        super.allowNull(nullAllowed)
        return this
    }

    /**
     * The function is used to build the sql and parameter map
     *
     * @return A ConditionResult object.
     */
    override fun build(): KotoResultSet<T> {
        validate()
        val result = super.build()
        return KotoResultSet(result.sql, result.paramMap, kotoJdbcWrapper, kClass)
    }

    fun query(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<Map<String, Any>> {
        validate()
        return build().query(jdbcWrapper)
    }

    inline fun <reified K> queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<K> {
        validate()
        return build().queryForList<K>(jdbcWrapper)
    }

    inline fun <reified K> queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): K {
        validate()
        return build().queryForObject<K>(jdbcWrapper)
    }

    inline fun <reified K> queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): K? {
        validate()
        return build().queryForObjectOrNull<K>(jdbcWrapper)
    }

    @JvmName("queryForList1")
    @Suppress("UNCHECKED_CAST")
    fun queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<T> {
        validate()
        build().let {
            return defaultJdbcHandler!!.forList(
                jdbcWrapper,
                it.sql,
                it.paramMap,
                kClass
            ) as List<T>
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T {
        validate()
        build().let {
            return defaultJdbcHandler!!.forObject(jdbcWrapper, it.sql, it.paramMap, false, kClass) as T
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T? {
        validate()
        build().let {
            return defaultJdbcHandler!!.forObjectOrNull(jdbcWrapper, it.sql, it.paramMap, kClass) as T?
        }
    }
}
