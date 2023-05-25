package com.kotoframework.orm.query.actions

import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin15
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom14<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo>(
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
    override var t14: T14? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(
    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, unknown, unknown, kJdbcWrapper
) {

    fun on(addCriteria: AddCriteria14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        this.onCriteria[t14!!.tableName] = criteriaField {
            addCriteria(
                t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!
            )
        }.criteria
        return this
    }

    private fun <F: SelectField> F.fieldHandler(
        addField: AddField14<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>,
        action: SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>.(Array<Field>) -> SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field) = super.select(*fields) as SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>

    fun select(addField: AddField14<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        whereCriteria.add(
            criteriaField {
                addCriteria(
                    t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!
                )
            }.criteria
        )
        return this
    }

    inline fun <reified K : KPojo> join(
        t15: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, K>
    ): SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, K> {
        return join(
            SelectFrom15(
                t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, wrapper
            )
        ).apply { joinTypes[t15.tableName] = joinType }
            .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!, t15)
    }

    override fun limit(limit: Int): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.offset(offset)
        return this
    }

    override fun page(
        pageIndex: Int, pageSize: Int
    ): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        return limit(1)
    }

    override fun distinct(): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField14<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField14<OrderByField, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
        super.ifNoValue(strategy)
        return this
    }
}
