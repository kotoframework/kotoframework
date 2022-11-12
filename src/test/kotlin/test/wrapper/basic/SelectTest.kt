package test.wrapper.basic

import com.kotoframework.KotoApp
import com.kotoframework.KotoBasicJdbcApp.setDynamicDataSource
import com.kotoframework.definition.asc
import com.kotoframework.definition.desc
import com.kotoframework.function.select.select
import com.kotoframework.core.condition.*
import com.kotoframework.core.future.from
import com.kotoframework.core.future.table
import test.wrapper.beans.TbUser
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import test.wrapper.DataSource.dataSource
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class SelectTest {
    init {
        KotoApp.setDynamicDataSource { dataSource }.setLog("console")
    }

    @Test
    fun selectAll(){
        val list = select<TbUser>().queryForList()
        assertEquals(501, list.size)
    }

    @Test
    fun testMultipleSelect() { // 测试复杂的查询情况
        //以下9种写法实际是等价的
        print(select(TbUser(1)).where().build().sql)
        print(TbUser(1).select().where().build().sql)
        print(from<TbUser> { it.select().by(it::id to 1) }.build().sql)
        print(from<TbUser> { it.select().where(it::id.eq(1)) }.build().sql)

        table<TbUser> {
            println(it.select().by("id" to 1).build().sql)
            println(it.select().by(TbUser::id to 1).build().sql)
        }

        table(TbUser(1)) {
            println(it.select().where().build().sql)
            println(it.select().by("id").build().sql)
            println(it.select().by(TbUser::id).build().sql)
            println(it.select().where { "id".eq() }.build().sql)
            println(it.select().where { it::id.eq() }.build().sql)
        }

        println(select<TbUser>().where { "id".eq(1) }.build().sql)
        println(select<TbUser>().where { it::id.eq(1) }.build().sql)

        val searchDto = TbUser(
            userName = "ousc",
            age = 15
        )
        val koto = select(searchDto).where().distinct().page(1, 20).suffix("order by update_time desc").build()
        assertEquals(
            "select SQL_CALC_FOUND_ROWS distinct `id`, `user_name` as `userName`, `nickname`, `password`, `sex`, `age`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `phone_number` as `phoneNumber`, `email_address` as `emailAddress`, `avatar`, DATE_FORMAT(`create_time`, '%Y-%m-%d %H:%i:%s') as `createTime`, DATE_FORMAT(`update_time`, '%Y-%m-%d %H:%i:%s') as `updateTime`, `deleted` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age order by update_time desc limit :pageIndex,:pageSize",
            koto.sql
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
            "userName" to "ousc",
            "pageIndex" to 0,
            "pageSize" to 20
        )
        assertEquals(
            expectedMap,
            koto.paramMap
        )
    }

    @Test
    fun testSelectBy() {
//        val koto = select(TbUser::updateTime, TbUser::birthday).by("id" to 1).build()
        val koto = from<TbUser> { it.select(it::userName, it::birthday).by(it::id to 1) }.build()
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
            koto.sql.trim()
        )
        assertEquals(
            expectedMap,
            koto.paramMap
        )
    }

    @Test
    fun testLimitOne() { // 测试只要第一条数据的情况
        val searchDto = TbUser(
            userName = "ousc",
            age = 15
        )
        val koto = select(searchDto).where().first().build()
        assertEquals(
            "select `id`, `user_name` as `userName`, `nickname`, `password`, `sex`, `age`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `phone_number` as `phoneNumber`, `email_address` as `emailAddress`, `avatar`, DATE_FORMAT(`create_time`, '%Y-%m-%d %H:%i:%s') as `createTime`, DATE_FORMAT(`update_time`, '%Y-%m-%d %H:%i:%s') as `updateTime`, `deleted` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
            koto.sql.trim()
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
            koto.paramMap
        )
    }


    @Test
    fun testOnlySelectPartOfFields() {
        val searchDto = TbUser(
            userName = "ousc",
            age = 15
        )
        val koto = select(searchDto, "userName", "age" to "AGE").where().first().build()

        assertEquals(
            "select `user_name` as `userName`, `age` as `AGE` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age limit 1",
            koto.sql.trim()
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
            koto.paramMap
        )
    }

    @Test
    fun testFuture() {
        val userInfo = TbUser(
            userName = "ousc",
            age = 15
        )

        table(userInfo) {
            val koto = select(it, it::id)
                .where(it::userName.eq and it::age.eq.reName("aage"))
                .distinct()
                .orderBy(it::id.desc(), it::updateTime.asc())
                .groupBy(it::sex)
                .build()

            assertEquals(
                "select distinct `id` from tb_user where ${deleted()} and `user_name` = :userName and `age` = :age group by `sex` order by `id` DESC,`update_time` ASC",
                koto.sql.trim()
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
                koto.paramMap
            )
        }
    }
}
