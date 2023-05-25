package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin7
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom6<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    override var t6: T6? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction6<T1, T2, T3, T4, T5, T6>(
    t1,
    t2,
    t3,
    t4,
    t5,
    t6,
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

    fun on(addCriteria: AddCriteria6<T1, T2, T3, T4, T5, T6>): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        this.onCriteria[t6!!.tableName] = criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!) }.criteria
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField6<F, T1, T2, T3, T4, T5, T6>,
        action: SelectFrom6<T1, T2, T3, T4, T5, T6>.(Array<Field>) -> SelectFrom6<T1, T2, T3, T4, T5, T6>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        return super.select(*fields) as SelectFrom6<T1, T2, T3, T4, T5, T6>
    }

    fun select(addField: AddField6<SelectField, T1, T2, T3, T4, T5, T6>): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria6<T1, T2, T3, T4, T5, T6>): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!) }.criteria)
        return this
    }

    inline fun <reified K : KPojo> join(
        t7: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin7<T1, T2, T3, T4, T5, T6, K>
    ): SelectFrom7<T1, T2, T3, T4, T5, T6, K> {
        return join(SelectFrom7(t1, t2, t3, t4, t5, t6, t7, kJdbcWrapper)).apply { joinTypes[t7.tableName] = joinType }
            .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7)
    }

    override fun limit(limit: Int): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        return limit(1)
    }

    override fun distinct(): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField6<SelectField, T1, T2, T3, T4, T5, T6>): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField6<OrderByField, T1, T2, T3, T4, T5, T6>): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom6<T1, T2, T3, T4, T5, T6> {
        super.ifNoValue(strategy)
        return this
    }
}
