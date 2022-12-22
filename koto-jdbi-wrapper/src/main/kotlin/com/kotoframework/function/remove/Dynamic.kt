package com.kotoframework.function.remove

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import org.jdbi.v3.core.Jdbi

/**
 * Created by ousc on 2022/5/30 16:46
 */

inline fun <reified T : KPojo> Jdbi.remove(KPojo: T): RemoveAction<T> {
    return remove(KPojo, this.wrapper())
}

fun Jdbi.remove(tableName: String): RemoveAction<Unknown> {
    return remove(tableName, this.wrapper())
}
