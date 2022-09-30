package com.kotoframework.function.create

import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/5/30 16:57
 */

fun <T : KPojo> create(KPojo: T): CreateWhere<T> {
    // get the annotation of this expression

    return CreateWhere(KPojo)
}
