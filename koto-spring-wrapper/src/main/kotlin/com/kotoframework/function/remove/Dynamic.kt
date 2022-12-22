package com.kotoframework.function.remove

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/5/30 16:46
 */

inline fun <reified T : KPojo> NamedParameterJdbcTemplate.remove(KPojo: T): RemoveAction<T> {
    return remove(KPojo, this.wrapper())
}

fun NamedParameterJdbcTemplate.remove(tableName: String): RemoveAction<Unknown> {
    return remove(tableName, this.wrapper())
}
