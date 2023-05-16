package com.kotoframework

import com.kotoframework.enums.*

/**
 * Created by ousc on 2022/4/18 10:49
 */

/*
* when use like, you can use this to set the position of the like
* */
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
val OceanBase = DBType.OceanBase

val Ignore = NoValueStrategy.Ignore
val False = NoValueStrategy.False
val True = NoValueStrategy.True
val IsNull = NoValueStrategy.IsNull
val NotNull = NoValueStrategy.NotNull
val Smart = NoValueStrategy.Smart

val INNER_JOIN = JoinType.INNER_JOIN
val LEFT_JOIN = JoinType.LEFT_JOIN
val RIGHT_JOIN = JoinType.RIGHT_JOIN
val FULL_JOIN = JoinType.FULL_JOIN


