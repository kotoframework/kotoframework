package test.wrapper.basic

import com.kotoframework.KotoApp
import com.kotoframework.KotoBasicJdbcApp.setDynamicDataSource
import com.kotoframework.Right
import test.wrapper.beans.TbUser
import com.kotoframework.function.update.update
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.condition.and
import com.kotoframework.core.condition.eq
import com.kotoframework.core.condition.like
import com.kotoframework.core.condition.matchRight
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import test.wrapper.DataSource.dataSource
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 21:46
 */
class UpdateTest : KPojo {
    init {
        KotoApp.setDynamicDataSource { dataSource }.setLog("console")
    }

    private val user = TbUser(
        id = 9938,
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
    fun updateById() { // 测试通过id更新
        val koto = update(user).byId()
        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "id" to 9938,
        )
        assertEquals(
            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `id` = :id@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where id = :id",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun updateByIds() { // 测试通过id列表更新
        val koto = update(user).byIds(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "ids" to listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            "updateTime" to koto.paramMap["updateTime"]!!,
        )
        assertEquals(
            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `id` = :id@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where ids in (:ids)",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun updateByPairs() { // 测试通过键值对更新
        val koto = update(user).by(
            "userName" to "ousc",
            "birthday" to "2020-04-18",
            "age" to null
        )
        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "age" to null,
            "avatar" to "http://cdn.leinbo.com/avatar.png",
            "birthday" to "2020-04-18",
            "email" to "mail@leinbo.com",
            "id" to 9938,
            "nickname" to "Leinbo",
            "password" to "Leinbo",
            "sex" to "male",
            "telephone" to "13888888888",
            "userName" to "ousc"
        )
        assertEquals(
            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `id` = :id@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `user_name` = :userName and `birthday` = :birthday and `age` is null",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun updateByFieldNames() { // 测试通过字段列表更新
        val koto = update(user, "userName").by(
            "userName",
            "birthday",
            "age"
        )
        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "age" to 18,
            "avatar" to "http://cdn.leinbo.com/avatar.png",
            "birthday" to "2020-08-02",
            "email" to "mail@leinbo.com",
            "id" to 9938,
            "nickname" to "Leinbo",
            "password" to "Leinbo",
            "sex" to "male",
            "telephone" to "13888888888",
            "userName" to "Leinbo"
        )
        assertEquals(
            "update tb_user set `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `user_name` = :userName and `birthday` = :birthday and `age` = :age",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun updateByConditions() { // 测试根据Where条件更新
        val koto = update(user).where {
            it::userName.eq() and it::birthday.like().matchRight()
        }.build()

        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "age" to 18,
            "avatar" to "http://cdn.leinbo.com/avatar.png",
            "birthday" to "2020-08-02%",
            "email" to "mail@leinbo.com",
            "id" to 9938,
            "nickname" to "Leinbo",
            "password" to "Leinbo",
            "sex" to "male",
            "telephone" to "13888888888",
            "userName" to "Leinbo"
        )
        assertEquals(
            "update tb_user set `age` = :age@New, `avatar` = :avatar@New, `birthday` = :birthday@New, `email_address` = :email@New, `id` = :id@New, `nickname` = :nickname@New, `password` = :password@New, `sex` = :sex@New, `phone_number` = :telephone@New, `user_name` = :userName@New, `update_time` = :updateTime@New where ${deleted()} and `user_name` = :userName and `birthday` like :birthday",
            koto.sql.trim()
        )

        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }

    @Test
    fun testUpdatePartOfFields() { // 测试更新部分字段
        val koto = update(user, "userName").byId()
        val expectedParamMap = mapOf(
            "age@New" to 18,
            "avatar@New" to "http://cdn.leinbo.com/avatar.png",
            "birthday@New" to "2020-08-02",
            "email@New" to "mail@leinbo.com",
            "id@New" to 9938,
            "nickname@New" to "Leinbo",
            "password@New" to "Leinbo",
            "sex@New" to "male",
            "telephone@New" to "13888888888",
            "userName@New" to "Leinbo",
            "updateTime@New" to koto.paramMap["updateTime@New"]!!,
            "id" to 9938,
        )

        assertEquals(
            "update tb_user set `user_name` = :userName@New, `update_time` = :updateTime@New where id = :id",
            koto.sql.trim()
        )
        assertEquals(
            expectedParamMap, koto.paramMap
        )
    }
}
