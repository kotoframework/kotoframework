package com.kotoframework.core.where

import com.kotoframework.*
import com.kotoframework.KotoApp.dbType
import com.kotoframework.beans.KotoResultSet
import com.kotoframework.beans.Unknown
import com.kotoframework.core.condition.Criteria
import com.kotoframework.core.condition.eq
import com.kotoframework.definition.AddCondition
import com.kotoframework.function.select.SelectWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoDataSet
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common
import com.kotoframework.utils.Common.copyProperties
import com.kotoframework.utils.Common.smartPagination
import com.kotoframework.utils.Extension.rmRedundantBlk
import com.kotoframework.utils.Jdbc.joinSqlStatement
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/5/10 02:29
 */
open class Where<T : KPojo>(
    var KPojo: T,
    open var kotoJdbcWrapper: KotoJdbcWrapper? = null,
    open val kClass: KClass<*> = Unknown::class,
    addCondition: AddCondition<T>? = null
) : BaseWhere<T>(KPojo, addCondition) {
    internal var deleted = 0
    internal open var prefix: String = ""
    internal var suffix: String = ""
    internal var distinct: Boolean = false
    internal var limitOne: Boolean = false
    internal var limit: Int? = null
    internal var offset: Int? = null
    internal var pageIndex: Int? = null
    internal var pageSize: Int? = null
    internal var groupBy: String = ""
    internal var orderBy: String = ""

    //Batch copy some attributes of where to this
    open fun where(where: Where<T>): Where<T> {
        copyProperties(where, this)
        return this
    }

    /**
     * It sets the pageIndex value to the finalMap.
     *
     * @param pageIndex The page number to retrieve.
     * @return Nothing.
     */
    fun page(pageIndex: Int, pageSize: Int): SelectWhere<T> {
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        return SelectWhere(KPojo, kotoJdbcWrapper, kClass = this.kClass).where(this)
    }

    /**
     * It takes a string and sets the prefix for the where clause
     *
     * @param sql The SQL to prefix the where clause with.
     * @return Nothing.
     */
    open fun prefix(sql: String): SelectWhere<T> {
        this.prefix = sql
        return SelectWhere(KPojo, kotoJdbcWrapper, kClass = this.kClass).where(this)
    }

    /**
     * It returns an instance of OperateWhere.
     *
     * @param sql The SQL statement to be executed
     * @return A new instance of OperateWhere with a new instance of CommonTable
     */
    internal fun prefixOW(sql: String): OperateWhere<T> {
        this.prefix = sql
        return OperateWhere(KPojo, kotoJdbcWrapper).where(this)
    }

    /**
     * This function returns a Where object with the suffix set to the given sql
     *
     * @param sql The SQL statement to be appended to the end of the generated SQL statement.
     * @return Nothing.
     */
    open fun suffix(sql: String): Where<T> {
        this.suffix = sql
        return this
    }

    /**
     * This function sets the distinct flag to true
     *
     * @return Nothing.
     */
    open fun distinct(): SelectWhere<T> {
        this.distinct = true
        return SelectWhere(KPojo, kotoJdbcWrapper, kClass = kClass).where(this)
    }

    /**
     * This function sets the deleted column to 1
     *
     * @return Nothing.
     */
    fun deleted(): Where<T> {
        this.deleted = 1
        return this
    }

    /**
     * `first()` sets the `limitOne` property to `true` and returns the `Where` object
     *
     * @return The Where object
     */
    open fun first(): Where<T> {
        this.limitOne = true
        return this
    }

    /**
     * It takes a variable number of arguments and adds them to a map.
     *
     * @param pairs An array of key-value pairs.
     * @return A Where object
     */
    override fun map(vararg pairs: Pair<String, Any?>): Where<T> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    private var ifNoValueStrategy: (Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }
    open fun ifNoValue(strategy: (Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }): Where<T> {
        this.ifNoValueStrategy = strategy
        return this
    }


    /**
     * The function is used to build the sql and parameter map
     *
     * @return A ConditionResult object.
     */
    override fun build(): KotoDataSet {
        conditions = conditions.filterNotNull().toMutableList()
        if (conditions.isEmpty()) {
            paramMap.keys.forEach {
                conditions.add(it.eq())
            }
        }

        conditions = conditions.distinctBy { it!!.reName to it.type }.toMutableList()

        for ((key, value) in finalMap) {
            paramMap[key] = value
        }

        val finalSql = joinSqlStatement(conditions, paramMap, ifNoValueStrategy)

        if (distinct && prefix.isNotBlank()) prefix = prefix.replaceFirst("select", "select distinct")

        var (paginatedPrefix, paginatedSuffix, paginatedLimit, paginatedOffset) = smartPagination(
            prefix,
            suffix,
            pageIndex,
            pageSize
        )

        limit = paginatedLimit ?: limit

        offset = paginatedOffset ?: offset

        if (limitOne) paginatedSuffix = "$paginatedSuffix limit 1"

        if (limit != null) paginatedSuffix = "$paginatedSuffix limit $limit"

        if (offset != null) paginatedSuffix = "$paginatedSuffix offset $offset"

        val sql = " ${
            listOf(Common.deleted(deleted, kotoJdbcWrapper, tableName), finalSql).filter { it.isNotBlank() }
                .joinToString(" and ")
        } "

        return KotoResultSet<T>(
            "$paginatedPrefix $sql $groupBy $orderBy $paginatedSuffix".rmRedundantBlk(),
            paramMap,
            kotoJdbcWrapper,
            kClass
        )
    }

    open operator fun component1() = build()
}
