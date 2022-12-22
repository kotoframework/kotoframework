package test.wrapper.basic

import com.kotoframework.BasicJdbcWrapper.Companion.transact
import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import test.wrapper.DataSource.namedJdbc
import com.kotoframework.KotoApp
import com.kotoframework.KotoBasicJdbcApp.setDynamicDataSource
import com.kotoframework.function.create.Patch.execute
import test.wrapper.beans.TbUser
import com.kotoframework.function.create.create
import com.kotoframework.interfaces.Patch.execute
import com.kotoframework.core.annotations.NeedTableIndexes
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Extension.toKPojo
import com.kotoframework.utils.Extension.toMap
import com.kotoframework.utils.Extension.toMutableMap
import org.junit.jupiter.api.Test
import test.wrapper.DataSource.dataSource
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 16:41
 */
class CreateTest {
    init {
        KotoApp.setDynamicDataSource { dataSource }.setLog("console", true)
    }

    val user = TbUser(
        id = 300,
        userName = "Leinbo",
        password = "Leinbo",
        nickname = "Leinbo",
        email = "mail@leinbo.com",
        age = 18,
        birthday = "2020-08-02",
        telephone = "13888888888",
        sex = "male",
        avatar = "http://cdn.leinbo.com/avatar.png"
    )


    @Test
    fun testBasicCreate() {
        val koto = create(user).build()
        val expectedParamMap =
            user.toMutableMap("updateTime" to koto.paramMap["updateTime"], "createTime" to koto.paramMap["createTime"])
        assertEquals(
            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime)",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun testOnId() { // 测试当id相同时update，id需建立索引
        val koto = create(user).onId().build()
        val expectedParamMap =
            user.toMutableMap("updateTime" to koto.paramMap["updateTime"], "createTime" to koto.paramMap["createTime"])
        assertEquals(
            "replace into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime)",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun testOnFields() { // 测试当满足多个条件时更新记录，要先查，速度慢
        val koto = create(user).on("userName", "birthday", "sex").update("userName").build()
        val expectedParamMap =
            user.toMutableMap("updateTime" to koto.paramMap["updateTime"], "createTime" to koto.paramMap["createTime"])

        assertEquals(
            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) select :age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime from dual where not exists (select 1 from tb_user where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName)",
            koto.sql.trim()
        )

        assertEquals(
            expectedParamMap, koto.paramMap
        )

        assertEquals(
            "update tb_user set `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName",
            koto.then?.sql?.trim()
        )
    }

    @Test
    fun testCreateExpect() {
        val koto = create(user).on("userName", "birthday", "sex").except("id").build()
        assertEquals(
            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) select :age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime from dual where not exists (select 1 from tb_user where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName)",
            koto.sql.trim()
        )
        val expectedParamMap =
            user.toMutableMap("updateTime" to koto.paramMap["updateTime"], "createTime" to koto.paramMap["createTime"])

        assertEquals(
            expectedParamMap, koto.paramMap
        )

        assertEquals(
            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName",
            koto.then?.sql?.trim()
        )
    }

    @OptIn(NeedTableIndexes::class)
    @Test
    fun testOnDuplicateUpdate() { // 测试当id相同时update
        val koto = create(user).onDuplicateUpdate().update("userName").build()
        val expectedParamMap =
            user.toMutableMap("updateTime" to koto.paramMap["updateTime"], "createTime" to koto.paramMap["createTime"])

        assertEquals(
            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime) on duplicate key update `user_name` = :userName",
            koto.sql.trim()
        )

        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }
}
