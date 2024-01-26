package com.kotoframework.function.update

import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.annotations.NeedTableIndexes
import com.kotoframework.core.where.Where
import com.kotoframework.definition.*
import com.kotoframework.core.condition.*
import com.kotoframework.function.create.CreateWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.rmRedundantBlk
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
        init(fields.map { it.toColumn() }.toList())
        return this
    }

    init {
        init()
    }

    fun init(expects: List<ColumnMeta> = listOf()) {
        val build = if (fields.isEmpty()) {
            UpdateSetClause(KPojo, expects).build()
        } else {
            UpdateSetClause(KPojo, expects) {
                fields.map { it.columnName.lineToHump().eq().alias(it.propertyName) }.arbitrary()
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
        return Where(KPojo, jdbcWrapper) { addCondition?.invoke(KPojo) }.map(*paramMap.toList().toTypedArray())
            .prefixOW("$sql where ").getUpdateWhere()
    }

    fun where(vararg condition: Criteria?): UpdateWhere<T> {
        return Where(KPojo, jdbcWrapper) { condition.toList().arbitrary() }.map(*paramMap.toList().toTypedArray())
            .prefixOW("$sql where ").getUpdateWhere()
    }

    fun byId(id: Number = paramMap["id@New"] as Number): KotoOperationSet<UpdateSetClause<T>, T> {
        paramMap["id"] = id
        return KotoOperationSet(
            "$sql where id = :id".rmRedundantBlk(),
            paramMap,
            jdbcWrapper = jdbcWrapper
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
            "$sql where ids in (:ids)".rmRedundantBlk(),
            paramMap,
            jdbcWrapper = jdbcWrapper
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
        return KotoOperationSet(koto.sql, paramMap, jdbcWrapper = jdbcWrapper)
    }

    companion object {
        fun <K : KPojo> Collection<UpdateAction<K>>.byId(id: Number? = null): List<KotoOperationSet<UpdateSetClause<K>, K>> {
            return this.map { it.byId(id?:it.paramMap["id@New"] as Number) }
        }

        fun <K : KPojo> Collection<UpdateAction<K>>.byIds(ids: List<Number>): List<KotoOperationSet<UpdateSetClause<K>, K>> {
            return this.map { it.byIds(ids) }
        }

        fun <K : KPojo> Collection<UpdateAction<K>>.by(vararg fields: Field): List<KotoOperationSet<UpdateWhere<K>, K>> {
            return this.map { it.by(*fields) }
        }

        fun <K : KPojo> Collection<UpdateAction<K>>.where(addCondition: AddCondition<K>? = null): List<UpdateWhere<K>> {
            return this.map { it.where(addCondition) }
        }

        fun <K : KPojo> Collection<UpdateAction<K>>.where(vararg condition: Criteria?): List<UpdateWhere<K>> {
            return this.map { it.where(*condition) }
        }

        fun <K : KPojo> Collection<UpdateAction<K>>.except(vararg fields: Field): List<UpdateAction<K>> {
            return this.map { it.except(*fields) }
        }
    }
}
