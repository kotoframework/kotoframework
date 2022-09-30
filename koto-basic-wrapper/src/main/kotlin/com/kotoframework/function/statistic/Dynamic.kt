package com.kotoframework.function.statistic

import com.kotoframework.PureJdbcWrapper.Companion.wrapper
import javax.sql.DataSource


/**
 * Created by ousc on 2022/5/30 17:52
 */
fun DataSource.statistic(tableName: String): Statistic {
    return Statistic(tableName, this.wrapper())
}
