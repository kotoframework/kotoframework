package com.kotoframework.function.update

import com.kotoframework.core.condition.eq
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.BaseWhere
import com.kotoframework.*
import com.kotoframework.definition.*
import com.kotoframework.definition.columnName
import com.kotoframework.definition.propertyName
import com.kotoframework.interfaces.KPojo
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Common.isNullOrBlank
import com.kotoframework.utils.Common.lineToHump
import com.kotoframework.utils.Common.rmRedudantBlk
import kotlin.reflect.full.declaredMemberProperties

/**
 * Created by ousc on 2022/5/10 02:31
 */
class UpdateSetClause<T : KPojo>(
    val kPojo: T,
    val excepted: List<KotoField> = listOf(),
    addCondition: AddCondition<T>? = null,
) : BaseWhere<T>(kPojo, addCondition) {
    private var prefix = ""
    private var suffix = ""

    /**
     * `prefix` is a function that takes a String as an argument and returns an UpdateWhere object
     *
     * @param sql The SQL statement to be executed.
     * @return The UpdateWhere object
     */
    fun prefix(sql: String): UpdateSetClause<T> {
        this.prefix = sql
        return this
    }

    /**
     * `suffix` is a function that takes a String as an argument and returns an UpdateWhere object
     *
     * @param sql The SQL statement to be executed.
     * @return The UpdateWhere object
     */
    fun suffix(sql: String): UpdateSetClause<T> {
        this.suffix = sql
        return this
    }

    /**
     * > This function is used to build the SQL statement and the parameters for the SQL statement
     *
     * @return A KotoOperationSet object.
     */
    override fun build(): KotoOperationSet<UpdateSetClause<T>, T> {
        val pre = conditions.toList()
        pre.forEach {
            if (it?.type == AND) {
                conditions.addAll(it.collections)
            }
        }
        conditions = conditions.filterNotNull().toMutableList()

        if (conditions.isEmpty()) {
            kPojo::class.declaredMemberProperties.forEach {
                conditions.add(it.columnName.lineToHump().eq(reName = it.propertyName))
            }
        }

        conditions.add("updateTime".eq())
        paramMap["updateTime"] = currentTime

        conditions = conditions.distinctBy { it!!.reName }
            .filter { condition -> condition!!.type == EQUAL && !excepted.map { it.propertyName }.contains(condition.parameterName) }
            .toMutableList()

        for ((key, value) in finalMap) {
            paramMap[key] = value
        }

        val sqls = mutableListOf<String>()
        conditions.forEach {
            val realName = when {
                !it!!.reName.isNullOrBlank() -> it.reName
                !it.parameterName.isNullOrBlank() -> it.parameterName
                else -> ""
            }!!
            if (!it.value.isNullOrBlank()) {
                paramMap[realName] = it.value
            }
            sqls.add(it.sql + "@New")
        }

        return KotoOperationSet("$prefix ${sqls.joinToString(", ")} $suffix".rmRedudantBlk(), paramMap.mapKeys { it.key + "@New" })
    }
}
