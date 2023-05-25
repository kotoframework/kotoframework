package com.kotoframework.orm.query.actions

import com.kotoframework.core.condition.Criteria
import com.kotoframework.definition.criteriaField
import com.kotoframework.definition.*
import com.kotoframework.enums.JoinType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.orm.AddJoin2
import com.kotoframework.orm.query.*
import com.kotoframework.utils.tableName

/**
 * Created by sundaiyue on 2023/4/3 02:07
 */
open class SelectFrom<T : KPojo>(override var t1: T? = null, val kJdbcWrapper: KJdbcWrapper? = null) : QueryAction1<T>(
    t1,
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
    unknown,
    unknown,
    kJdbcWrapper
) {

    init {
        super.initTables()
    }

    override fun select(vararg fields: Field): SelectFrom<T> {
        return super.select(*fields) as SelectFrom<T>
    }

    private fun <F : SelectField> F.fieldHandler(
        addField: AddField<F, T>, action: SelectFrom<T>.(Array<Field>) -> SelectFrom<T>
    ) = action(apply { addField(t1!!) }.fields.toTypedArray())

    fun select(addField: AddField<SelectField, T>): SelectFrom<T> {
        return selectField.fieldHandler(addField) {
            select(*it)
        }
    }

    fun where(): SelectFrom<T> {
        this.autoWhere = true
        return this
    }

    fun where(addCriteria: AddCriteria1<T>): SelectFrom<T> {
        whereCriteria.add(criteriaField { addCriteria(t1!!) }.criteria)
        return this
    }

    inline fun <reified K : KPojo> join(
        t2: K = K::class.javaInstance(),
        joinType: JoinType = JoinType.LEFT_JOIN,
        action: AddJoin2<T, K>
    ) = join(SelectFrom2(t1, t2, wrapper)).apply { joinTypes[t2.tableName] = joinType }
        .action(t1!!, t2)

    override fun limit(limit: Int): SelectFrom<T> {
        super.limit(limit)
        return this
    }

    override fun offset(offset: Int): SelectFrom<T> {
        super.offset(offset)
        return this
    }

    override fun page(pageIndex: Int, pageSize: Int): SelectFrom<T> {
        super.page(pageIndex, pageSize)
        return this
    }

    @Deprecated("first() is deprecated, use limit(1) instead", ReplaceWith("limit(1)"))
    override fun first(): SelectFrom<T> {
        return limit(1)
    }

    override fun distinct(): SelectFrom<T> {
        super.distinct()
        return this
    }

    override fun groupBy(vararg fields: Field): SelectFrom<T> {
        super.groupBy(*fields)
        return this
    }

    fun groupBy(addField: AddField<SelectField, T>): SelectFrom<T> {
        return selectField.fieldHandler(addField) { groupBy(*it) }
    }

    override fun orderBy(vararg fields: Field): SelectFrom<T> {
        super.orderBy(*fields)
        return this
    }

    fun orderBy(addField: AddField<OrderByField, T>): SelectFrom<T> {
        return orderByField.fieldHandler(addField) { orderBy(*it) }
    }

    override fun ifNoValue(strategy: NoValueField.(Criteria) -> NoValueStrategy): SelectFrom<T> {
        super.ifNoValue(strategy)
        return this
    }
}
