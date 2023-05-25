package com.kotoframework.orm.query.actions

import com.kotoframework.SQL
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin3
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableAlias
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom2<T1 : KPojo, T2 : KPojo>(
    override var t1: T1? = null, override var t2: T2? = null, val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction2<T1, T2>(
    t1,
    t2,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    unknown,
    kJdbcWrapper
) {
    fun on(addCriteria: AddCriteria2<T1, T2>): SelectFrom2<T1, T2> {
        this.onCriteria[t2!!.tableName] = criteriaField { addCriteria(t1!!, t2!!) }.criteria
        return this
    }

    fun using(field: String): SelectFrom2<T1, T2> {
        this.onCriteria[t2!!.tableName] = Criteria(
            type = SQL,
            sql = " ${t1!!::class.tableAlias}.$field = ${t2!!.tableAlias}.$field ",
            tableName = t2!!.tableName
        )
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField2<F, T1, T2>, action: SelectFrom2<T1, T2>.(Array<Field>) -> SelectFrom2<T1, T2>
    ): SelectFrom2<T1, T2> {
        return action(apply { addField(t1!!, t2!!) }.fields.toTypedArray())
    }

    override fun select(vararg fields: Field): SelectFrom2<T1, T2> {
        return super.select(*fields) as SelectFrom2<T1, T2>
    }

    fun select(addField: AddField2<SelectField, T1, T2>): SelectFrom2<T1, T2> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom2<T1, T2> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria2<T1, T2>): SelectFrom2<T1, T2> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!) }.criteria)
        return this
    }

    inline fun <reified K : KPojo> join(
        t3: K = K::class.javaInstance(),
        joinType: JoinType = JoinType.LEFT_JOIN,
        action: AddJoin3<T1, T2, K>
    ) = join(SelectFrom3(t1, t2, t3, wrapper)).apply { joinTypes[t3.tableName] = joinType }
        .action(t1!!, t2!!, t3)

    override fun limit(limit: Int): SelectFrom2<T1, T2> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom2<T1, T2> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom2<T1, T2> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom2<T1, T2> {
        return limit(1)
    }

    override fun distinct(): SelectFrom2<T1, T2> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom2<T1, T2> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField2<SelectField, T1, T2>): SelectFrom2<T1, T2> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom2<T1, T2> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField2<OrderByField, T1, T2>): SelectFrom2<T1, T2> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom2<T1, T2> {
        super.ifNoValue(strategy)
        return this
    }
}
