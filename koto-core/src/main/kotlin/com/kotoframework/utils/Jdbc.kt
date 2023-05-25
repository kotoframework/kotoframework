package com.kotoframework.utils

import com.kotoframework.*
import com.kotoframework.core.condition.Criteria
import com.kotoframework.KotoApp.dbType
import com.kotoframework.beans.*
import com.kotoframework.core.annotations.Column
import com.kotoframework.core.annotations.DateTimeFormat
import com.kotoframework.definition.NoValueField
import com.kotoframework.definition.noValueField
import com.kotoframework.enums.ConditionType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.interfaces.KQueryHandler
import com.kotoframework.utils.Log.log
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

/**
 * Created by ousc on 2022/4/18 13:12
 */

object Jdbc {

    var tableMap = mutableMapOf<String, TableObject>() // 存储表名和字段名的映射关系

    var defaultJdbcHandler: KQueryHandler? = null
    var defaultJdbcWrapper: KJdbcWrapper? = null

    /**
     * If the namedJdbc parameter is not null, return it. Otherwise, if the defaultNamedJdbc is not null, return it.
     * Otherwise, throw a RuntimeException
     *
     * @param jdbcWrapper The NamedParameterJdbcTemplate object that you want to use.
     * @return The namedJdbc parameter if it is not null, otherwise the defaultNamedJdbc if it is not null, otherwise an
     * exception is thrown.
     */
    private fun getJdbcWrapper(jdbcWrapper: KJdbcWrapper?): KJdbcWrapper {
        return jdbcWrapper ?: defaultJdbcWrapper ?: throw NullPointerException("JdbcWrapper is null")
    }

    val KJdbcWrapper.dbName
        get() = getDBNameFromUrl(url)

    fun getDBNameFromUrl(url: String): String {
        return when (dbType) {
            MySql, OceanBase -> url.split("?").first().split("//")[1]
            SQLite -> url.split("//").last()
            Oracle -> url.split("@").last()
            MSSql -> url.split("//").last().split(";").first()
            PostgreSQL -> url.split("//").last().split("/").first()
            else -> throw UnsupportedDatabaseTypeException()
        }
    }

    fun tableMetaKey(jdbcWrapper: KJdbcWrapper?, tableName: String): String {
        return try {
            "${getJdbcWrapper(jdbcWrapper).dbName}_$tableName"
        } catch (npe: NullPointerException) {
            tableName
        }
    }

    /**
     * It gets the table structure from the database and stores it in a map.
     *
     * @param meta TableMeta
     * @param jdbcWrapper KotoJdbcWrapper? = null
     * @param kPojo KPojo? = null
     * @return A TableObject
     */
    fun initMetaData(
        meta: TableMeta,
        jdbcWrapper: KJdbcWrapper? = null,
        kPojo: KPojo? = null
    ): TableObject {
        val key = tableMetaKey(jdbcWrapper, meta.tableName)
        val defaultMetaData = {
            if (kPojo == null) {
                null
            } else {
                val columns = kPojo::class.declaredMemberProperties.map {
                    val columnAnnotation = it.findAnnotation<Column>()
                    val defaultName = it.name.humpToLine()
                    val defaultType = if (it.findAnnotation<DateTimeFormat>() != null) {
                        "datetime"
                    } else {
                        val t = it.returnType.toString()
                        when {
                            t.contains("Int") -> "int"
                            t.contains("Long") -> "bigint"
                            t.contains("Float") -> "float"
                            t.contains("Double") -> "double"
                            t.contains("String") -> "varchar"
                            t.contains("Boolean") -> "tinyint"
                            else -> "varchar"
                        }
                    }
                    if (columnAnnotation != null) {
                        return@map columnAnnotation.let { column ->
                            TableColumn(
                                column.name.ifEmpty { defaultName },
                                column.type.ifEmpty { defaultType }
                            )
                        }
                    } else {
                        return@map TableColumn(
                            defaultName,
                            defaultType
                        )
                    }
                }

                TableObject(
                    columns,
                    meta
                ).apply {
                    tableMap[key] = this
                }
            }
        }
        try {
            val wrapper = getJdbcWrapper(jdbcWrapper)
            if (tableMap[key] != null) {
                return tableMap[key]!!
            }
            val list =
                wrapper.forList(
                    when (dbType) {
                        MySql, OceanBase -> "show full fields from ${meta.tableName}"
                        SQLite -> "PRAGMA table_info(${meta.tableName})"
                        Oracle -> "SELECT COLUMN_NAME as Field, DATA_TYPE as Type FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '${meta.tableName}'"
                        MSSql -> "SELECT COLUMN_NAME as Field, DATA_TYPE as Type FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '${meta.tableName}'"
                        PostgreSQL -> "SELECT COLUMN_NAME as Field, DATA_TYPE as Type FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '${meta.tableName}'"
                        else -> throw UnsupportedDatabaseTypeException()
                    }
                )
            val columns = list.map {
                TableColumn(
                    (it["Field"] ?: it["name"]).toString(),
                    (it["Type"] ?: it["type"]).toString()
                )
            }
            return TableObject(
                columns,
                meta
            ).apply {
                tableMap[key] = this
            }
        } catch (npe: NullPointerException) {
            return defaultMetaData() ?: throw npe
        }
    }

    /**
     * > It takes a list of conditions, a map of parameters, and returns a string of SQL
     *
     * @param conditions The list of conditions to be joined
     * @param paramMap The parameter map, which is used to store the parameters of the query.
     * @param ifNoValueStrategy Whether to allow null values
     * @param join The join condition of the condition, and, or, etc.
     * @param brackets Whether to add brackets to the final result
     * @param showAlias Whether to show the alias of the table, the default is false
     * @return A string of SQL
     */
    fun joinSqlStatement(
        conditions: List<Criteria?>,
        paramMap: MutableMap<String, Any?>,
        ifNoValueStrategy: NoValueField.(Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy },
        infix: String = "and",
        brackets: Boolean = false,
        multiTable: Boolean = false
    ): String {
        val listOfSql = mutableListOf<String>()
        conditions.filterNotNull().forEach {
            if (it.type == ConditionType.ROOT) {
                it.type = AND
            }

            val prefix = if (multiTable) "${it.tableName!!.lineToHump()}." else ""

            val realName =
                if (it.valueAcceptable && it.value != null && paramMap[it.parameterName] != null && paramMap[it.parameterName] != it.value) {
                    "${it.parameterName}_${it.value}"
                } else {
                    it.parameterName
                }

            if(realName.isNotEmpty()) {
                paramMap[realName] = it.value ?: paramMap[realName]
            }

            it.sql = SqlGenerator.generate(it, realName)

            if (paramMap[realName].isNullOrEmpty() && noValueField.ifNoValueStrategy(it)
                    .ignore() && it.noValueStrategy.ignore() && it.valueAcceptable
            ) return@forEach

            val defaultIfNoValue: String? = if (paramMap[realName].isNullOrEmpty() && it.valueAcceptable) {
                it.noValueStrategy.dealWithNoValue(prefix, realName, it, noValueField.ifNoValueStrategy(it))
            } else null

            if (defaultIfNoValue != null) {
                listOfSql.add(defaultIfNoValue)
                return@forEach
            }

            when (it.type) {
                EQUAL, ISNULL -> {
                    listOfSql.add(prefix + it.sql)
                }

                LIKE -> {
                    when (it.pos) {
                        Left -> paramMap[realName] = "%${paramMap[realName]}"
                        Right -> paramMap[realName] = "${paramMap[realName]}%"
                        Both -> paramMap[realName] = "%${paramMap[realName]}%"
                        else -> {
                        }
                    }
                    listOfSql.add(prefix + it.sql)
                }

                GT, GE, LT, LE -> {
                    if (it.type == GT || it.type == GE) paramMap[realName] =
                        paramMap[realName.replace("Min", "")] ?: paramMap[realName] ?: it.value
                    else paramMap[realName] = paramMap[realName.replace("Max", "")] ?: paramMap[realName] ?: it.value
                    listOfSql.add(prefix + it.sql)
                }

                BETWEEN -> {
                    if (paramMap[realName] is ClosedRange<*>) {
                        paramMap[realName + "Min"] = (paramMap[realName] as ClosedRange<*>).start
                        paramMap[realName + "Max"] = (paramMap[realName] as ClosedRange<*>).endInclusive
                        paramMap.remove(realName)
                    } else {
                        throw IllegalArgumentException("The type `${paramMap[realName]!!::class.java.simpleName}` of the value of BETWEEN is not supported")
                    }

                    listOfSql.add(prefix + it.sql)
                }

                IN -> {
                    if (paramMap[realName] is Collection<*>?) {
                        paramMap[realName] = paramMap[realName] ?: listOf<String>()
                        listOfSql.add(prefix + it.sql)
                    } else {
                        throw IllegalArgumentException("The type `${paramMap[realName]!!::class.java.simpleName}` of the value of IN is not supported")
                    }
                }

                SQL -> listOfSql.add(it.sql)
                AND -> {
                    listOfSql.add(
                        joinSqlStatement(
                            it.children,
                            paramMap,
                            ifNoValueStrategy,
                            "and",
                            (infix != "and"),
                            multiTable
                        )
                    )
                }

                OR -> listOfSql.add(
                    joinSqlStatement(
                        it.children,
                        paramMap,
                        ifNoValueStrategy,
                        "or",
                        (infix != "or"),
                        multiTable
                    )
                )

                else -> {}
            }
        }
        val res = listOfSql.filter { it.isNotBlank() }
        if (res.isEmpty()) return ""
        if (brackets && res.size > 1)
            return "(${res.joinToString(" $infix ")})"
        return res.joinToString(" $infix ")
    }

    fun queryKotoJdbcData(
        jdbcWrapper: KJdbcWrapper,
        sql: String,
        paramMap: Map<String, Any?>,
    ): List<Map<String, Any>> {
        return jdbcWrapper.forList(sql, paramMap)
    }

    fun query(
        jdbcWrapper: KJdbcWrapper? = null,
        sql: String,
        paramMap: Map<String, Any?>
    ): List<Map<String, Any>> {
        val wrapper = getJdbcWrapper(jdbcWrapper)
        log(wrapper, sql, listOf(paramMap), "query")
        return queryKotoJdbcData(wrapper, sql, paramMap)
    }

    private fun getSqlType(sql: String): String {
        return when {
            sql.contains("insert into") || sql.contains("replace into") -> "create"
            sql.startsWith("delete") -> "remove"
            else -> "update"
        }
    }

    /**
     * It executes a SQL statement, and returns the number of rows affected by the statement.
     *
     * @param jdbc NamedParameterJdbcTemplate
     * @param sql The SQL statement to execute.
     * @param paramMap The map of parameters to be used in the query.
     * @return The number of rows affected by the SQL statement.
     */
    fun execute(
        jdbcWrapper: KJdbcWrapper? = null, sql: String, paramMap: Map<String, Any?>
    ): KExecuteResult {
        val wrapper = getJdbcWrapper(jdbcWrapper)

        var lastInsertId: Int? = null
        val affectLineNum = wrapper.update(sql, paramMap)

        if (sql.contains("insert")) {
            lastInsertId =
                wrapper.forObject(
                    when (dbType) {
                        MySql, OceanBase -> "select last_insert_id()"
                        SQLite -> "select last_insert_rowid()"
                        Oracle -> "select last_insert_id() from dual"
                        MSSql -> "select @@identity"
                        PostgreSQL -> "select lastval()"
                        else -> throw IllegalArgumentException("Unsupported database type")
                    }, emptyMap<String, String>(), Integer::class.java
                )?.toInt()
                    ?: 0
            if (lastInsertId == 0) {
                lastInsertId = null
            }
        }
        log(wrapper, sql, listOf(paramMap), getSqlType(sql), "影响行数：$affectLineNum 最后插入ID：$lastInsertId")
        return KExecuteResult(affectLineNum, lastInsertId)
    }

    /**
     * > Execute a batch of SQL statements
     *
     * @param jdbc NamedParameterJdbcTemplate? = null,
     * @param sql The SQL statement to be executed.
     * @param paramMap The parameter array, each element is a parameter map, and the parameter map is the same as the
     * parameter map of the execute method.
     * @return A KotoExecuteResult object.
     */
    fun batchExecute(
        jdbcWrapper: KJdbcWrapper? = null,
        sql: String,
        paramMap: Array<Map<String, Any?>>
    ): Int {
        val wrapper = getJdbcWrapper(jdbcWrapper)
        var affectLineNum = 0

        log(wrapper, sql, paramMap.toList(), getSqlType(sql), "影响行数：$affectLineNum")

        wrapper.batchUpdate(sql, paramMap).forEach {
            affectLineNum += it
        }

        return affectLineNum
    }

}
