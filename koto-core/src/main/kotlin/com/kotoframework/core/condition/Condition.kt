package com.kotoframework.core.condition

import com.kotoframework.*
import kotlin.reflect.KCallable

/**
 * Created by sundaiyue on 2022/3/25 09:30
 */
class Condition(private var parameterName: String = "Unknown", private val kCallable: KCallable<*>? = null) {

    private fun getReName(suffix: String = ""): String {
        return parameterName + suffix
    }

    /**
     * `fun like(expression: String?, not: Boolean = false, tableName: String? = null): LikeCriteria`
     *
     * The function is named `like` and it takes three parameters:
     *
     * 1. `expression` is a `String?`
     * 2. `not` is a `Boolean` with a default value of `false`
     * 3. `tableName` is a `String?` with a default value of `null`
     *
     * The function returns a `LikeCriteria` object
     *
     * @param expression The expression to be used in the LIKE clause.
     * @param not Boolean = false,
     * @param tableName The table name to be used in the query.
     * @return LikeCriteria
     */
    fun like(
        expression: String?,
        not: Boolean = false,
        tableName: String? = null
    ): LikeCriteria {
        return LikeCriteria(
            parameterName = parameterName,
            value = expression,
            reName = getReName(),
            not = not,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * `notLike` is a function that takes an expression and a table name and returns a `LikeCriteria` object.
     *
     * @param expression The expression to be used in the LIKE clause.
     * @param tableName The name of the table to use in the query.
     * @return LikeCriteria
     */
    fun notLike(
        expression: String?,
        tableName: String? = null
    ): LikeCriteria {
        return like(expression, true, tableName)
    }

    /**
     * `eq` is a function that returns a `Criteria` object
     *
     * @param value The value of the field to be queried
     * @param not Whether to use the not equal operator
     * @param tableName The table name to be queried.
     * @return Criteria
     */
    fun eq(
        value: Any?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = EQUAL,
            reName = getReName(),
            value = value,
            not = not,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * `notEq` is a function that takes a value and a table name and returns a `Criteria` object.
     *
     * @param value The value to compare against.
     * @param tableName The name of the table to use in the query.
     * @return Criteria
     */
    fun notEq(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return eq(value, true, tableName)
    }

    /**
     * `gt` is a function that returns a `Criteria` object
     *
     * @param value The value of the condition
     * @param tableName The table name of the field, which is used to distinguish the field name when the field name is the
     * same.
     * @return Criteria
     */
    fun gt(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = GT,
            reName = getReName("Min"),
            value = value,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * It returns a Criteria object.
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     */
    fun ge(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = GE,
            reName = getReName("Min"),
            value = value,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * `lt` is a function that returns a `Criteria` object
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     */
    fun lt(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = LT,
            reName = getReName("Max"),
            value = value,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * `le` is a function that returns a `Criteria` object
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     */
    fun le(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = LE,
            reName = getReName("Max"),
            value = value,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /**
     * `between` is a function that returns a `Criteria` object.
     *
     * @param range The range of values to be queried.
     * @param not Boolean = false,
     * @param tableName The table name of the field, which is used to distinguish the field name when the field name is the
     * same.
     * @return A Criteria object
     */
    fun between(
        range: ClosedRange<*>?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = BETWEEN,
            reName = getReName(),
            value = range,
            not = not,
            tableName = tableName,
            kCallable = kCallable
        )
    }

    /* A function that takes a `ClosedRange<T>?` and a `String?` and returns a `Criteria` */
    inline fun <reified T : Comparable<T>> notBetween(
        range: ClosedRange<T>?,
        tableName: String? = null
    ): Criteria {
        return between(range, true, tableName)
    }

    /**
     * `fun isIn(list: Collection<*>?, not: Boolean = false, tableName: String? = null): Criteria`
     *
     * The function takes in a list of values, a boolean value, and a table name. The function returns a Criteria object
     *
     * @param list The value of the parameter, which is a collection of values.
     * @param not Whether to use not in
     * @param tableName The table name, which is used to distinguish the same field name in different tables.
     * @return Criteria
     */
    fun isIn(
        list: Collection<*>?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = IN,
            reName = getReName(),
            value = list,
            not = not,
            tableName = tableName,
            kCallable = kCallable
        )
    }


    /**
     * `notIn` is a function that takes a `Collection<*>?` and a `String?` and returns a `Criteria`
     *
     * @param list The list of values to check against.
     * @param tableName The name of the table to use in the query.
     * @return Criteria
     */
    fun notIn(
        list: Collection<*>?,
        tableName: String? = null
    ): Criteria {
        return isIn(list, true, tableName)
    }


    /**
     * `fun isNull(not: Boolean = false, tableName: String? = null): Criteria`
     *
     * The function is named `isNull` and it takes two parameters: `not` and `tableName`. The `not` parameter is a
     * `Boolean` and is optional. The `tableName` parameter is a `String` and is also optional. The function returns a
     * `Criteria` object
     *
     * @param not Whether to use the NOT operator
     * @param tableName The table name to be queried.
     * @return Criteria
     */
    fun isNull(
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = ISNULL,
            reName = parameterName,
            not = not,
            tableName = tableName,
            kCallable = kCallable
        )
    }
    /**
     * `fun notNull(tableName: String? = null): Criteria`
     *
     * The function name is `notNull` and it takes a single parameter named `tableName` of type `String?` with a default
     * value of `null`. The function returns a `Criteria` object
     *
     * @param tableName The name of the table to use. If null, the default table will be used.
     * @return Criteria
     */
    fun notNull(
        tableName: String? = null
    ): Criteria {
        return isNull(true, tableName)
    }
}
