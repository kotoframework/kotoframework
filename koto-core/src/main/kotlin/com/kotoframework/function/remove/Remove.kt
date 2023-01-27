package com.kotoframework.function.remove

import com.kotoframework.KotoApp
import com.kotoframework.beans.TableMeta
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Extension.tableMeta
import com.kotoframework.utils.Extension.tableName
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/5/30 17:17
 */
inline fun <reified T : KPojo> remove(KPojo: T, jdbcWrapper: KotoJdbcWrapper? = null): RemoveAction<T> {
    val meta = KPojo.tableMeta
    Jdbc.initMetaData(meta, jdbcWrapper, KPojo)
    return RemoveAction(KPojo.tableName, KPojo, jdbcWrapper)
}

/**
 * > The function `remove` is a function that takes a table name as a parameter and returns a `TableRemove` object
 *
 * @param tableName The name of the table to be operated on.
 * @return TableRemove
 */
fun remove(tableName: String, jdbcWrapper: KotoJdbcWrapper? = null): RemoveAction<Unknown> {
    Jdbc.initMetaData(
        TableMeta(
            tableName,
            KotoApp.Config.softDeleteConfig,
            KotoApp.Config.createTimeConfig,
            KotoApp.Config.updateTimeConfig,
            KotoApp.Config.deleteTimeConfig
        ), jdbcWrapper
    )
    return RemoveAction(tableName, Unknown(), jdbcWrapper)
}
