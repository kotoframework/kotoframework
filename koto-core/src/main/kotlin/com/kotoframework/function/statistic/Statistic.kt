package com.kotoframework.function.statistic

import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc.getJdbcWrapper

/**
 * Created by ousc on 2022/4/9 08:50
 */
class Statistic(private val tableName: String, private val kotoJdbcWrapper: KotoJdbcWrapper? = null) {
    private var total = true
    private var mode: String = "pastAll"
    private var days = 0

    /**
     * > This function sets the mode to week and the days to 7
     *
     * @return The Statistic object
     */
    fun week(): Statistic {
        this.mode = "week"
        this.days = 7
        return this
    }

    /**
     * It sets the mode to month and the days to 30.
     *
     * @return The Statistic object
     */
    fun month(): Statistic {
        this.mode = "month"
        this.days = 30
        return this
    }

    /**
     * `pastNDays` is a function that takes an integer as an argument and returns a `Statistic` object
     *
     * @param days The number of days to go back.
     * @return The Statistic object
     */
    fun pastNDays(days: Int): Statistic {
        this.mode = "pastNDays"
        this.days = days
        return this
    }

    /**
     * If the total is false, return the statistic.
     *
     * @return The Statistic object
     */
    fun everyDay(): Statistic {
        this.total = false
        return this
    }

    val sql: String
        get() {
            return when (total) {
                true -> "select count(1) from $tableName where ${
                    deleted(
                        0,
                        kotoJdbcWrapper,
                        tableName
                    )
                }" + when (mode) {
                    "week" -> " and YEARWEEK(DATE_FORMAT(update_time,'%Y-%m-%d'))=YEARWEEK(now())"
                    "month" -> " and DATE_FORMAT(update_time, '%Y%m')=DATE_FORMAT(CURDATE() ,'%Y%m')"
                    "pastNDays" -> " and DATE_FORMAT(update_time, '%Y-%m-%d')>=DATE_FORMAT(curdate(), '%Y-%m-%d') - ${this.days}"
                    else -> ""
                }

                else -> "select count(1) num, DATE_FORMAT(create_time,'%Y-%m-%d') date from $tableName where ${
                    deleted(
                        0,
                        kotoJdbcWrapper,
                        tableName
                    )
                } and DATE_FORMAT(create_time,'%Y-%m-%d')>=date_sub(curdate(),interval ${this.days} day) and DATE_FORMAT(create_time,'%Y-%m-%d')<=curdate() group by DATE_FORMAT(create_time,'%Y-%m-%d')"
            }
        }

    /**
     * > This function takes a JdbcTemplate as an argument and returns an Int
     *
     * @param jdbcTemplate JdbcTemplate
     * @return The number of rows in the table.
     */
    fun queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): Int {
        val wrapper = getJdbcWrapper(jdbcWrapper)
        Log.log(wrapper, sql, listOf(mapOf("type" to this.total)), "statistic")
        return wrapper.forObject(sql, mapOf(), Int::class.java)!!
    }

    data class StatisticResult(
        val countList: List<Int>,
        val dateList: List<String>
    )

    /**
     * > It takes a JdbcTemplate as an argument, executes a query, and returns a StatisticResult object
     *
     * @param jdbcTemplate JdbcTemplate
     * @return A StatisticResult object with a list of counts and a list of dates.
     */
    fun queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): StatisticResult {
        val wrapper = getJdbcWrapper(jdbcWrapper)
        Log.log(wrapper, sql, listOf(), "statistic")
        val list = wrapper.forList(sql, mapOf())
        val countList = list.map { (it["num"] as Long).toInt() }.toList()
        val dateList = list.map { it["date"] as String }.toList()
        return StatisticResult(countList = countList, dateList = dateList)
    }

    companion object {
        fun statistic(tableName: String): Statistic {
            return Statistic(tableName)
        }
    }
}
