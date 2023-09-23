package com.kotoframework.orm.query

import com.kotoframework.KotoApp
import com.kotoframework.beans.KResultSet
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.Field
import com.kotoframework.definition.NoValueField
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.interfaces.Queryable
import com.kotoframework.utils.tableMeta
import com.kotoframework.utils.tableName
import com.kotoframework.utils.toMap
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.SqlBuilder

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class QueryAction<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo, T15 : KPojo, T16 : KPojo>(open var t1: T1? = null, open var t2: T2? = null, open var t3: T3? = null, open var t4: T4? = null, open var t5: T5? = null, open var t6: T6? = null, open var t7: T7? = null, open var t8: T8? = null, open var t9: T9? = null, open var t10: T10? = null, open var t11: T11? = null, open var t12: T12? = null, open var t13: T13? = null, open var t14: T14? = null, open var t15: T15? = null, open var t16: T16? = null, override val wrapper: KJdbcWrapper? = null) : Queryable<T1>(wrapper, t1!!::class) {
    internal lateinit var tables: List<KPojo>

    var selectedFields: MutableList<Field> = mutableListOf("*") // selected fields


    internal var distinct: Boolean = false
    internal var limit: Int? = null
    internal var offset: Int? = null
    internal var pageIndex: Int? = null
    internal var pageSize: Int? = null
    internal var groupBy: List<Field>? = null
    internal var orderBy: List<Field>? = null
    internal var suffix: String? = null
    private var sql: String = ""
    var complex = false
    internal val paramMap: MutableMap<String, Any?> = mutableMapOf()
    internal var onCriteria: MutableMap<String, Criteria?> = mutableMapOf()
    val joinTypes = mutableMapOf<String, JoinType>()
    internal var whereCriteria: MutableList<Criteria?> = mutableListOf()
    internal var autoWhere = false
    internal var ifNoValueStrategy: NoValueField.(Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }
    open fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.ifNoValueStrategy = strategy
        return this
    }

    internal fun initTables() {
        this.tables = listOfNotNull(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16).filter { it != unknown }
        tables.forEach { table ->
            Jdbc.initMetaData(table.tableMeta, wrapper, table)
            val prefix = if (tables.size == 1) "" else "${table.tableName}@"
            paramMap.putAll(table.toMap().map { entry -> "$prefix${entry.key}" to entry.value }.filter { it.second != null }.toMap())
        }
    }

    open fun select(vararg fields: Field): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        selectedFields = fields.toMutableList()
        return this
    }

    inline fun <reified R : QA> join(action: R): R {
        return action.let {
            it.selectedFields.addAll(selectedFields)
            it
        }
    }

    open fun limit(limit: Int): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.limit = limit
        return this
    }

    open fun offset(offset: Int): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.offset = offset
        return this
    }

    open fun page(pageIndex: Int, pageSize: Int): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    open fun first(): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return limit(1)
    }

    open fun distinct(): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.distinct = true
        return this
    }

    open fun groupBy(vararg fields: Field): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.groupBy = fields.toList()
        return this
    }

    open fun orderBy(vararg fields: Field): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.orderBy = fields.toList()
        return this
    }

    open fun patch(vararg pairs: Pair<String, Any?>): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        pairs.forEach {
            paramMap[it.first] = it.second
        }
        return this
    }

    @Deprecated("map(pairs) is deprecated, use patch() instead", ReplaceWith("patch(pairs)"))
    open fun map(vararg pairs: Pair<String, Any?>): QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return patch(*pairs)
    }

    val prepared: KResultSet<T1> get() = build()
    override fun build(): KResultSet<T1> {
        initTables()
        val (sql, paramMap) = SqlBuilder(this).build("select")
        return KResultSet(sql, paramMap, wrapper, t1!!::class)
    }
}
