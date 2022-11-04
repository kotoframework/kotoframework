package test.wrapper.spring

import com.kotoframework.KotoApp
import com.kotoframework.KotoSpringApp.setDynamicDataSource
import com.kotoframework.function.optionList.optionList
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import test.wrapper.DataSource
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class OptionListTest {
    init {
        KotoApp.setDynamicDataSource { DataSource.namedJdbc }.setLog("console")
    }
    @Test
    fun testBasicUasage() { // 测试基本用法
        val koto = optionList("tb_user", "userName" to "ous")
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), koto.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 200",
            koto.sql
        )
    }

    @Test
    fun testGetMoreFields() { // 测试获取多个字段
        val koto = optionList("tb_user", "userName" to "ous", listOf("userName", "nickName", "age"))
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), koto.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName`,`nick_name` as `nickName`,`age` as `age` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 200",
            koto.sql
        )
    }

    @Test
    fun testSuffix(){
        val koto = optionList("tb_user", "userName" to "ous", listOf("userName", "nickName", "age"), suffix = "order by update_time ASC")
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), koto.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName`,`nick_name` as `nickName`,`age` as `age` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by update_time ASC limit 200",
            koto.sql
        )
    }
}
