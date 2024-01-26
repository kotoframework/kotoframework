package com.kotoframework.function.update

import com.kotoframework.definition.Field
import com.kotoframework.function.create.CreateWhere
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
    kPojo: T,
    vararg fields: Field,
    jdbcWrapper: KotoJdbcWrapper? = null
): UpdateAction<T> {
    return UpdateAction(kPojo, fields.toList(), jdbcWrapper = jdbcWrapper)
}

fun <T: KPojo> update(listOfKPojo: Collection<T>, vararg fields: Field, jdbcWrapper: KotoJdbcWrapper? = null): List<UpdateAction<T>> {
    return listOfKPojo.map {
        UpdateAction(it, fields.toList(), jdbcWrapper = jdbcWrapper)
    }
}

