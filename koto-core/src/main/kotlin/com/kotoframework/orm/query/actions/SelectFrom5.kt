package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin6
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom5<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction5<T1, T2, T3, T4, T5>(
    t1,
    t2,
    t3,
    t4,
    t5,
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


    fun on(addCriteria: AddCriteria5<T1, T2, T3, T4, T5>): SelectFrom5<T1, T2, T3, T4, T5> {
        this.onCriteria[t5!!.tableName] = criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!) }.kotlinAST
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField5<F, T1, T2, T3, T4, T5>,
        action: SelectFrom5<T1, T2, T3, T4, T5>.(Array<Field>) -> SelectFrom5<T1, T2, T3, T4, T5>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field) = super.select(*fields) as SelectFrom5<T1, T2, T3, T4, T5>

    fun select(addField: AddField5<SelectField, T1, T2, T3, T4, T5>): SelectFrom5<T1, T2, T3, T4, T5> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom5<T1, T2, T3, T4, T5> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria5<T1, T2, T3, T4, T5>): SelectFrom5<T1, T2, T3, T4, T5> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!) }.kotlinAST)
        return this
    }

    inline fun <reified K : KPojo> join(
        t6: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin6<T1, T2, T3, T4, T5, K>
    ) = join(SelectFrom6(t1, t2, t3, t4, t5, t6, kJdbcWrapper)).apply { joinTypes[t6.tableName] = joinType }
        .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6)

    override fun limit(limit: Int): SelectFrom5<T1, T2, T3, T4, T5> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom5<T1, T2, T3, T4, T5> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom5<T1, T2, T3, T4, T5> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom5<T1, T2, T3, T4, T5> {
        return limit(1)
    }

    override fun distinct(): SelectFrom5<T1, T2, T3, T4, T5> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom5<T1, T2, T3, T4, T5> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField5<SelectField, T1, T2, T3, T4, T5>): SelectFrom5<T1, T2, T3, T4, T5> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom5<T1, T2, T3, T4, T5> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField5<OrderByField, T1, T2, T3, T4, T5>): SelectFrom5<T1, T2, T3, T4, T5> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom5<T1, T2, T3, T4, T5> {
        super.ifNoValue(strategy)
        return this
    }
}
