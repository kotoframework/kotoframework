package com.kotoframework.function.update

import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.Where
import com.kotoframework.definition.*
import com.kotoframework.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.condition.Criteria
import com.kotoframework.core.condition.arbitrary
import com.kotoframework.core.condition.eq
import com.kotoframework.core.condition.isNull
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.rmRedudantBlk
import com.kotoframework.utils.Extension.tableName

/**
 * Created by ousc on 2022/4/18 13:22
 */
class UpdateAction<T : KPojo>(
    private val KPojo: T,
    val fields: List<Field>,
    private var sql: String = "",
    val paramMap: MutableMap<String, Any?> = mutableMapOf(),
    val jdbcWrapper: KotoJdbcWrapper?
) : KPojo {
    fun except(vararg fields: Field): UpdateAction<T> {
        init(fields.map { it.fd }.toList())
        return this
    }

    init {
        init()
    }

    fun init(expects: List<KotoField> = listOf()) {
        val build = if (fields.isEmpty()) {
            UpdateSetClause(KPojo, excepted = expects).build()
        } else {
            UpdateSetClause(KPojo, excepted = expects) {
                Criteria(
                    type = ConditionType.AND,
                    collections = fields.map { it.columnName.lineToHump().eq(reName = it.propertyName) })
            }.build()
        }
        sql = "update ${KPojo.tableName} set ${build.sql}"
        paramMap.putAll(build.paramMap)
    }

    /**
     * > The function `where` returns an instance of `OperateWhere<T>` which is a subclass of `Operate<T>`
     *
     * The function `where` takes an optional parameter `addCondition` of type `AddCondition<T>?` which is a function that
     * takes a parameter of type `T` and returns nothing
     *
     * @param addCondition AddCondition<T>? = null
     * @return Where<T>
     */
    fun where(addCondition: AddCondition<T>? = null): UpdateWhere<T> {
        return Where(KPojo, kotoJdbcWrapper = jdbcWrapper) { addCondition?.invoke(KPojo) }.map(*paramMap.toList().toTypedArray())
            .prefixOW("$sql where ").getUpdateWhere()
    }

    fun where(vararg condition: Criteria?): UpdateWhere<T> {
        return Where(KPojo, kotoJdbcWrapper = jdbcWrapper) { condition.toList().arbitrary() }.map(*paramMap.toList().toTypedArray())
            .prefixOW("$sql where ").getUpdateWhere()
    }

    fun byId(id: Number = paramMap["id@New"] as Number): KotoOperationSet<UpdateSetClause<T>, T> {
        paramMap["id"] = id
        return KotoOperationSet(
            "$sql where id = :id".rmRedudantBlk(),
            paramMap
        )
    }

    /**
     * > This function is used to filter the data by ids and return a new KotoOperationSet with the sql and paramMap updated to include the where clause
     *
     * @param ids The list of ids to be queried
     * @return A KotoOperationSet object.
     */
    fun byIds(ids: List<Number>): KotoOperationSet<UpdateSetClause<T>, T> {
        paramMap["ids"] = ids
        paramMap["updateTime"] = currentTime
        return KotoOperationSet(
            "$sql where ids in (:ids)".rmRedudantBlk(),
            paramMap
        )
    }

    /**
     * > It takes a variable number of strings, and returns a `KotoOperationSet` object with the sql and paramMap updated to include the where clause
     * @param fields The fields to be used in the where clause.
     * @return A KotoOperationSet
     */
    fun by(
        vararg fields: Field
    ): KotoOperationSet<UpdateWhere<T>, T> {
        val columns = mutableListOf<Pair<String, Boolean>>()
        fields.forEach { field ->
            columns.add(field.columnName.lineToHump() to (field is Pair<*, *>))
            if (field is Pair<*, *>)
                paramMap[field.columnName.lineToHump()] = field.second
        }
        val koto = Where(
            KPojo
        ) {
            columns.map { if (it.second && paramMap[it.first] == null) it.first.isNull() else it.first.eq() }
                .arbitrary()
        }
            .prefixOW("$sql where ")
            .map(
                *fields.mapNotNull { if (it is Pair<*, *>) (it.first!!.columnName.lineToHump() to it.second) else null }
                    .toTypedArray()
            )
            .build()
        paramMap.putAll(koto.paramMap)
        return KotoOperationSet(koto.sql, paramMap)
    }
}
