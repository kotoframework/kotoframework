package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.function.columnSearch.columnSearch
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import test.wrapper.beans.TbUser
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class ColumnSearchTest {
    init {
        KotoApp.setLog("console")
    }

    @Test
    fun testBasicUasage() { // 测试基本用法
        val prepared = columnSearch("tb_user", "userName" to "ous")
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), prepared.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 200",
            prepared.sql
        )
    }

    @Test
    fun testGetMoreFields() { // 测试获取多个字段
        val prepared = columnSearch("tb_user", "userName" to "ous", listOf("userName", "nickName", "age"))
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), prepared.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName`,`nick_name` as `nickName`,`age` as `age` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 200",
            prepared.sql
        )
    }

    @Test
    fun testSuffix() {
        val prepared = columnSearch(
            "tb_user",
            "userName" to "ous",
            listOf("userName", "nickName", "age"),
            suffix = "order by update_time ASC"
        )
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), prepared.paramMap
        )
        assertEquals(
            "select distinct `user_name` as `userName`,`nick_name` as `nickName`,`age` as `age` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by update_time ASC limit 200",
            prepared.sql
        )
    }

    @Test
    fun testLimit() {
        val prepared = columnSearch("tb_user", "userName" to "ous", listOf("userName", "nickName", "age"), limit = 10)
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), prepared.paramMap
        )

        assertEquals(
            "select distinct `user_name` as `userName`,`nick_name` as `nickName`,`age` as `age` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 10",
            prepared.sql
        )
    }

    @Test
    fun testKPojoInputSupport() {
        val prepared = columnSearch(TbUser::userName to "ous", limit = 10)
        assertEquals(
            mapOf(
                "userName" to "%ous%"
            ), prepared.paramMap
        )

        assertEquals(
            "select distinct `user_name` as `userName` from tb_user where ${deleted()} and UPPER(`user_name`) like UPPER(:userName) and `user_name` is not null order by `id` desc limit 10",
            prepared.sql
        )
    }
}
