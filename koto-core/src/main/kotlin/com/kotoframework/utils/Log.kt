package com.kotoframework.utils

import com.kotoframework.*
import com.kotoframework.KotoApp.dbType
import com.kotoframework.beans.LogLine
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Printer.BLACK
import com.kotoframework.utils.Printer.BLUE
import com.kotoframework.utils.Printer.BOLD
import com.kotoframework.utils.Printer.CYAN
import com.kotoframework.utils.Printer.GREEN
import com.kotoframework.utils.Printer.GREY
import com.kotoframework.utils.Printer.MAGENTA
import com.kotoframework.utils.Printer.RED
import com.kotoframework.utils.Common.currentTimeM
import com.kotoframework.utils.Jdbc.getDBNameFromUrl
import com.kotoframework.utils.Printer.YELLOW
import java.lang.Integer.min
import java.util.regex.Pattern

/**
 * Created by ousc on 2022/4/18 10:35
 */
object Log {
    internal var out: String? = "console"
    internal var simpleLog: Boolean = false

    var tasks = mutableListOf<List<LogLine>>() // 任务队列

    /**
     * It takes a SQL statement and a mode, and returns the table name from the SQL statement according to the mode.
     *
     * @param sql The SQL statement to be executed.
     * @param mode The type of SQL statement, which can be updated, removed, created, queried, and so on.
     * @return The table name from the SQL statement.
     */
    private fun getTableNameFromSQL(sql: String, mode: String): String {
        if (sql.startsWith("select count(*) from (")) {
            val sql2 = sql.substring(22, sql.length - 1)
            return getTableNameFromSQL(sql2, mode)
        }
        val pattern = when (mode) {
            "update" -> Pattern.compile("(?i)update\\s+(\\S+)\\s+set\\s+")
            "remove" -> Pattern.compile("(?i)from\\s+(\\S+)\\s+")
            "create" -> Pattern.compile("(?i)into\\s+(\\S+)\\s+")
            "query" -> Pattern.compile("(?i)from\\s+(\\S+)\\s+")
            else -> Pattern.compile("(?i)from\\s+(\\S+)\\s+")
        }
        val matcher = pattern.matcher(sql)
        if (matcher.find()) {
            return matcher.group(1)
        }
        return ""
    }

    /**
     * It takes a SQL statement and a mode, and returns the table name from the SQL statement according to the mode.
     *
     * @param sql The SQL statement to be executed.
     * @param mode The type of SQL statement, which can be updated, removed, created, queried, and so on.
     * @return The table name from the SQL statement.
     */
    fun log(
        jdbcWrapper: KotoJdbcWrapper? = Jdbc.defaultJdbcWrapper,
        sql: String,
        paramMaps: List<Map<String, Any?>>,
        type: String,
        result: String? = null
    ) {
        if (jdbcWrapper == null) return
        val outs = out?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }
        if (outs.isNullOrEmpty()) return
        val logLines = createLog(jdbcWrapper, sql, paramMaps, type, result)
        if (tasks.isNotEmpty()) {
            tasks.add(logLines)
        } else {
            tasks.add(logLines)
            printLogLines(outs)
        }
    }

    private fun printLogLines(outs: List<String>) {
        Runnable {
            while (tasks.isNotEmpty()) {
                val logLines = tasks.first()
                outs.forEach {
                    for (logLine in logLines) {
                        when (it) {
                            "console" -> {
                                logLine.print()
                            }

                            else -> {
                                logLine.write(it)
                            }
                        }
                    }
                }
                if (tasks.isNotEmpty()) {
                    tasks.removeAt(0)
                }
            }
        }.run()
    }

    private fun createLog(
        jdbcWrapper: KotoJdbcWrapper,
        sql: String,
        paramMaps: List<Map<String, Any?>>,
        type: String,
        result: String? = null
    ): List<LogLine> {
        val dataSource = getDBNameFromUrl(jdbcWrapper.url)
        val tableName = getTableNameFromSQL(sql.lowercase(), type)
        val logLines = mutableListOf(
            LogLine("INFO START | ", arrayOf(GREEN, BOLD)),
            LogLine("$currentTimeM | ", arrayOf(BLUE, BOLD)),
            LogLine(jdbcWrapper::class.java.name, arrayOf(YELLOW, BOLD)).endl(),
            LogLine("${"-".repeat(30)}Koto操作开始${"-".repeat(30)}\n", arrayOf(BLACK, BOLD)).endl(),
            LogLine("数据源\t：\t\t${dataSource}", arrayOf(GREEN, BOLD)).endl(),
            LogLine("数据表名\t：\t\t${tableName}", arrayOf(CYAN, BOLD)).endl(),
            LogLine("操作类型\t：\t\t${type}", arrayOf(RED, BOLD)).endl(),
            LogLine("执行语句\t：", arrayOf(BLACK, BOLD)).endl(),
            LogLine("\t\t${sql}", arrayOf(BLACK, BOLD)).endl()
        )
        if (paramMaps.isNotEmpty() && paramMaps.any { it.filter { mapIt -> mapIt.value != null }.isNotEmpty() }) {
            logLines.add(LogLine("数据字典\t：\t\t", arrayOf(MAGENTA, BOLD)).endl())
            if (paramMaps.size == 1 && !simpleLog) {
                logLines.addAll(printParamMap("row", paramMaps.first()))
            } else {
                logLines.addAll(printParamMap("col", paramMaps = paramMaps))
            }
        }
        if (result != null) {
            logLines.addAll(
                listOf(
                    LogLine("-".repeat(50), arrayOf(BLACK, BOLD)).endl(),
                    LogLine(result, arrayOf(RED, BOLD)).endl()
                )
            )
        }
        logLines.add(LogLine("${"-".repeat(30)}Koto操作结束${"-".repeat(30)}\n", arrayOf(BLACK, BOLD)))
        return logLines
    }

    private fun printParamMap(
        type: String,
        paramMap: Map<String, Any?>? = null,
        paramMaps: List<Map<String, Any?>>? = null
    ): MutableList<LogLine> {
        val logLines = mutableListOf<LogLine>()
        if (type == "row") {
            val paramList = paramMap!!.toList().filter { it.second != null }
            val maxLength = paramList.map { it.first.length }.maxOf { it }
            logLines.addAll(
                listOf(
                    LogLine("\t\t${"字段名".padEnd(maxLength)}\t\t｜\t\t值", arrayOf(BLACK, BOLD)).endl(),
                    LogLine("-".repeat(50), arrayOf(BLACK, BOLD)).endl()
                )
            )
            paramList.forEach {
                val secondStr = it.second.toString()
                logLines.addAll(
                    listOf(
                        LogLine("\t\t${it.first.padEnd(maxLength)}\t\t", arrayOf(MAGENTA, BOLD)),
                        LogLine("｜", arrayOf(BLACK, BOLD)),
                        LogLine(
                            "\t\t${secondStr.slice(0..min(secondStr.length - 1, 100))}",
                            arrayOf(GREY, BOLD)
                        ).endl()
                    )
                )
            }
        } else {
            logLines.addAll(
                listOf(
                    LogLine("-".repeat(50), arrayOf(BLACK, BOLD)).endl()
                )
            )
            paramMaps!!.forEachIndexed { index, map ->
                logLines.addAll(
                    listOf(
                        LogLine("\t\t${"第${index + 1}行".padEnd(10)}\t\t", arrayOf(MAGENTA, BOLD)),
                        LogLine("｜", arrayOf(BLACK, BOLD)),
                        LogLine("\t\t", arrayOf(GREY, BOLD)),
                        LogLine(simpleMap2String(map), arrayOf(GREY, BOLD)).endl()
                    )
                )
            }
        }
        return logLines
    }

    private fun simpleMap2String(map: Map<String, Any?>): String {
        return map.map {
            "${it.key}=${it.value}"
        }.joinToString(",")
    }
}
