package com.kotoframework.core.condition

import com.kotoframework.*

/**
 * Created by sundaiyue on 2022/3/25 09:30
 */

/**
 * Condition
 *
 * @property parameterName
 * @property kCallable
 * @constructor Create empty Condition
 * @author ousc
 */
class Condition(private var parameterName: String = "Unknown") {

    /**
     * `getReName` is a function that returns a string.
     *
     * @param suffix
     * @return String
     * @author ousc
     */
    private fun getReName(suffix: String = ""): String {
        return parameterName + suffix
    }

    /**
     * `eq` is a function that returns a `Criteria` object
     *
     * @param value The value of the field to be queried
     * @param not Whether to use the not equal operator
     * @param tableName The table name to be queried.
     * @return Criteria
     * @author ousc
     */
    fun eq(
        value: Any?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = EQUAL,
            aliasName = getReName(),
            value = value,
            not = not,
            tableName = tableName,
        )
    }

    /**
     * `notEq` is a function that takes a value and a table name and returns a `Criteria` object.
     *
     * @param value The value to compare against.
     * @param tableName The name of the table to use in the query.
     * @return Criteria
     * @author ousc
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
     * @author ousc
     */
    fun gt(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = GT,
            aliasName = getReName("Min"),
            value = value,
            tableName = tableName,
        )
    }

    /**
     * It returns a Criteria object.
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     * @author ousc
     */
    fun ge(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = GE,
            aliasName = getReName("Min"),
            value = value,
            tableName = tableName,
        )
    }

    /**
     * `lt` is a function that returns a `Criteria` object
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     * @author ousc
     */
    fun lt(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = LT,
            aliasName = getReName("Max"),
            value = value,
            tableName = tableName,
        )
    }

    /**
     * `le` is a function that returns a `Criteria` object
     *
     * @param value The value of the condition
     * @param tableName The table name to be queried.
     * @return Criteria
     * @author ousc
     */
    fun le(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = LE,
            aliasName = getReName("Max"),
            value = value,
            tableName = tableName,
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
     * @author ousc
     */
    fun between(
        range: ClosedRange<*>?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = BETWEEN,
            aliasName = getReName(),
            value = range,
            not = not,
            tableName = tableName,
        )
    }

   /**
     * `notBetween` is a function that takes a range and a table name and returns a `Criteria` object.
     *
     * @param range The range of values to be queried.
     * @param tableName The name of the table to use in the query.
     * @return Criteria
     * @author ousc
     */
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
     * @author ousc
     */
    fun isIn(
        list: Collection<*>?,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = IN,
            aliasName = getReName(),
            value = list,
            not = not,
            tableName = tableName,
        )
    }


    /**
     * `notIn` is a function that takes a `Collection<*>?` and a `String?` and returns a `Criteria`
     *
     * @param list The list of values to check against.
     * @param tableName The name of the table to use in the query.
     * @return Criteria
     * @author ousc
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
     * @author ousc
     */
    fun isNull(
        not: Boolean = false,
        tableName: String? = null
    ): Criteria {
        return Criteria(
            parameterName = parameterName,
            type = ISNULL,
            aliasName = parameterName,
            not = not,
            tableName = tableName,
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
     * @author ousc
     */
    fun notNull(
        tableName: String? = null
    ): Criteria {
        return isNull(true, tableName)
    }
}
