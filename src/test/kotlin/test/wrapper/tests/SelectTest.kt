//package test.wrapper.tests
//
//import com.kotoframework.KotoApp
//import com.kotoframework.beans.KotoResultSet.Companion.convertCountSql
//import com.kotoframework.definition.asc
//import com.kotoframework.definition.desc
//import com.kotoframework.function.select.select
//import com.kotoframework.core.condition.*
//import com.kotoframework.orm.from
//import com.kotoframework.utils.deleted
//import test.wrapper.beans.TbUser
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals
//
///**
// * Created by ousc on 2022/4/18 21:46
// */
//class SelectTest {
//    init {
//        KotoApp.setLog("console")
//    }
//
//    @Test
//    fun testSelect() { // test select
//        //In fact, the following ways are equivalent
//
//
//        val user = TbUser(
//            id = 1
//        )
//
//        val prepared = listOf(
//            from(TbUser()) {
//                select("*", "count(*) as `count`", it::sex, it::avatar)
//                where(TbUser::id.eq(1))
//                where(it::id.eq(1)).distinct().orderBy(user::updateTime.desc()).page(1, 20)
//            },
//            select(TbUser(1)).where().distinct().page(1, 20).orderBy(user::updateTime.desc()).prepared,
//            select(TbUser(1)).by(TbUser::id).distinct().page(1, 20).orderBy(user::updateTime.desc()).prepared,
//            select(TbUser(1)).by("id").distinct().page(1, 20).orderBy(user::updateTime.desc()).prepared,
//            select<TbUser>().by(TbUser::id to 1).distinct().page(1, 20).orderBy(user::updateTime.desc()).prepared,
//            select<TbUser>().by("id" to 1).distinct().page(1, 20).orderBy(user::updateTime.desc()).prepared,
//            select<TbUser>().where(TbUser::id.eq(1)).distinct().page(1, 20).orderBy(user::updateTime.desc())
//                .prepared,
//            select<TbUser>().where { it::id.eq(1) }.distinct().page(1, 20).orderBy(user::updateTime.desc())
//                .prepared,
//            from(user) { where().distinct().page(1, 20).orderBy(user::updateTime.desc()) }
//
//        )
//
//        prepared.forEach {
//            assertEquals(
//                "select distinct `age`, `avatar`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `email_address` as `emailAddress`, `id`, `nickname`, `password`, `sex`, `phone_number` as `phoneNumber`, `user_name` as `userName` from tb_user where ${deleted()} and `id` = :id order by `update_time` DESC limit 20 offset 0",
//                it.sql.trim()
//            )
//
//            assertEquals(
//                "select count(*) from (select 1 from tb_user where ${deleted()} and `id` = :id order by `update_time` DESC ) as t",
//                convertCountSql(it.sql.trim())
//            )
//
//            val expectedMap = mapOf<String, Any?>(
//                "birthday" to null,
//                "password" to null,
//                "sex" to null,
//                "nickname" to null,
//                "telephone" to null,
//                "avatar" to null,
//                "id" to 1,
//                "userName" to null,
//                "age" to null,
//                "email" to null,
//            )
//            assertEquals(
//                expectedMap,
//                it.paramMap
//            )
//        }
//    }
//
//    @Test
//    fun testSelectBy() {
//        val prepared = from<TbUser> { user -> select(user::userName, user::birthday).by(user::id to 1) }
//        val expectedMap = mapOf<String, Any?>(
//            "age" to null,
//            "avatar" to null,
//            "birthday" to null,
//            "email" to null,
//            "id" to 1,
//            "nickname" to null,
//            "password" to null,
//            "sex" to null,
//            "telephone" to null,
//            "userName" to null
//        )
//        assertEquals(
//            "select `user_name` as `userName`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday` from tb_user where ${deleted()} and `id` = :id",
//            prepared.sql.trim()
//        )
//        assertEquals(
//            expectedMap,
//            prepared.paramMap
//        )
//    }
//
//    @Test
//    fun testLimitOne() { // 测试只要第一条数据的情况
//        val user = TbUser(
//            userName = "ousc",
//            age = 15
//        )
//        val (prepared) = select(user).where().first()
//        assertEquals(
//            "select `age`, `avatar`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `email_address` as `emailAddress`, `id`, `nickname`, `password`, `sex`, `phone_number` as `phoneNumber`, `user_name` as `userName` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
//            prepared.sql.trim()
//        )
//
//        val expectedMap = mapOf<String, Any?>(
//            "age" to 15,
//            "avatar" to null,
//            "birthday" to null,
//            "email" to null,
//            "id" to null,
//            "nickname" to null,
//            "password" to null,
//            "sex" to null,
//            "telephone" to null,
//            "userName" to "ousc"
//        )
//        assertEquals(
//            expectedMap,
//            prepared.paramMap
//        )
//    }
//
//
//    @Test
//    fun testOnlySelectPartOfFields() {
//        val user = TbUser(
//            userName = "ousc",
//            age = 15
//        )
//        val (prepared) = select(user, "userName", "age" to "AGE").where().first()
//
//        assertEquals(
//            "select `user_name` as `userName`, `age` as `AGE` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
//            prepared.sql.trim()
//        )
//
//        val expectedMap = mapOf<String, Any?>(
//            "age" to 15,
//            "avatar" to null,
//            "birthday" to null,
//            "email" to null,
//            "id" to null,
//            "nickname" to null,
//            "password" to null,
//            "sex" to null,
//            "telephone" to null,
//            "userName" to "ousc"
//        )
//        assertEquals(
//            expectedMap,
//            prepared.paramMap
//        )
//    }
//
//    @Test
//    fun testFuture() {
//        val user = TbUser(
//            userName = "ousc",
//            age = 15
//        )
//
//        from(user) {
//            val (prepared) =
//                select(it, it::id)
//                    .where(it::userName.eq and it::age.eq.alias("aage"))
//                    .distinct()
//                    .orderBy(it::id.desc(), it::updateTime.asc())
//                    .groupBy(it::sex)
//
//            assertEquals(
//                "select distinct `id` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :aage group by `sex` order by `id` DESC,`update_time` ASC",
//                prepared.sql.trim()
//            )
//
//            val expectedMap = mapOf<String, Any?>(
//                "age" to 15,
//                "aage" to 15,
//                "avatar" to null,
//                "birthday" to null,
//                "email" to null,
//                "id" to null,
//                "nickname" to null,
//                "password" to null,
//                "sex" to null,
//                "telephone" to null,
//                "userName" to "ousc"
//            )
//
//            assertEquals(
//                expectedMap,
//                prepared.paramMap
//            )
//            1
//        }
//    }
//}
