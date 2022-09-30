package com.kotoframework.function.select

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.function.associate.javaInstance
import com.kotoframework.interfaces.KPojo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/5/30 16:46
 */

inline fun <reified T : KPojo> NamedParameterJdbcTemplate.select(
    kPojo: T = T::class.javaInstance(),
    vararg fields: Any
): SelectAction<T> {
    return select(kPojo, *fields, jdbcWrapper = this.wrapper())
}
