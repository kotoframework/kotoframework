package com.kotoframework.function.remove

import com.kotoframework.KotoApp.softDeleteColumn
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Extension.tableMeta
import com.kotoframework.utils.Extension.tableName
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/5/30 17:17
 */
fun <T : KPojo> remove(KPojo: T, jdbcWrapper: KotoJdbcWrapper? = null): RemoveAction<T> {
    val meta = KPojo.tableMeta
    Jdbc.initMetaData(meta, jdbcWrapper)
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
        Jdbc.TableMeta(
            tableName,
            Jdbc.TableSoftDelete(
                softDeleteColumn.isBlank().not(),
                softDeleteColumn
            )
        ), jdbcWrapper
    )
    return RemoveAction(tableName, Unknown(), jdbcWrapper)
}
