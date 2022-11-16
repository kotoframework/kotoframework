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
    internal var pos: LikePosition? = Never, // like position
    var reName: String? = null, // rename the parameter name in the sql and the paramMap
    var sql: String = "", // sql
    internal var ifNoValueStrategy: NoValueStrategy = Ignore, // when the value is null, whether to generate sql
    val value: Any? = null, // value
    val tableName: String? = "", // table name
    internal val kCallable: KCallable<*>? = null, // the property of the kCallable
    internal val collections: List<Criteria?> = mutableListOf() // collection of conditions
) {
    init {
        sql = SqlGenerator.generate(this)
    }

    internal val valueAcceptable: Boolean
        get() = type != ISNULL && type != SQL && type != AND && type != OR
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
 * @param ifNoValueStrategy
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
    ifNoValueStrategy: NoValueStrategy = Ignore,
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
    ifNoValueStrategy,
    value,
    tableName,
    kCallable,
    collections
)
