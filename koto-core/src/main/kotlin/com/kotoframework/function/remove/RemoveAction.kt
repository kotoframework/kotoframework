package com.kotoframework.function.remove

import com.kotoframework.core.condition.BaseCondition
import com.kotoframework.core.condition.eq
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.Where
import com.kotoframework.definition.AddCondition
import com.kotoframework.ConditionType
import com.kotoframework.*
import com.kotoframework.core.condition.arbitrary
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.isNull
import com.kotoframework.core.where.OperateWhere
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Common.rmRedudantBlk
import com.kotoframework.utils.Common.toMap

/**
 * Created by ousc on 2022/4/18 13:22
 */
class RemoveAction<T : KPojo>(
    private val tableName: String,
    private val KPojo: T,
    private val jdbcWrapper: KotoJdbcWrapper? = null
) {
    var sql = "delete from $tableName"
    val paramMap = mutableMapOf<String, Any?>()

    init {
        paramMap.putAll(KPojo.toMap())
    }

    fun where(addCondition: AddCondition<T>? = null): RemoveWhere<T> {
        paramMap["updateTime"] = currentTime
        return Where(KPojo, jdbcWrapper) { addCondition?.invoke(KPojo) }.map(
            *paramMap.toList().toTypedArray()
        ).prefixOW("$sql where ").getRemoveWhere()
    }

    fun where(vararg condition: BaseCondition?): RemoveWhere<T> {
        paramMap["updateTime"] = currentTime
        return Where(KPojo, jdbcWrapper) { condition.toList().arbitrary() }.map(
            *paramMap.toList().toTypedArray()
        ).prefixOW("${this.sql} where ").getRemoveWhere()
    }

    fun soft(): RemoveAction<T> {
        sql = "update $tableName set ${deleted(1, jdbcWrapper, tableName)}, update_time = :updateTime"
        paramMap["updateTime"] = currentTime
        return this
    }

    fun byId(id: Number = paramMap["id"] as Number): KotoOperationSet<RemoveWhere<T>, T> {
        paramMap["id"] = id
        paramMap["updateTime"] = currentTime
        return KotoOperationSet(
            sql = "$sql where id = :id".rmRedudantBlk(),
            paramMap = paramMap
        )
    }

    fun byIds(ids: List<Number>): KotoOperationSet<RemoveWhere<T>, T> {
        paramMap["ids"] = ids
        paramMap["updateTime"] = currentTime
        return KotoOperationSet(
            sql = "$sql where id in (:ids)".rmRedudantBlk(),
            paramMap = paramMap
        )
    }

    fun by(
        vararg fields: Pair<String, Any?>
    ): KotoOperationSet<RemoveWhere<T>, T> {
        fields.forEach { field ->
            paramMap[field.first] = field.second
        }
        val columns = fields.map { it.first }
        val koto = Where(
            KPojo, jdbcWrapper
        ) {
            columns.map { if (paramMap[it] == null) it.isNull() else it.eq() }.arbitrary()
        }
            .map(*fields, "updateTime" to currentTime)
            .prefixOW("$sql where ")
            .build()
        return KotoOperationSet(koto.sql, koto.paramMap)
    }
}
