package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin9
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom8<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    override var t6: T6? = null,
    override var t7: T7? = null,
    override var t8: T8? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction8<T1, T2, T3, T4, T5, T6, T7, T8>(
    t1, t2, t3, t4, t5, t6, t7, t8, unknown, unknown, unknown, unknown, unknown, unknown, unknown, unknown, kJdbcWrapper
) {
    fun on(addCriteria: AddCriteria8<T1, T2, T3, T4, T5, T6, T7, T8>): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        this.onCriteria[t8!!.tableName] =
            criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!) }.kotlinAST
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField8<F, T1, T2, T3, T4, T5, T6, T7, T8>,
        action: SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>.(Array<Field>) -> SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        return super.select(*fields) as SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>
    }

    fun select(addField: AddField8<SelectField, T1, T2, T3, T4, T5, T6, T7, T8>): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        this.autoWhere = true
        return this
    }

    fun where(criteria: Criteria? = null): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        whereCriteria.add(criteria)
        return this
    }

    fun where(addCriteria: AddCriteria8<T1, T2, T3, T4, T5, T6, T7, T8>): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!) }.kotlinAST)
        return this
    }

    inline fun <reified K : KPojo> join(
        t9: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin9<T1, T2, T3, T4, T5, T6, T7, T8, K>
    ) = join(SelectFrom9(t1, t2, t3, t4, t5, t6, t7, t8, t9, wrapper)).apply {
        joinTypes[t9.tableName] = joinType
    }.action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9)

    override fun limit(limit: Int): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        return limit(1)
    }

    override fun distinct(): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField8<SelectField, T1, T2, T3, T4, T5, T6, T7, T8>): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField8<OrderByField, T1, T2, T3, T4, T5, T6, T7, T8>): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
        super.ifNoValue(strategy)
        return this
    }
}
