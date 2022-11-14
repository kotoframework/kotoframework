package com.kotoframework

/**
 * Created by ousc on 2022/4/18 10:49
 */

enum class SortType {
    ASC,
    DESC
}

enum class ConditionType {
    LIKE,
    EQUAL,
    IN,
    ISNULL,
    SQL,
    GT,
    GE,
    LT,
    LE,
    BETWEEN,
    AND,
    OR
}

enum class LikePosition {
    Left,
    Right,
    Both,
    Never
}

enum class DBType{
    MySql,
    Oracle,
    MSSql,
    PostgreSQL,
    SQLite
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

