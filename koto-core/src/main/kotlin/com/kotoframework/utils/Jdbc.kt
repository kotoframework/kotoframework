package com.kotoframework.utils

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.core.condition.Criteria
import com.kotoframework.*
import com.kotoframework.beans.TableColumn
import com.kotoframework.beans.TableMeta
import com.kotoframework.beans.TableObject
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.interfaces.KotoQueryHandler
import com.kotoframework.utils.Extension.isNullOrEmpty
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Log.log
import java.util.*

/**
 * Created by ousc on 2022/4/18 13:12
 */

object Jdbc {

    var tableMap = mutableMapOf<String, TableObject>() // 存储表名和字段名的映射关系

    var defaultJdbcHandler: KotoQueryHandler? = null
    var defaultJdbcWrapper: KotoJdbcWrapper? = null

    /**
     * If the namedJdbc parameter is not null, return it. Otherwise, if the defaultNamedJdbc is not null, return it.
     * Otherwise, throw a RuntimeException
     *
     * @param jdbcWrapper The NamedParameterJdbcTemplate object that you want to use.
     * @return The namedJdbc parameter if it is not null, otherwise the defaultNamedJdbc if it is not null, otherwise an
     * exception is thrown.
     */
    fun getJdbcWrapper(jdbcWrapper: KotoJdbcWrapper?): KotoJdbcWrapper {
        return jdbcWrapper ?: defaultJdbcWrapper ?: throw RuntimeException("jdbcWrapper is null")
    }

    val KotoJdbcWrapper.dbName
        get() = url.split("?").first().split(
            "//"
        )[1].split("/")[1]


    /**
     * It gets the table structure from the database and stores it in a map.
     *
     * @param tableName The name of the table to be queried
     * @param namedJdbc NamedParameterJdbcTemplate? = null
     * @return A TableObject
     */
    fun initMetaData(
        meta: TableMeta,
        jdbcWrapper: KotoJdbcWrapper? = null,
    ): TableObject {
        val wrapper = getJdbcWrapper(jdbcWrapper)
        val key = "${wrapper.dbName}_${meta.tableName}"
        if (tableMap[key] != null) {
            return tableMap[key]!!
        }
        val list = wrapper.forList("show full fields from ${meta.tableName}", mapOf())
        val columns = list.map {
            TableColumn(
                it["Field"] as String,
                it["Type"] as String
            )
        }
        tableMap[key] =
            TableObject(
                columns,
                meta
            )
        return tableMap[key]!!
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
        ifNoValueStrategy: (Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy },
        join: String = "and",
        brackets: Boolean = false,
        showAlias: Boolean = false
    ): String {
        val sqls = mutableListOf<String>()
        conditions.filterNotNull().forEach {
            val alias = if (showAlias) "${it.tableName!!.lineToHump()}." else ""

            if (paramMap[it.parameterName] is List<*> && it.type != IN) {
                it.type = IN
            }

            if (paramMap[it.parameterName] is ClosedRange<*> && it.type != BETWEEN) {
                it.type = BETWEEN
            }

            val realName = when {
                !it.reName.isNullOrBlank() -> it.reName
                else -> it.parameterName
            }!!

            if (it.valueAcceptable) {
                if (!paramMap[it.parameterName].isNullOrEmpty() && paramMap[realName].isNullOrEmpty()) {
                    paramMap[realName] = paramMap[it.parameterName]
                }
                if (!it.value.isNullOrEmpty())
                    paramMap[realName] = it.value ?: paramMap[realName]
            }

            if (paramMap[realName].isNullOrEmpty() && ifNoValueStrategy(it).ignore() && it.noValueStrategy.ignore() && it.valueAcceptable) return@forEach

            val defaultIfNoValue: String? = if (paramMap[realName].isNullOrEmpty() && it.valueAcceptable) {
                it.noValueStrategy.dealWithNoValue(alias, realName, it, ifNoValueStrategy(it))
            } else null

            if (defaultIfNoValue != null) {
                sqls.add(defaultIfNoValue)
                return@forEach
            }

            when (it.type) {
                EQUAL, ISNULL -> {
                    sqls.add(alias + it.sql)
                }

                LIKE -> {
                    when (it.pos) {
                        Left -> paramMap[realName] = "%${paramMap[realName]}"
                        Right -> paramMap[realName] = "${paramMap[realName]}%"
                        Both -> paramMap[realName] = "%${paramMap[realName]}%"
                        else -> {
                        }
                    }
                    sqls.add(alias + it.sql)
                }

                GT, GE, LT, LE -> {
                    if (it.type == GT || it.type == GE) paramMap[realName] =
                        paramMap[realName.replace("Min", "")] ?: paramMap[realName] ?: it.value
                    else paramMap[realName] = paramMap[realName.replace("Max", "")] ?: paramMap[realName] ?: it.value
                    sqls.add(alias + it.sql)
                }

                BETWEEN -> {
                    if (it.value is ClosedRange<*>) {
                        paramMap[realName + "Min"] = it.value.start
                        paramMap[realName + "Max"] = it.value.endInclusive
                        paramMap.remove(realName)
                    } else {
                        throw IllegalArgumentException("The type of the value of BETWEEN is not supported")
                    }

                    sqls.add(alias + it.sql)
                }

                IN -> {
                    if (it.value is Collection<*>) {
                        paramMap[realName] = paramMap[realName] ?: listOf<String>()
                        sqls.add(alias + it.sql)
                    } else {
                        throw IllegalArgumentException("The type of the value of IN is not supported")
                    }
                }

                SQL -> sqls.add(it.sql)
                AND -> {
                    sqls.add(
                        joinSqlStatement(
                            it.collections,
                            paramMap,
                            ifNoValueStrategy,
                            "and",
                            (join != "and"),
                            showAlias
                        )
                    )
                }

                OR -> sqls.add(
                    joinSqlStatement(
                        it.collections,
                        paramMap,
                        ifNoValueStrategy,
                        "or",
                        (join != "or"),
                        showAlias
                    )
                )

                else -> {}
            }
        }
        val res = sqls.filter { it.isNotBlank() }
        if (res.isEmpty()) return ""
        if (brackets && res.size > 1)
            return "(${res.joinToString(" $join ")})"
        return res.joinToString(" $join ")
    }

    fun queryKotoJdbcData(
        jdbcWrapper: KotoJdbcWrapper,
        sql: String,
        paramMap: Map<String, Any?>,
    ): List<Map<String, Any>> {
        return jdbcWrapper.forList(sql, paramMap)
    }

    fun query(
        jdbcWrapper: KotoJdbcWrapper? = null,
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
        jdbcWrapper: KotoJdbcWrapper? = null, sql: String, paramMap: Map<String, Any?>
    ): KotoExecuteResult {
        val wrapper = getJdbcWrapper(jdbcWrapper)

        var lastInsertId: Int? = null
        val affectLineNum = wrapper.update(sql, paramMap)

        if (sql.contains("insert")) {
            lastInsertId =
                wrapper.forObject("select last_insert_id()", emptyMap<String, String>(), Integer::class.java)?.toInt()
                    ?: 0
            if (lastInsertId == 0) {
                lastInsertId = null
            }
        }
        log(wrapper, sql, listOf(paramMap), getSqlType(sql), "影响行数：$affectLineNum 最后插入ID：$lastInsertId")
        return KotoExecuteResult(affectLineNum, lastInsertId)
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
        jdbcWrapper: KotoJdbcWrapper? = null,
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
