package com.kotoframework.enums

import com.kotoframework.interfaces.KPojo
import com.kotoframework.orm.query.QA
import com.kotoframework.orm.query.javaInstance

enum class JoinType(val value: String) {
    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    FULL_JOIN("FULL JOIN"),
    CROSS_JOIN("CROSS JOIN")
}