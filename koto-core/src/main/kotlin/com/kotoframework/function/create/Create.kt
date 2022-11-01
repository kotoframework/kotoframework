package com.kotoframework.function.create

import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper

/**
 * Created by ousc on 2022/5/30 16:57
 */

fun <T : KPojo> create(KPojo: T, jdbcWrapper: KotoJdbcWrapper? = null): CreateWhere<T> {
    return CreateWhere(KPojo, kotoJdbcWrapper = jdbcWrapper)
}
