package com.kotoframework.interfaces

import com.kotoframework.orm.query.actions.SelectFrom
import com.kotoframework.orm.query.from

/**
 * Created by ousc on 2022/4/25 16:04
 */
interface KPojo

inline fun <reified T : KPojo> T.orm(): SelectFrom<T> {
    return from(this)
}