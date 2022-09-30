package com.kotoframework.function.select

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.function.associate.javaInstance
import com.kotoframework.interfaces.KPojo
import org.jdbi.v3.core.Jdbi
/**
 * Created by ousc on 2022/5/30 16:46
 */

inline fun <reified T : KPojo> Jdbi.select(
    kPojo: T = T::class.javaInstance(),
    vararg fields: Any
): SelectAction<T> {
    return select(kPojo, *fields, jdbcWrapper = this.wrapper())
}
