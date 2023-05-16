package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.enums.ConditionType
import com.kotoframework.enums.LikePosition
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.utils.SqlGenerator

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
@Suppress("LeakingThis")
open class Criteria(
    internal open var parameterName: String = "", // original parameter name
    var type: ConditionType? = null, // condition type
    var not: Boolean = false, // whether the condition is not
    var pos: LikePosition? = Never, // like position
    internal var aliasName: String? = null, // rename the parameter name in the sql and the paramMap
    internal val tableName: String? = "", // table name
    internal var value: Any? = null, // value
    internal var sql: String = "", // sql
    internal var noValueStrategy: NoValueStrategy = KotoApp.defaultNoValueStrategy, // when the value is null, whether to generate sql
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

    var children: MutableList<Criteria?> = mutableListOf()
    fun addCriteria(criteria: Criteria?) {
        children.add(criteria)
    }
}