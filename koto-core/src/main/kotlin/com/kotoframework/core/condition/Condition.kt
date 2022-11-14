package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.LikePosition
import kotlin.reflect.KCallable

/**
 * Created by sundaiyue on 2022/3/25 09:30
 */
class Condition(private var parameterName: String = "Unknown", private val kCallable: KCallable<*>? = null) {

    private fun getReName(suffix: String = ""): String {
        return parameterName + suffix
    }

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

    fun notLike(
        expression: String?,
        tableName: String? = null
    ): LikeCriteria {
        return like(expression, true, tableName)
    }

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

    fun notEq(
        value: Any?,
        tableName: String? = null
    ): Criteria {
        return eq(value, true, tableName)
    }

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

    inline fun <reified T : Comparable<T>> notBetween(
        range: ClosedRange<T>?,
        tableName: String? = null
    ): Criteria {
        return between(range, true, tableName)
    }


    /**
     * If the condition is true, then return the condition
     *
     * @param reName The name of the parameter in the SQL statement.
     * @param humpToLine If true, the humpToLine function will be called.
     * @return Nothing.
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
     * If the condition is true, then return the condition
     *
     * @param reName The name of the parameter in the SQL statement.
     * @param humpToLine If true, the humpToLine function will be called.
     * @return Nothing.
     */
    fun notIn(
        list: Collection<*>?,
        tableName: String? = null
    ): Criteria {
        return isIn(list, true, tableName)
    }

    /**
     * If the condition is null, return null
     *
     * @param humpToLine If true, the parameter name will be converted to hump style.
     * @return Nothing.
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
     * If the `iif` parameter is not null, then return a `Criteria` object with the `type` property set to
     * `ConditionType.NOTNULL`
     *
     * @param humpToLine If true, the parameter name will be converted to hump style.
     * @return Nothing.
     */
    fun notNull(
        tableName: String? = null
    ): Criteria {
        return isNull(true, tableName)
    }
}
