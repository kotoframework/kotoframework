package com.kotoframework.enums;

import com.kotoframework.*
import com.kotoframework.core.condition.Criteria;

enum class NoValueStrategy {
    Ignore,
    False,
    True,
    IsNull,
    NotNull,
    Smart;

    fun ignore(): Boolean {
        return this == Ignore
    }

    fun dealWithNoValue(
        alias: String,
        realName: String,
        criteria:Criteria,
        noValueStrategy: NoValueStrategy,
        counter: Int = 0
    ): String? {
        if (counter > 1) return null
        val defaultIgnore: String? = dealWithNoValue(alias, realName, criteria, noValueStrategy, counter + 1)
        return when (if (counter == 0) this else noValueStrategy) {
            IsNull -> "${alias}${realName} is null"
            NotNull -> "${alias}${realName} is not null"
            True -> "true"
            False -> "false"
            Smart -> when {
                criteria.type == EQUAL && !criteria.not -> "${alias}${realName} is null"
                criteria.type == EQUAL && criteria.not -> "${alias}${realName} is not null"
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
