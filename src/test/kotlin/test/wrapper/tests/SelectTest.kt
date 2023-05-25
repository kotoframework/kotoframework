package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.beans.KotoResultSet.Companion.convertCountSql
import com.kotoframework.definition.asc
import com.kotoframework.definition.desc
import com.kotoframework.function.select.select
import com.kotoframework.core.condition.*
import com.kotoframework.core.future.from
import com.kotoframework.core.future.table
import com.kotoframework.utils.Common.deleted
import test.wrapper.beans.TbUser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class SelectTest {
    init {
        KotoApp.setLog("console")
    }

    @Test
    fun testSelect() { // test select
        //In fact, the following ways are equivalent
        // select(TbUser(1)).where.query()
        // select(TbUser(1)).where().query()
        // select(TbUser(1)).by(TbUser::id).query()
        // select(TbUser(1)).by("id").query()
        // select<TbUser>().by(TbUser::id to 1).query()
        // select<TbUser>().by("id" to 1).query()
        // select<TbUser>().where(TbUser::id.eq(1)).query()
        // select<TbUser>().where{ it::id.eq(1) }.query()
        // TbUser(1).select.where.query()
        // TbUser(1).select().where.query()
        // TbUser(1).select().where().query()
        // from<TbUser> { user -> user.select.by(user::id to 1) }.query()
        // from<TbUser> { user -> user.select.where(user::id.eq(1)) }.query()
        // from(TbUser(1)) { user -> user.select.by(user::id) }.query()
        // from(TbUser(1)) { user -> user.select.by("id") }.query()
        // from(TbUser(1)) { user -> user.select.where }.query()
        // from(TbUser(1)) { user -> user.select.where() }.query()
        // from(TbUser(1)) { user -> user.select.where(user::id.eq) }.query()
        // from(TbUser(1)) { user -> user.select.where(user::id.eq()) }.query()


        val user = TbUser(
            userName = "ousc",
            age = 15
        )
        val (prepared) = user.select().where().distinct().page(1, 20).orderBy(user::updateTime.desc())

        assertEquals(
            "select distinct `age`, `avatar`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `email_address` as `emailAddress`, `id`, `nickname`, `password`, `sex`, `phone_number` as `phoneNumber`, `user_name` as `userName` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age order by `update_time` DESC limit 20 offset 0",
            prepared.sql
        )

        assertEquals(
            "select count(*) from (select 1 from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age order by `update_time` DESC ) as t",
            convertCountSql(prepared.sql)
        )

        val expectedMap = mapOf<String, Any?>(
            "age" to 15,
            "avatar" to null,
            "birthday" to null,
            "email" to null,
            "id" to null,
            "nickname" to null,
            "password" to null,
            "sex" to null,
            "telephone" to null,
            "userName" to "ousc"
        )
        assertEquals(
            expectedMap,
            prepared.paramMap
        )
    }

    @Test
    fun testSelectBy() {
        val (prepared) = from<TbUser> { user -> user.select(user::userName, user::birthday).by(user::id to 1) }
        val expectedMap = mapOf<String, Any?>(
            "age" to null,
            "avatar" to null,
            "birthday" to null,
            "email" to null,
            "id" to 1,
            "nickname" to null,
            "password" to null,
            "sex" to null,
            "telephone" to null,
            "userName" to null
        )
        assertEquals(
            "select `user_name` as `userName`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday` from tb_user where ${deleted()} and `id` = :id",
            prepared.sql.trim()
        )
        assertEquals(
            expectedMap,
            prepared.paramMap
        )
    }

    @Test
    fun testLimitOne() { // 测试只要第一条数据的情况
        val user = TbUser(
            userName = "ousc",
            age = 15
        )
        val (prepared) = user.select().where().first()
        assertEquals(
            "select `age`, `avatar`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `email_address` as `emailAddress`, `id`, `nickname`, `password`, `sex`, `phone_number` as `phoneNumber`, `user_name` as `userName` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
            prepared.sql.trim()
        )

        val expectedMap = mapOf<String, Any?>(
            "age" to 15,
            "avatar" to null,
            "birthday" to null,
            "email" to null,
            "id" to null,
            "nickname" to null,
            "password" to null,
            "sex" to null,
            "telephone" to null,
            "userName" to "ousc"
        )
        assertEquals(
            expectedMap,
            prepared.paramMap
        )
    }


    @Test
    fun testOnlySelectPartOfFields() {
        val user = TbUser(
            userName = "ousc",
            age = 15
        )
        val (prepared) = select(user, "userName", "age" to "AGE").where().first()

        assertEquals(
            "select `user_name` as `userName`, `age` as `AGE` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
            prepared.sql.trim()
        )

        val expectedMap = mapOf<String, Any?>(
            "age" to 15,
            "avatar" to null,
            "birthday" to null,
            "email" to null,
            "id" to null,
            "nickname" to null,
            "password" to null,
            "sex" to null,
            "telephone" to null,
            "userName" to "ousc"
        )
        assertEquals(
            expectedMap,
            prepared.paramMap
        )
    }

    @Test
    fun testFuture() {
        val user = TbUser(
            userName = "ousc",
            age = 15
        )

        table(user) {
            val (prepared) =
                select(it, it::id)
                    .where(it::userName.eq and it::age.eq.alias("aage"))
                    .distinct()
                    .orderBy(it::id.desc(), it::updateTime.asc())
                    .groupBy(it::sex)

            assertEquals(
                "select distinct `id` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :aage group by `sex` order by `id` DESC,`update_time` ASC",
                prepared.sql.trim()
            )

            val expectedMap = mapOf<String, Any?>(
                "age" to 15,
                "aage" to 15,
                "avatar" to null,
                "birthday" to null,
                "email" to null,
                "id" to null,
                "nickname" to null,
                "password" to null,
                "sex" to null,
                "telephone" to null,
                "userName" to "ousc"
            )

            assertEquals(
                expectedMap,
                prepared.paramMap
            )
        }
    }
}
