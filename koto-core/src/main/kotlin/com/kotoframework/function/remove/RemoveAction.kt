package com.kotoframework.function.remove

import com.kotoframework.core.condition.Criteria
import com.kotoframework.core.condition.eq
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.Where
import com.kotoframework.definition.AddCondition
import com.kotoframework.core.condition.arbitrary
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.isNull
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Extension.rmRedundantBlk
import com.kotoframework.utils.Extension.tableMeta
import com.kotoframework.utils.Extension.toMap

/**
 * Created by ousc on 2022/4/18 13:22
 */
class RemoveAction<T : KPojo>(
    private val tableName: String,
    private val kPojo: T,
    private val jdbcWrapper: KotoJdbcWrapper? = null
) {
    var sql = "delete from $tableName"
    val paramMap = mutableMapOf<String, Any?>()

    init {
        paramMap.putAll(kPojo.toMap())
    }

    fun soft(): RemoveAction<T> {
        val tableMeta = kPojo.tableMeta
        sql = "update $tableName set ${deleted(1, jdbcWrapper, tableName)}, ${
            if (tableMeta.deleteTime.enabled) {
                paramMap[tableMeta.deleteTime.alias] = currentTime
                "`${tableMeta.deleteTime.column}` = :${tableMeta.deleteTime.alias}"
            } else {
                ""
            }
        }"
        return this
    }

    fun byId(id: Number = paramMap["id"] as Number): KotoOperationSet<RemoveWhere<T>, T> {
        paramMap["id"] = id
        return KotoOperationSet(
            sql = "$sql where id = :id".rmRedundantBlk(),
            paramMap = paramMap,
            jdbcWrapper = jdbcWrapper
        )
    }

    fun byIds(ids: List<Number>): KotoOperationSet<RemoveWhere<T>, T> {
        paramMap["ids"] = ids
        return KotoOperationSet(
            sql = "$sql where id in (:ids)".rmRedundantBlk(),
            paramMap = paramMap,
            jdbcWrapper = jdbcWrapper
        )
    }

    fun by(
        vararg fields: Pair<String, Any?>
    ): KotoOperationSet<RemoveWhere<T>, T> {
        fields.forEach { field ->
            paramMap[field.first] = field.second
        }
        val columns = fields.map { it.first }
        val (sql) = Where(
            kPojo, jdbcWrapper
        ) {
            columns.map { if (paramMap[it] == null) it.isNull() else it.eq() }.arbitrary()
        }
            .map(*fields)
            .prefixOW("$sql where ")
            .build()
        return KotoOperationSet(sql, paramMap, jdbcWrapper = jdbcWrapper)
    }

    fun where(addCondition: AddCondition<T>? = null): RemoveWhere<T> {
        return Where(kPojo, jdbcWrapper) { addCondition?.invoke(kPojo) }.map(
            *paramMap.toList().toTypedArray()
        ).prefixOW("$sql where ").getRemoveWhere()
    }

    fun where(vararg condition: Criteria?): RemoveWhere<T> {
        return Where(kPojo, jdbcWrapper) { condition.toList().arbitrary() }.map(
            *paramMap.toList().toTypedArray()
        ).prefixOW("${this.sql} where ").getRemoveWhere()
    }
}
