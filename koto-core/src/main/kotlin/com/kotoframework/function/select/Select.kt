package com.kotoframework.function.select

import com.kotoframework.*
import com.kotoframework.KotoApp.dbType
import com.kotoframework.beans.TableColumn
import com.kotoframework.definition.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.annotations.DateTimeFormat
import com.kotoframework.utils.Common.toSqlDate
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.tableMeta
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Jdbc.dbName
import com.kotoframework.utils.Jdbc.initMetaData
import com.kotoframework.utils.Jdbc.tableMap


const val ALL_FIELDS = "*"


@JvmName("generic")
inline fun <reified T : KPojo> T.select(vararg fields: Field, jdbcWrapper: KotoJdbcWrapper? = null): SelectAction<T> {
    return com.kotoframework.function.select.select(this, *fields, jdbcWrapper = jdbcWrapper)
}

inline fun <reified T : KPojo> select(
    KPojo: T? = null, vararg fields: Field, jdbcWrapper: KotoJdbcWrapper? = null
): SelectAction<T> {
    val table = KPojo ?: T::class.java.newInstance()
    val meta = table.tableMeta
    initMetaData(meta, jdbcWrapper)
    var selectFields = (if (fields.isEmpty()) listOf(ALL_FIELDS) else fields.toList()).toMutableList()

    if (selectFields.contains(ALL_FIELDS)) {
        selectFields = selectFields.apply {
            remove(ALL_FIELDS)
            addAll(tableMap[Jdbc.getJdbcWrapper(jdbcWrapper).dbName + "_" + meta.tableName]!!.fields.map { it.name.lineToHump() })
        }.distinct().toMutableList()
    }

    return generateSelectSqlByFields(meta.tableName, selectFields, table, jdbcWrapper)
}

/**
 * It generates a select statement based on the fields passed in.
 *
 * @param tableName The name of the table to be queried
 * @param fields The fields you want to query
 * @param KPojo The object that contains the data to be inserted.
 * @return A TableSelect object
 */
inline fun <reified T : KPojo> generateSelectSqlByFields(
    tableName: String, fields: List<Field>, KPojo: T, jdbcWrapper: KotoJdbcWrapper? = null
): SelectAction<T> {
    val dateTimeFormat =
        T::class.java.declaredFields.filter { field -> field.annotations.any { it is DateTimeFormat } }
            .associate { it.name to (it.annotations.first { annotation -> annotation is DateTimeFormat } as DateTimeFormat).pattern }

    val sql = generateSqlByFieldAndType(
        tableName,
        fields,
        tableMap[Jdbc.getJdbcWrapper(jdbcWrapper).dbName + "_" + tableName]!!.fields,
        dateTimeFormat
    )
    return SelectAction(sql, KPojo, jdbcWrapper, T::class)
}

/**
 * > This function generates a SQL query based on the table name, the fields to filter, the fields to select, and the
 * types of the fields to select
 *
 * @param tableName The name of the table you want to query.
 * @param filterFields The fields that are not to be included in the query.
 * @param fields The fields that you want to filter on.
 * @param types The list of types of the fields in the table.
 * @return A string
 */
fun generateSqlByFieldAndType(
    tableName: String,
    filterFields: List<Field>,
    fields: List<TableColumn>,
    dateTimeFormat: Map<String, String>
): String {
    var sql = "select "
    for (field in filterFields.withIndex()) {
        if (sql != "select ") {
            sql = "$sql, "
        }
        sql = getSql(
            sql,
            field.value,
            fields.firstOrNull { it.name == field.value.columnName }?.type ?: "varchar(255)",
            dateTimeFormat
        )
    }
    sql = "$sql from $tableName"
    return sql
}

internal fun getSql(
    sql: String, field: Field, type: String, dateTimeFormat: Map<String, String>
): String {
    return when {
        type == "date" -> "$sql ${dateFormatFunc(field.columnName, toSqlDate(dateTimeFormat[field.aliasName]) ?: "%Y-%m-%d", field.aliasName)}"
        type == "datetime" -> "$sql ${dateFormatFunc(field.columnName, toSqlDate(dateTimeFormat[field.aliasName]) ?: "%Y-%m-%d %H:%i:%s", field.aliasName)}"
        else -> {
            if (field.columnName.contains("(") || field.columnName.lowercase().contains(" as ")) {
                "$sql ${field.columnName}"
            } else if (field.columnName == field.aliasName) {
                "$sql `${field.aliasName}`"
            } else {
                "$sql `${field.columnName}` as `${field.aliasName}`"
            }
        }
    }
}

internal fun dateFormatFunc(column: String, format: String, alias: String): String {
    return when (dbType) {
        MySql -> "DATE_FORMAT(`${column}`, '${format}') as `${alias}`"
        SQLite -> "strftime('${format}', `${column}`) as `${alias}`"
        Oracle -> "TO_CHAR(`${column}`, '${format}') as `${alias}`"
        MSSql -> "CONVERT(VARCHAR(20), `${column}`, 120) as `${alias}`"
        PostgreSQL -> "TO_CHAR(`${column}`, '${format}') as `${alias}`"
        else -> throw Exception("Unsupported database type")
    }
}
