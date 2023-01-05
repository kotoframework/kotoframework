package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.utils.SqlGenerator
import kotlin.reflect.KCallable

/**
 * Created by ousc on 2022/4/18 10:55
 **/
@Suppress("LeakingThis")
open class Criteria(
    internal open val parameterName: String = "", // original parameter name
    internal open var not: Boolean = false, // whether the condition is not
    internal open var type: ConditionType? = null, // condition type
    internal open var pos: LikePosition? = Never, // like position
    internal open var reName: String? = null, // rename the parameter name in the sql and the paramMap
    internal open var sql: String = "", // sql
    internal open var noValueStrategy: NoValueStrategy = KotoApp.defaultNoValueStrategy, // when the value is null, whether to generate sql
    internal open val value: Any? = null, // value
    internal open val tableName: String? = "", // table name
    internal open val kCallable: KCallable<*>? = null, // the property of the kCallable
    internal open val collections: List<Criteria?> = mutableListOf() // collection of conditions
) {
    init {
        //Leaking this can cause memory leaks, because the object is not fully constructed yet, and the object is returned from the constructor.
        //To fix this, you can use the lateinit modifier, or you can use a factory method.
        sql = SqlGenerator.generate(this)
        if (type != EQUAL && noValueStrategy == Ignore) {
            noValueStrategy = Smart
        }
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
 * @param noValueStrategy
 * @param value
 * @param tableName
 * @param collections
 * @author ousc
 */
open class LikeCriteria(
    override val parameterName: String,
    override var not: Boolean = false,
    override var type: ConditionType? = LIKE,
    override var pos: LikePosition? = Never,
    override var reName: String? = null,
    override var sql: String = "",
    override var noValueStrategy: NoValueStrategy = KotoApp.defaultNoValueStrategy,
    override var value: Any? = null,
    override var tableName: String? = "",
    override var kCallable: KCallable<*>? = null,
    override var collections: List<Criteria?> = mutableListOf()
) : Criteria(
    parameterName,
    not,
    type,
    pos,
    reName,
    sql,
    noValueStrategy,
    value,
    tableName,
    kCallable,
    collections
)
