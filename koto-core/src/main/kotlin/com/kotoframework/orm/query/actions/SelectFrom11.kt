package com.kotoframework.orm.query.actions

import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin12
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom11<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    override var t6: T6? = null,
    override var t7: T7? = null,
    override var t8: T8? = null,
    override var t9: T9? = null,
    override var t10: T10? = null,
    override var t11: T11? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(
    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, unknown, unknown, unknown, unknown, unknown, kJdbcWrapper
) {
    fun on(addCriteria: AddCriteria11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        this.onCriteria[t11!!.tableName] =
            criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!) }.criteria
        return this
    }

    private fun <F: SelectField> F.fieldHandler(
        addField: AddField11<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
        action: SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>.(Array<Field>) -> SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>  = super.select(*fields) as SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>

    fun select(addField: AddField11<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        whereCriteria.add(
            criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!) }.criteria
        )
        return this
    }

    inline fun <reified K : KPojo> join(
        t12: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, K>
    ) = join(
        SelectFrom12(
            t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, wrapper
        )
    ).apply { joinTypes[t12.tableName] = joinType }
        .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12)


    override fun limit(limit: Int): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        return limit(1)
    }

    override fun distinct(): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField11<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField11<OrderByField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
        super.ifNoValue(strategy)
        return this
    }
}
