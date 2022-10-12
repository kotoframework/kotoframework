package com.kotoframework.utils

import com.kotoframework.core.condition.Criteria
import com.kotoframework.*
import com.kotoframework.utils.Common.getParameter
import com.kotoframework.utils.Extension.yes


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
    fun generate(condition: Criteria): String {
        val realName = when {
            !condition.reName.isNullOrBlank() -> condition.reName
            !condition.parameterName.isNullOrBlank() -> condition.parameterName
            else -> ""
        }
        val parameter = if (condition.parameterName != null) getParameter(condition) else null

        return when (condition.type) {
            LIKE, EQUAL, GT, GE, LT, LE -> "`$parameter` ${
                when (condition.type) {
                    LIKE -> condition.not.yes { "not like" } ?: "like"
                    EQUAL -> condition.not.yes { "!=" } ?: "="
                    GT -> ">"
                    GE -> ">="
                    LT -> "<"
                    LE -> "<="
                    else -> ""
                }
            } :${realName}"

            BETWEEN -> "`$parameter` ${condition.not.yes { "not between" } ?: "between"} :${realName + "Min"} and :${realName + "Max"}"
            IN -> "`$parameter` ${condition.not.yes { "not in" } ?: "in"} (:$realName)"

            ISNULL -> "`$parameter` ${condition.not.yes { "is not" } ?: "is"} null"

            else -> condition.sql
        }
    }
}
