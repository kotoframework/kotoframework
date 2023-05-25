package com.kotoframework.utils

import com.kotoframework.*
import com.kotoframework.beans.UnsupportedDatabaseTypeException
import com.kotoframework.core.annotations.DateTimeFormat
import com.kotoframework.core.condition.declareSql
import com.kotoframework.core.condition.eq
import com.kotoframework.definition.CriteriaField
import com.kotoframework.definition.*
import com.kotoframework.definition.aliasName
import com.kotoframework.definition.columnName
import com.kotoframework.interfaces.KPojo
import com.kotoframework.orm.query.QA
import com.kotoframework.utils.Jdbc.joinSqlStatement
import com.kotoframework.utils.Jdbc.tableMap
import com.kotoframework.utils.Jdbc.tableMetaKey
import kotlin.reflect.KCallable
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

/**
 * Created by sundaiyue on 2023/4/4 16:05
 */
class SqlBuilder(private val qa: QA) : CriteriaField(qa.tables) {
    /*
    * mode:
    * 0: select from one table
    * 1: select from more than one table
     */
    private var mode = 0
    private var dateTimeFormatMap: MutableMap<String, Map<String, String>> =
        mutableMapOf() // table name -> field name -> date time format
    private var fieldTypeMap: MutableMap<String, Map<String, String>> =
        mutableMapOf() // table name -> field name -> field type
    private var onParamMap = mutableMapOf<String, Any?>() // on param map
    private var whereParamMap = mutableMapOf<String, Any?>() // where param map

    /*
    *  init tables and mode
     */
    fun init(): Pair<String, MutableMap<String, Any?>> {
        if (this.qa.tables.size == 1) {
            this.mode = 0
        } else {
            this.mode = 1
        }
        this.qa.tables.forEach { kPojo ->
            dateTimeFormatMap[kPojo.tableName] =
                kPojo::class.declaredMemberProperties.filter { field -> field.findAnnotation<DateTimeFormat>() != null }
                    .associate { it.name to (it.annotations.first { annotation -> annotation is DateTimeFormat } as DateTimeFormat).pattern }

            val fields = tableMap[tableMetaKey(qa.wrapper, kPojo.tableName)]!!.fields
            fieldTypeMap[kPojo.tableName] = fields.associate { it.name to it.type }
        }
        return selectSql() to whereParamMap
    }

    fun build(type: String): Pair<String, Map<String, Any?>> {
        return init()
    }

    private fun selectSql(): String {
        ifBlackSelectAll()
        generateAutoWhere()

        val fieldSql =
            this.qa.selectedFields.map { generateSelectSql(it) }.flatten().joinToString(", ") { it.columnName }
        val fromSql = generateFromSql()
        val whereSql = generateWhereSql()
        val distinctSql = if (qa.distinct) "distinct" else null
        val prefix = listOfNotNull("select", distinctSql, fieldSql, "from", fromSql, whereSql).joinToString(" ")
        val pagination = smartPagination(
            prefix,
            qa.suffix,
            qa.pageIndex,
            qa.pageSize
        )

        pagination.suffix = listOfNotNull(
            pagination.suffix,
            (pagination.limit ?: qa.limit)?.let { "limit $it" },
            (pagination.offset ?: qa.offset)?.let { "offset $it" }
        ).joinToString(" ")

        val orderBy = groupOrOrderBy("order", qa.orderBy ?: listOf())
        val groupBy = groupOrOrderBy("group", qa.groupBy ?: listOf())

        return listOfNotNull(
            pagination.prefix,
            groupBy,
            orderBy,
            pagination.suffix
        ).joinToString(" ")
    }

    private fun generateWhereSql(): String {
        whereParamMap.putAll(qa.paramMap)
        val whereStatement = joinSqlStatement(qa.whereCriteria, whereParamMap, qa.ifNoValueStrategy)
        return if (whereStatement.isBlank()) "" else "where $whereStatement"
    }

    private fun generateFromSql(): String {
        return if (mode == 0) {
            qa.t1!!.tableName
        } else {
            var sql = "`${qa.t1!!.tableName}` as `${qa.t1!!.tableAlias}`"
            this.qa.tables.forEachIndexed { index, table ->
                if (index == 0) return@forEachIndexed
                val onStatement = joinSqlStatement(
                    listOf(qa.onCriteria[table.tableName]),
                    onParamMap,
                    qa.ifNoValueStrategy,
                    multiTable = true
                )
                sql += " ${qa.joinTypes[table.tableName]!!.name} `${table.tableName}` as `${table.tableAlias}` on $onStatement"
            }
            whereParamMap.putAll(onParamMap)
            sql
        }
    }

    private fun generateSelectSql(field: Field): List<ColumnMeta> {
        if (this.mode == 0) {
            val tableName = qa.t1!!.tableName
            val tableAlias = qa.t1!!.tableAlias
            return listOf(
                field.toColumn().apply {
                    columnName = getSql(
                        field,
                        fieldTypeMap[tableName]!![field.columnName] ?: "varchar",
                        tableAlias,
                        dateTimeFormatMap[tableName]!![field.columnName]
                    )
                }
            )
        } else {
            if (field is String) {
                return listOf(field.toColumn());
            } else if (field isAssignableFrom KPojo::class) {
                val tableName = (field as KPojo).tableName
                val tableAlias = field.tableAlias
                val fields = tableMap[tableMetaKey(qa.wrapper, tableName)]!!.fields
                return fields.map {
                    it.toColumn().apply {
                        columnName = getSql(
                            it.name.lineToHump(),
                            it.type,
                            tableAlias,
                            dateTimeFormatMap[tableName]!![columnName]
                        )
                    }
                }
            } else {
                val (tableName, tableAlias) = when (field) {
                    is KCallable<*> -> {
                        Pair(
                            field.receiver.tableName,
                            field.receiver.tableAlias
                        )
                    }

                    is Pair<*, *> -> {
                        if (field.first is KCallable<*>) {
                            Pair(
                                (field.first as KCallable<*>).receiver.tableName,
                                (field.first as KCallable<*>).receiver.tableAlias
                            )
                        } else {
                            throw IllegalArgumentException("The first argument must be KCallable<*>")
                        }
                    }

                    else -> {
                        throw IllegalArgumentException("${field.javaClass.simpleName} is not a KPojo")
                    }
                }

                return listOf(
                    field.toColumn().apply {
                        columnName = getSql(
                            field,
                            fieldTypeMap[tableName]!![field.columnName] ?: "varchar",
                            tableAlias,
                            dateTimeFormatMap[tableName]!![field.columnName]
                        )
                    }
                )
            }
        }
    }

    private fun ifBlackSelectAll() {
        if (this.qa.selectedFields.contains("*") || this.qa.selectedFields.isEmpty()) {
            this.qa.selectedFields =
                (this.qa.selectedFields.filter { it != "*" } +
                        this.qa.tables.flatMap { it::class.declaredMemberProperties }).toMutableList()
        }
    }

    private fun generateAutoWhere() {
        if (qa.autoWhere) {
            qa.whereCriteria.addAll(qa.tables.map {
                val prefix = if (this.mode == 0) "" else "${it.tableName}@"
                it::class.declaredMemberProperties.map { kCallable -> kCallable.eq }
                    .filter { qa.paramMap["$prefix${it.aliasName}"] != null }
            }.flatten())
        }

        if (qa.whereCriteria.isNotEmpty()) {
            qa.whereCriteria.add(
                deleted(
                    0,
                    qa.wrapper,
                    qa.t1!!.tableName,
                    if (mode == 1) "`${qa.t1!!.tableAlias}`." else ""
                ).declareSql()
            )
        }
    }

    private fun getSql(
        field: Field, type: String, tableAlias: String, dateTimeFormat: String? = null
    ): String {
        val fieldAlias = if (qa.complex) {
            "`$tableAlias${field.aliasName.replaceFirstChar { it.uppercase() }}`"
        } else {
            "`${field.aliasName}`"
        }
        val (columnName) = field.toColumn()
        return when {
            type == "date" -> dateFormatFunc(
                tableAlias,
                columnName,
                toSqlDate(dateTimeFormat) ?: "%Y-%m-%d",
                fieldAlias
            )

            type == "datetime" -> dateFormatFunc(
                tableAlias,
                columnName,
                toSqlDate(dateTimeFormat) ?: "%Y-%m-%d %H:%i:%s",
                fieldAlias
            )

            else -> {
                if (columnName.contains("(") || columnName.lowercase().contains(" as ")) {
                    columnName
                } else if (this.mode == 0) {
                    if ("`$columnName`" == fieldAlias) {
                        "`$columnName`"
                    } else {
                        "`$columnName` as $fieldAlias"
                    }
                } else {
                    "`$tableAlias`.`$columnName` as $fieldAlias"
                }
            }
        }
    }

    private fun dateFormatFunc(tableAlias: String, column: String, format: String, alias: String): String {
        val columnName = if (this.mode == 0) {
            "`$column`"
        } else {
            "`$tableAlias`.`$column`"
        }
        return when (KotoApp.dbType) {
            MySql, OceanBase -> "DATE_FORMAT($columnName, '$format') as $alias"
            SQLite -> "strftime('$format', $columnName) as $alias"
            Oracle -> "TO_CHAR($columnName`, '$format') as $alias"
            MSSql -> "CONVERT(VARCHAR(20), $columnName, 120) as $alias"
            PostgreSQL -> "TO_CHAR($columnName, '$format') as $alias"
            else -> throw UnsupportedDatabaseTypeException()
        }
    }

    private fun groupOrOrderBy(type: String, fields: List<Field?>): String? {
        if (fields.none { !it.isNullOrEmpty() }) {
            return null
        }
        val sqls = mutableListOf<String>()
        var str = "$type by "
        fields.forEach {
            if (it.isNullOrEmpty()) return@forEach
            val fieldName = getFieldName(it!!)
            val direction = if (type === "order") it.direction else null
            sqls.add(listOfNotNull(fieldName, direction).joinToString(" "))
        }
        return str + sqls.joinToString(", ")
    }

    private fun getFieldName(field: Field): String {
        return when (field) {
            is String -> field
            is KCallable<*> -> {
                if (this.qa.tables.size > 1) {
                    "`${field.receiver.tableAlias}`.`${field.columnName}`"
                } else {
                    "`${field.columnName}`"
                }
            }

            is Pair<*, *> -> getFieldName(field.first as Field)
            else -> throw IllegalArgumentException("field type error")
        }
    }
}
