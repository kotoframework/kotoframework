package com.kotoframework.function.update

import com.kotoframework.definition.Field
import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/5/30 17:06
 */

inline fun <reified T : KPojo> update(KPojo: T, vararg fields: Field): UpdateAction<T> {
    return UpdateAction(KPojo, fields.toList())
}
