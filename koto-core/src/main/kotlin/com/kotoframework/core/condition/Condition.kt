package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.LikePosition

/**
 * Created by ousc on 2022/3/25 09:30
 */
class Condition(var parameterName: String = "Unknown") {

    private fun getReName(reName: String?, suffix: String = ""): String {
        return if (reName.isNullOrEmpty()) parameterName + suffix else reName
    }

    fun like(
        expression: String?,
        reName: String?,
        pos: LikePosition,
        iif: Boolean?,
        humpToLine: Boolean,
        not: Boolean = false,
        tableName: String? = null
    ): LikeCondition? {
        if (iif != null && !iif) return null
        return LikeCondition(
            parameterName = parameterName,
            pos = if (expression.isNullOrEmpty()) pos else LikePosition.Never,
            value = expression,
            reName = getReName(reName),
            humpToLine = humpToLine,
            not = not,
            tableName = tableName
        )
    }

    fun notLike(
        expression: String?,
        reName: String?,
        pos: LikePosition,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): LikeCondition? {
        return like(expression, reName, pos, iif, humpToLine, true, tableName)
    }

    fun eq(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = EQUAL,
            reName = getReName(reName),
            humpToLine = humpToLine,
            value = value,
            not = not,
            tableName = tableName
        )
    }

    fun notEq(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        return eq(value, reName, iif, humpToLine, true, tableName)
    }

    fun gt(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = GT,
            reName = getReName(reName, "Min"),
            humpToLine = humpToLine,
            value = value,
            tableName = tableName
        )
    }

    fun ge(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = GE,
            reName = getReName(reName, "Min"),
            humpToLine = humpToLine,
            value = value,
            tableName = tableName
        )
    }

    fun lt(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = LT,
            reName = getReName(reName, "Max"),
            humpToLine = humpToLine,
            value = value,
            tableName = tableName
        )
    }

    fun le(
        value: Any?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = LE,
            reName = getReName(reName, "Max"),
            humpToLine = humpToLine,
            value = value,
            tableName = tableName
        )
    }

    fun between(
        range: ClosedRange<*>?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = BETWEEN,
            reName = getReName(reName),
            humpToLine = humpToLine,
            value = range,
            not = not,
            tableName = tableName
        )
    }

    inline fun <reified T : Comparable<T>> notBetween(
        range: ClosedRange<T>?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        return between(range, reName, iif, humpToLine, true, tableName)
    }


    /**
     * If the condition is true, then return the condition
     *
     * @param reName The name of the parameter in the SQL statement.
     * @param iif If true, the condition will be ignored.
     * @param humpToLine If true, the humpToLine function will be called.
     * @return Nothing.
     */
    fun isIn(
        list: Collection<*>?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = IN,
            reName = getReName(reName),
            humpToLine = humpToLine,
            value = list,
            not = not,
            tableName = tableName
        )
    }

    /**
     * If the condition is true, then return the condition
     *
     * @param reName The name of the parameter in the SQL statement.
     * @param iif If true, the condition will be ignored.
     * @param humpToLine If true, the humpToLine function will be called.
     * @return Nothing.
     */
    fun notIn(
        list: Collection<*>?,
        reName: String?,
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        return isIn(list, reName, iif, humpToLine, true, tableName)
    }

    /**
     * If the condition is null, return null
     *
     * @param iif If true, the condition will be ignored.
     * @param humpToLine If true, the parameter name will be converted to hump style.
     * @return Nothing.
     */
    fun isNull(
        iif: Boolean?,
        humpToLine: Boolean,
        not: Boolean = false,
        tableName: String? = null
    ): Criteria? {
        if (iif != null && !iif) return null
        return Criteria(
            parameterName = parameterName,
            type = ISNULL,
            reName = parameterName,
            humpToLine = humpToLine,
            not = not,
            tableName = tableName
        )
    }

    /**
     * If the `iif` parameter is not null, then return a `Criteria` object with the `type` property set to
     * `ConditionType.NOTNULL`
     *
     * @param iif If you want to ignore the condition, you can set it to false.
     * @param humpToLine If true, the parameter name will be converted to hump style.
     * @return Nothing.
     */
    fun notNull(
        iif: Boolean?,
        humpToLine: Boolean,
        tableName: String? = null
    ): Criteria? {
        return isNull(iif, humpToLine, true, tableName)
    }
}
