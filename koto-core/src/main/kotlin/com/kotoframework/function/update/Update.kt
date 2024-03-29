package com.kotoframework.function.update

import com.kotoframework.definition.Field
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper

/**
 * Created by ousc on 2022/5/30 17:06
 */

@JvmName("updateUseExpand")
fun <T: KPojo> T.update(vararg fields: Field, jdbcWrapper: KotoJdbcWrapper? = null): UpdateAction<T> {
    return UpdateAction(this, fields.toList(), jdbcWrapper = jdbcWrapper)
}

fun <T : KPojo> update(
    KPojo: T,
    vararg fields: Field,
    jdbcWrapper: KotoJdbcWrapper? = null
): UpdateAction<T> {
    return UpdateAction(KPojo, fields.toList(), jdbcWrapper = jdbcWrapper)
}
