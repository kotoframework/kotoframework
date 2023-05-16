package com.kotoframework.function.columnSearch

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.beans.KResultSet
import com.kotoframework.utils.receiver
import com.kotoframework.definition.Field
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.utils.tableName
import javax.sql.DataSource
import kotlin.reflect.KProperty1


/**
 * Created by ousc on 2022/5/30 17:52
 */
fun <T : KPojo> DataSource.columnSearch(
    field: Pair<KProperty1<T, String?>, String?>,
    queryFields: Collection<Field>? = null,
    suffix: String? = "order by `id` desc",
    limit: Int = 200
): KResultSet<String> {
    return columnSearch(
        field.first.receiver.tableName, Pair(field.first.name, field.second), queryFields, suffix, limit, this.wrapper()
    )
}

fun DataSource.columnSearch(
    tableName: String,
    fields: Pair<Field, String?>,
    queryFields: Collection<Field>? = null,
    suffix: String? = "order by `id` desc",
    limit: Int = 200,
    wrapper: KJdbcWrapper? = null
): KResultSet<String> {
    return columnSearch(
        tableName, fields, queryFields, suffix, limit, this.wrapper()
    )
}
