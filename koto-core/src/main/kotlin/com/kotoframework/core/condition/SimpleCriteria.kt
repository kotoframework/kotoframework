package com.kotoframework.core.condition

import com.kotoframework.EQUAL
import com.kotoframework.Ignore
import com.kotoframework.KotoApp
import com.kotoframework.Smart
import com.kotoframework.enums.ConditionType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.utils.humpToLine
import com.kotoframework.utils.lineToHump

class SimpleCriteria(
    val parameterName: String,
    var aliasName: String? = null, // rename the parameter name in the sql and the paramMap
    val type: ConditionType? = null,
    val not: Boolean = false,
    val value: Any?,
    val tableName: String? = null,
    var noValueStrategy: NoValueStrategy = KotoApp.defaultNoValueStrategy, // when the value is null, whether to generate sql
) {
    val children: MutableList<SimpleCriteria?> = mutableListOf()
    fun addCriteria(criteria: SimpleCriteria?) {
        children.add(criteria)
    }
    init {
        if (type != EQUAL && noValueStrategy == Ignore) {
            noValueStrategy = Smart
        }
    }

}

fun lineToHump(str: String): String {
    return str.lineToHump()
}

fun humpToLine(str: String): String {
    return str.humpToLine()
}