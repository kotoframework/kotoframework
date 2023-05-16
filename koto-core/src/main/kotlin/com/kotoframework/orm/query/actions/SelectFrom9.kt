package com.kotoframework.orm.query.actions

import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.orm.query.javaInstance
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin10
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom9<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo>(
    override var t1: T1? = null,
    override var t2: T2? = null,
    override var t3: T3? = null,
    override var t4: T4? = null,
    override var t5: T5? = null,
    override var t6: T6? = null,
    override var t7: T7? = null,
    override var t8: T8? = null,
    override var t9: T9? = null,
    val kJdbcWrapper: KJdbcWrapper? = null
) : QueryAction9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    t1, t2, t3, t4, t5, t6, t7, t8, t9, unknown, unknown, unknown, unknown, unknown, unknown, unknown, kJdbcWrapper
) {
    fun on(addCriteria: AddCriteria9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        this.onCriteria[t9!!.tableName] =
            criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!) }.kotlinAST
        return this
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField9<F, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
        action: SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>.(Array<Field>) -> SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
    ) = action(apply { addField(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!) }.fields.toTypedArray())

    override fun select(vararg fields: Field): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        return super.select(*fields) as SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
    }

    fun select(addField: AddField9<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9>): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        return selectField.fieldHandler(addField) { select(*it) }
    }

    fun where(): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        whereCriteria.add(criteriaField { addCriteria(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!) }.kotlinAST)
        return this
    }

    inline fun <reified K : KPojo> join(
        t10: K = K::class.javaInstance(), joinType: JoinType, action: AddJoin10<T1, T2, T3, T4, T5, T6, T7, T8, T9, K>
    ) = join(SelectFrom10(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, wrapper)).apply {
        joinTypes[t10.tableName] = joinType
    }.action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10)

    override fun limit(limit: Int): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        return limit(1)
    }

    override fun distinct(): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField9<SelectField, T1, T2, T3, T4, T5, T6, T7, T8, T9>): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField9<OrderByField, T1, T2, T3, T4, T5, T6, T7, T8, T9>): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        super.ifNoValue(strategy)
        return this
    }
}
