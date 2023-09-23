//package test.wrapper.tests
//
//import com.kotoframework.KotoApp
//import test.wrapper.beans.TbUser
//import com.kotoframework.function.create.create
//import com.kotoframework.core.annotations.NeedTableIndexes
//import com.kotoframework.utils.deleted
//import com.kotoframework.utils.toMutableMap
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals
//
///**
// * Created by ousc on 2022/4/18 16:41
// */
//class CreateTest {
//    init {
//        KotoApp.setLog("console", true)
//    }
//
//    private val user = TbUser(
//        id = 300,
//        userName = "Leinbo",
//        password = "Leinbo",
//        nickname = "Leinbo",
//        email = "mail@leinbo.com",
//        age = 18,
//        birthday = "2020-08-02",
//        telephone = "13888888888",
//        sex = "male",
//        avatar = "http://cdn.leinbo.com/avatar.png"
//    )
//
//
//    @Test
//    fun testBasicCreate() { // test basic create
//        val (prepared) = create(user)
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//        assertEquals(
//            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime)",
//            prepared.sql.trim()
//        )
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//    }
//
//    @Test
//    fun testOnId() { // test on id
//        val (prepared) = create(user).onId()
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//        assertEquals(
//            "replace into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime)",
//            prepared.sql.trim()
//        )
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//    }
//
//    @Test
//    fun testOnFields() { // test on fields
//        val (prepared) = create(user).on("userName", "birthday", "sex").update("userName")
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//
//        assertEquals(
//            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) select :age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime from dual where not exists (select 1 from tb_user where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName)",
//            prepared.sql.trim()
//        )
//
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//
//        assertEquals(
//            "update tb_user set `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName",
//            prepared.then?.sql?.trim()
//        )
//    }
//
//    @Test
//    fun testCreateExpect() { // test create except
//        val (prepared) = create(user).on("userName", "birthday", "sex").except("id")
//        assertEquals(
//            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) select :age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime from dual where not exists (select 1 from tb_user where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName)",
//            prepared.sql.trim()
//        )
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//
//        assertEquals(
//            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `birthday` = :birthday and `sex` = :sex and `user_name` = :userName",
//            prepared.then?.sql?.trim()
//        )
//    }
//
//    @OptIn(NeedTableIndexes::class)
//    @Test
//    fun testOnDuplicateUpdate() { // test on duplicate update
//        val (prepared) = create(user).onDuplicateUpdate().update("userName")
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//
//        assertEquals(
//            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime) on duplicate key update `user_name` = :userName",
//            prepared.sql.trim()
//        )
//
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//    }
//
//    @Test
//    fun testCreateUseExpand() { // test create use expand
//        val (prepared) = user.create()
//        val expectedParamMap =
//            user.toMutableMap(
//                "updateTime" to prepared.paramMap["updateTime"],
//                "createTime" to prepared.paramMap["createTime"]
//            )
//
//        assertEquals(
//            "insert into tb_user (`age`,`avatar`,`birthday`,`email_address`,`id`,`nickname`,`password`,`sex`,`phone_number`,`user_name`,`update_time`,`create_time`) values (:age,:avatar,:birthday,:email,:id,:nickname,:password,:sex,:telephone,:userName,:updateTime,:createTime)",
//            prepared.sql.trim()
//        )
//
//        assertEquals(
//            expectedParamMap, prepared.paramMap
//        )
//
//    }
//}
