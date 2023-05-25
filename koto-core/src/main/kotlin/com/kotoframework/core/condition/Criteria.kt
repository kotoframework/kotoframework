package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.enums.ConditionType
import com.kotoframework.enums.LikePosition
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.utils.SqlGenerator
import com.kotoframework.utils.humpToLine
import com.kotoframework.utils.lineToHump

/**
 * Created by ousc on 2022/4/18 10:55
 **/

/**
 * Criteria
 *
 * @property parameterName
 * @property not
 * @property type
 * @property pos
 * @property aliasName
 * @property sql
 * @property noValueStrategy
 * @property value
 * @property tableName
 * @property kCallable
 * @property children
 * @constructor Create empty Criteria
 * @author ousc
 */
@Suppress("leaking_this")
class Criteria(
    var parameterName: String = "", // original parameter name
    var type: ConditionType, // condition type
    var not: Boolean = false, // whether the condition is not
    var value: Any? = null, // value
    val tableName: String? = "", // table name
    var pos: LikePosition? = Never, // like position
    var sql: String = "", // sql
    var noValueStrategy: NoValueStrategy = KotoApp.defaultNoValueStrategy, // when the value is null, whether to generate sql
) {
    init {
        if (type != EQUAL && noValueStrategy == Ignore) {
            noValueStrategy = Smart
        }
    }

    internal val valueAcceptable: Boolean
        get() = type != ISNULL && type != SQL && type != AND && type != OR

    var children: MutableList<Criteria?> = mutableListOf()
    fun addCriteria(criteria: Criteria?) {
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