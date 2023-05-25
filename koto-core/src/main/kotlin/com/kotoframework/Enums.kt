package com.kotoframework

import com.kotoframework.core.condition.Criteria
import com.kotoframework.utils.Common

/**
 * Created by ousc on 2022/4/18 10:49
 */

enum class SortType {
    ASC, DESC
}

enum class ConditionType {
    LIKE, EQUAL, IN, ISNULL, SQL, GT, GE, LT, LE, BETWEEN, AND, OR
}

enum class LikePosition {
    Left, Right, Both, Never
}

enum class DBType {
    MySql, Oracle, MSSql, PostgreSQL, SQLite
}

enum class NoValueStrategy {
    Ignore, False, True, IsNull, NotNull, Smart;

    fun ignore(): Boolean {
        return this == Ignore
    }

    fun dealWithNoValue(
        alias: String,
        criteria: Criteria,
        noValueStrategy: NoValueStrategy,
        counter: Int = 0
    ): String? {
        if(counter > 1) return null
        val defaultIgnore: String? = dealWithNoValue(alias, criteria, noValueStrategy, counter + 1)
        val columnName = Common.getColumnName(criteria)
        return when (if (counter == 0) this else noValueStrategy) {
            IsNull -> "$alias`$columnName` is null"
            NotNull -> "$alias`$columnName` is not null"
            True -> "true"
            False -> "false"
            Smart -> when {
                criteria.type == EQUAL && !criteria.not -> "$alias`$columnName` is null"
                criteria.type == EQUAL && criteria.not -> "$alias`$columnName` is not null"
                criteria.type == LIKE && !criteria.not -> "true"
                criteria.type == LIKE && criteria.not -> "false"
                criteria.type == IN && !criteria.not -> "false"
                criteria.type == IN && criteria.not -> "true"
                criteria.type == BETWEEN && !criteria.not -> "false"
                criteria.type == BETWEEN && criteria.not -> "true"
                criteria.type == GT || criteria.type == GE || criteria.type == LT || criteria.type == LE -> "false"
                else -> null
            }

            Ignore -> defaultIgnore
        }
    }
}

val Left = LikePosition.Left
val Right = LikePosition.Right
val Both = LikePosition.Both
val Never = LikePosition.Never

val LIKE = ConditionType.LIKE
val EQUAL = ConditionType.EQUAL
val IN = ConditionType.IN
val ISNULL = ConditionType.ISNULL
val SQL = ConditionType.SQL
val GT = ConditionType.GT
val GE = ConditionType.GE
val LT = ConditionType.LT
val LE = ConditionType.LE
val BETWEEN = ConditionType.BETWEEN
val AND = ConditionType.AND
val OR = ConditionType.OR

val ASC = SortType.ASC
val DESC = SortType.DESC

val MySql = DBType.MySql
val Oracle = DBType.Oracle
val MSSql = DBType.MSSql
val PostgreSQL = DBType.PostgreSQL
val SQLite = DBType.SQLite

val Ignore = NoValueStrategy.Ignore
val False = NoValueStrategy.False
val True = NoValueStrategy.True
val IsNull = NoValueStrategy.IsNull
val NotNull = NoValueStrategy.NotNull
val Smart = NoValueStrategy.Smart


