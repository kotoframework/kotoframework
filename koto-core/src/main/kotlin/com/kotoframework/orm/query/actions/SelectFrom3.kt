package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin4
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom3<T1 : KPojo, T2 : KPojo, T3 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction3<T1, T2, T3>(
    t1,
    t2,
    t3,
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

    fun on(addCriteria: AddCriteria3<T1, T2, T3>): SelectFrom3<T1, T2, T3> {
        this.onCriteria[t3!!.tableName] = criteriaField { addCriteria(t1!!, t2!!, t3!!) }.kotlinAST
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField3<F, T1, T2, T3>, action: SelectFrom3<T1, T2, T3>.(Array<Field>) -> SelectFrom3<T1, T2, T3>
    ): SelectFrom3<T1, T2, T3> {
        return action(apply { addField(t1!!, t2!!, t3!!) }.fields.toTypedArray())
    }

    override fun select(vararg fields: Field): SelectFrom3<T1, T2, T3> {
        return super.select(*fields) as SelectFrom3<T1, T2, T3>
    }

    fun select(addField: AddField3<SelectField, T1, T2, T3>): SelectFrom3<T1, T2, T3> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom3<T1, T2, T3> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria3<T1, T2, T3>): SelectFrom3<T1, T2, T3> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!) }.kotlinAST)
        return this
    }

    inline fun <reified K : KPojo> join(
        t4: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin4<T1, T2, T3, K>
    ) = join(SelectFrom4(t1, t2, t3, t4, kJdbcWrapper)).apply { joinTypes[t4.tableName] = joinType }
        .action(t1!!, t2!!, t3!!, t4)

    override fun limit(limit: Int): SelectFrom3<T1, T2, T3> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom3<T1, T2, T3> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom3<T1, T2, T3> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom3<T1, T2, T3> {
        return limit(1)
    }

    override fun distinct(): SelectFrom3<T1, T2, T3> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom3<T1, T2, T3> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField3<SelectField, T1, T2, T3>): SelectFrom3<T1, T2, T3> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom3<T1, T2, T3> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField3<OrderByField, T1, T2, T3>): SelectFrom3<T1, T2, T3> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom3<T1, T2, T3> {
        super.ifNoValue(strategy)
        return this
    }
}
