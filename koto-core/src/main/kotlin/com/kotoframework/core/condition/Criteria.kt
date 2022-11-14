package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.utils.SqlGenerator
import kotlin.reflect.KCallable

/**
 * Created by ousc on 2022/4/18 10:55
 **/
open class Criteria(
    val parameterName: String = "", // original parameter name
    var not: Boolean = false, // whether the condition is not
    var type: ConditionType? = null, // condition type
    var pos: LikePosition? = Never, // like position
    var reName: String? = null, // rename the parameter name in the sql and the paramMap
    var sql: String = "", // sql
    var allowNull: Boolean = false, // when the value is null, whether to generate sql
    val value: Any? = null, // value
    val tableName: String? = "", // table name
    val kCallable: KCallable<*>? = null, // the property of the kCallable
    val collections: List<Criteria?> = mutableListOf() // collection of conditions
) {
    init {
        sql = SqlGenerator.generate(this)
    }
}

/**
 * Like condition
 *
 * @constructor
 *
 * @param parameterName
 * @param not
 * @param type
 * @param pos
 * @param reName
 * @param sql
 * @param allowNull
 * @param humpToLine
 * @param value
 * @param tableName
 * @param collections
 * @author ousc
 */
class LikeCriteria(
    parameterName: String,
    not: Boolean = false,
    type: ConditionType? = LIKE,
    pos: LikePosition? = Never,
    reName: String? = null,
    sql: String = "",
    allowNull: Boolean = false,
    value: Any? = null,
    tableName: String? = "",
    kCallable: KCallable<*>? = null,
    collections: List<Criteria?> = mutableListOf()
) : Criteria(
    parameterName,
    not,
    type,
    pos,
    reName,
    sql,
    allowNull,
    value,
    tableName,
    kCallable,
    collections
)
