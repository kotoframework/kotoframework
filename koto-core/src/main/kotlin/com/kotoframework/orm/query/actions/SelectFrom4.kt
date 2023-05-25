package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin5
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom4<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction4<T1, T2, T3, T4>(
    t1,
    t2,
    t3,
    t4,
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

    fun on(addCriteria: AddCriteria4<T1, T2, T3, T4>): SelectFrom4<T1, T2, T3, T4> {
        this.onCriteria[t4!!.tableName] = criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!) }.criteria
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField4<F, T1, T2, T3, T4>,
        action: SelectFrom4<T1, T2, T3, T4>.(Array<Field>) -> SelectFrom4<T1, T2, T3, T4>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field) = super.select(*fields) as SelectFrom4<T1, T2, T3, T4>

    fun select(addField: AddField4<SelectField, T1, T2, T3, T4>): SelectFrom4<T1, T2, T3, T4> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom4<T1, T2, T3, T4> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria4<T1, T2, T3, T4>): SelectFrom4<T1, T2, T3, T4> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!) }.criteria)
        return this
    }

    inline fun <reified K : KPojo> join(
        t5: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin5<T1, T2, T3, T4, K>
    ) = join(SelectFrom5(t1, t2, t3, t4, t5, kJdbcWrapper)).apply { joinTypes[t5.tableName] = joinType }
        .action(t1!!, t2!!, t3!!, t4!!, t5)

    override fun limit(limit: Int): SelectFrom4<T1, T2, T3, T4> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom4<T1, T2, T3, T4> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom4<T1, T2, T3, T4> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom4<T1, T2, T3, T4> {
        return limit(1)
    }

    override fun distinct(): SelectFrom4<T1, T2, T3, T4> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom4<T1, T2, T3, T4> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField4<SelectField, T1, T2, T3, T4>): SelectFrom4<T1, T2, T3, T4> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom4<T1, T2, T3, T4> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField4<OrderByField, T1, T2, T3, T4>): SelectFrom4<T1, T2, T3, T4> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom4<T1, T2, T3, T4> {
        super.ifNoValue(strategy)
        return this
    }
}
