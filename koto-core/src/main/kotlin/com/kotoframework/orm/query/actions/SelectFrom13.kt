package com.kotoframework.orm.query.actions

import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin14
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom13<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo>(
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
    override var t12: T12? = null,
    override var t13: T13? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(
    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, unknown, unknown, unknown, kJdbcWrapper
) {

    fun on(addCriteria: AddCriteria13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        this.onCriteria[t13!!.tableName] = criteriaField {
            addCriteria(
                t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!
            )
        }.kotlinAST
        return this
    }

    private fun <F: SelectField> F.fieldHandler(
        addField: AddField13<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>,
        action: SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>.(Array<Field>) -> SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field) = super.select(*fields) as SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>

    fun select(addField: AddField13<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        whereCriteria.add(
            criteriaField {
                addCriteria(
                    t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!
                )
            }.kotlinAST
        )
        return this
    }

    inline fun <reified K : KPojo> join(
        t14: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, K>
    ): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, K> {
        return join(
            SelectFrom14(
                t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, wrapper
            )
        ).apply { joinTypes[t14.tableName] = joinType }
            .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14)
    }

    override fun limit(limit: Int): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.offset(offset)
        return this
    }

    override fun page(
        pageIndex: Int, pageSize: Int
    ): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        return limit(1)
    }

    override fun distinct(): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField13<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField13<OrderByField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
        super.ifNoValue(strategy)
        return this
    }
}
