package com.kotoframework.function.statistic

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate


/**
 * Created by ousc on 2022/5/30 17:52
 */
fun NamedParameterJdbcTemplate.statistic(tableName: String): Statistic {
    return Statistic(tableName, this.wrapper())
}
