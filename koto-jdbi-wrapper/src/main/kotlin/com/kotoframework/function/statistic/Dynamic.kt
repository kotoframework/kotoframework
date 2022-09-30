package com.kotoframework.function.statistic

import com.kotoframework.JdbiWrapper.Companion.wrapper
import org.jdbi.v3.core.Jdbi

/**
 * Created by ousc on 2022/5/30 17:52
 */
fun Jdbi.statistic(tableName: String): Statistic {
    return Statistic(tableName, this.wrapper())
}
