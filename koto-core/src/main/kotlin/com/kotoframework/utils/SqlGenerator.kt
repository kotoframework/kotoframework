package com.kotoframework.utils

import com.kotoframework.core.condition.Criteria
import com.kotoframework.*


/**
 * Created by ousc on 2022/4/28 19:20
 */
object SqlGenerator {
    /**
     * > It generates a SQL fragment based on the condition type
     *
     * @param condition The condition object
     * @return A string that is the SQL query.
     */
    fun generate(condition: Criteria, realName: String): String {
        val parameter = getColumnName(condition)

        return when (condition.type) {
            LIKE, EQUAL, GT, GE, LT, LE -> "`$parameter` ${
                when (condition.type) {
                    LIKE -> "like".takeUnless { condition.not } ?: "not like"
                    EQUAL -> "=".takeUnless { condition.not } ?: "!="
                    GT -> ">"
                    GE -> ">="
                    LT -> "<"
                    LE -> "<="
                    else -> ""
                }
            } :${realName}"

            BETWEEN -> "`$parameter` ${"between".takeUnless { condition.not } ?: "not between"} :${realName + "Min"} and :${realName + "Max"}"
            IN -> "`$parameter` ${"in".takeUnless { condition.not } ?: "not in"} (:$realName)"

            ISNULL -> "`$parameter` ${"is".takeUnless { condition.not } ?:"is not" } null"

            else -> condition.sql
        }
    }
}
