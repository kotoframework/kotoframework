package com.kotoframework.function.select

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.function.associate.javaInstance
import com.kotoframework.interfaces.KPojo
import javax.sql.DataSource

/**
 * Created by ousc on 2022/5/30 16:46
 */

inline fun <reified T : KPojo> DataSource.select(
    kPojo: T = T::class.javaInstance(),
    vararg fields: Any
): SelectAction<T> {
    return select(kPojo, *fields, jdbcWrapper = this.wrapper())
}
