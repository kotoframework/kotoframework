package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.function.statistic.Statistic.Companion.statistic
import com.kotoframework.interfaces.KPojo
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class StatisticTest : KPojo {
    init {
        KotoApp.setLog("console")
    }
    @Test
    fun testGetPastTotal() { // 测试获取过去全部时间的总数
        val koto = statistic("user_info")
        assertEquals("select count(1) from user_info where ${deleted()}", koto.sql)
    }

    @Test
    fun testGetPastWeekTotal() { // 测试获取过去一周的总数
        val koto = statistic("user_info").week()
        assertEquals(
            "select count(1) from user_info where ${deleted()} and YEARWEEK(DATE_FORMAT(update_time,'%Y-%m-%d'))=YEARWEEK(now())",
            koto.sql
        )
    }

    @Test
    fun testGetPastMonthTotal() { // 测试获取过去一月的总数
        val koto = statistic("user_info").month()
        assertEquals(
            "select count(1) from user_info where ${deleted()} and DATE_FORMAT(update_time, '%Y%m')=DATE_FORMAT(CURDATE() ,'%Y%m')",
            koto.sql
        )
    }

    @Test
    fun testGetPastNDaysTotal() { // 测试获取过去N天的总数
        val koto = statistic("user_info").pastNDays(14)
        assertEquals(
            "select count(1) from user_info where ${deleted()} and DATE_FORMAT(update_time, '%Y-%m-%d')>=DATE_FORMAT(curdate(), '%Y-%m-%d') - 14",
            koto.sql
        )
    }

    @Test
    fun testGetPastNDaysList() { // 测试获取过去N天的Date和num的列表
        val koto = statistic("user_info").pastNDays(14).everyDay()
        assertEquals(
            "select count(1) num, DATE_FORMAT(create_time,'%Y-%m-%d') date from user_info where ${deleted()} and DATE_FORMAT(create_time,'%Y-%m-%d')>=date_sub(curdate(),interval 14 day) and DATE_FORMAT(create_time,'%Y-%m-%d')<=curdate() group by DATE_FORMAT(create_time,'%Y-%m-%d')",
            koto.sql
        )
    }
}
