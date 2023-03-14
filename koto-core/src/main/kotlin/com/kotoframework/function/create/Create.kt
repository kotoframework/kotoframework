package com.kotoframework.function.create

import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper

/**
 * Created by ousc on 2022/5/30 16:57
 */

@JvmName("createUseExpand")
fun <T : KPojo> T.create(jdbcWrapper: KotoJdbcWrapper? = null): CreateWhere<T> {
    return CreateWhere(this, kotoJdbcWrapper = jdbcWrapper)
}

@JvmName("createUseExpand")
fun <T : KPojo> Collection<T>.create(jdbcWrapper: KotoJdbcWrapper? = null): List<CreateWhere<T>> {
    return map { create(it, jdbcWrapper) }
}

fun <T : KPojo> create(KPojo: T, jdbcWrapper: KotoJdbcWrapper? = null): CreateWhere<T> {
    return CreateWhere(KPojo, kotoJdbcWrapper = jdbcWrapper)
}

fun <T: KPojo> create(listOfKPojo: Collection<T>, jdbcWrapper: KotoJdbcWrapper? = null): List<CreateWhere<T>> {
    return listOfKPojo.map { create(it, jdbcWrapper) }
}
