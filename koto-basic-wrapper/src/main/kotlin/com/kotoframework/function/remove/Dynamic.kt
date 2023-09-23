package com.kotoframework.function.remove

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import javax.sql.DataSource

/**
 * Created by ousc on 2022/5/30 16:46
 */

//inline fun <reified T : KPojo> DataSource.remove(KPojo: T): RemoveAction<T> {
//    return remove(KPojo, this.wrapper())
//}
//
//fun DataSource.remove(tableName: String): RemoveAction<Unknown> {
//    return remove(tableName, this.wrapper())
//}
