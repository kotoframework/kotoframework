package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin8
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom7<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    override var t6: T6? = null,
    override var t7: T7? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction7<T1, T2, T3, T4, T5, T6, T7>(
    t1,
    t2,
    t3,
    t4,
    t5,
    t6,
    t7,
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
    fun on(addCriteria: AddCriteria7<T1, T2, T3, T4, T5, T6, T7>): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        this.onCriteria[t7!!.tableName] =
            criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!) }.kotlinAST
        return this
    }

    private fun <F: SelectField> F.fieldHandler(
        addField: AddField7<F, T1, T2, T3, T4, T5, T6, T7>,
        action: SelectFrom7<T1, T2, T3, T4, T5, T6, T7>.(Array<Field>) -> SelectFrom7<T1, T2, T3, T4, T5, T6, T7>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field) = super.select(*fields) as SelectFrom7<T1, T2, T3, T4, T5, T6, T7>

    fun select(addField: AddField7<SelectField, T1, T2, T3, T4, T5, T6, T7>): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria7<T1, T2, T3, T4, T5, T6, T7>): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!) }.kotlinAST)
        return this
    }

    inline fun <reified K : KPojo> join(
        t8: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin8<T1, T2, T3, T4, T5, T6, T7, K>
    ) = join(SelectFrom8(t1, t2, t3, t4, t5, t6, t7, t8, wrapper)).apply { joinTypes[t8.tableName] = joinType }
        .action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8)

    override fun limit(limit: Int): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        return limit(1)
    }

    override fun distinct(): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField7<SelectField, T1, T2, T3, T4, T5, T6, T7>): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField7<OrderByField, T1, T2, T3, T4, T5, T6, T7>): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
        super.ifNoValue(strategy)
        return this
    }
}
