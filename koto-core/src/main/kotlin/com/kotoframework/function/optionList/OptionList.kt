package com.kotoframework.function.optionList

import com.kotoframework.beans.Unknown
import com.kotoframework.core.condition.and
import com.kotoframework.core.condition.notNull
import com.kotoframework.beans.KotoResultSet
import com.kotoframework.core.where.Where
import com.kotoframework.definition.Field
import com.kotoframework.definition.aliasName
import com.kotoframework.definition.columnName
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.condition.receiver
import com.kotoframework.utils.Extension.rmRedudantBlk
import com.kotoframework.utils.Extension.tableName
import kotlin.reflect.KProperty1

/**
 * Created by ousc on 2022/5/30 17:28
 */
fun optionList(
    tableName: String,
    fields: Pair<Field, String?>,
    queryFields: Collection<Field>? = null,
    suffix: String? = "order by `id` desc"
): KotoResultSet<String> {
    val fieldName = fields.first.columnName
    val fieldAlias = fields.first.aliasName
    val value = fields.second
    val where = Where(
        Unknown(),
        kClass = String::class,
    ) { "UPPER(`$fieldName`) like UPPER(:$fieldAlias)" and fieldAlias.notNull() }.map(
        fieldAlias to "%$value%"
    ).build()

    val sql = if (queryFields == null) {
        "select distinct `$fieldName` as `$fieldAlias` from $tableName where ${where.sql} $suffix limit 200"
    } else {
        "select distinct ${
            queryFields.joinToString(",") { "`${it.columnName}` as `${it.aliasName}`" }
        } from $tableName where ${where.sql} $suffix limit 200"
    }

    return KotoResultSet(sql.rmRedudantBlk(), where.paramMap, kClass = String::class)
}


fun <T : KPojo> optionList(
    field: Pair<KProperty1<T, String?>, String?>, queryFields: Collection<Field>? = null
): KotoResultSet<String> {
    return optionList(
        field.first.receiver.tableName, Pair(field.first.name, field.second), queryFields
    )
}

