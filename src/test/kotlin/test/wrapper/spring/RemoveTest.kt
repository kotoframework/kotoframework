package test.wrapper.spring

import test.wrapper.DataSource.namedJdbc
import com.kotoframework.KotoApp
import com.kotoframework.KotoSpringApp.setDynamicDataSource
import com.kotoframework.function.remove.remove
import com.kotoframework.core.condition.eq
import test.wrapper.beans.TbUser
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 17:55
 */
class RemoveTest {
    init {
        KotoApp.setDynamicDataSource { namedJdbc }.setLog("console")
    }

    @Test
    fun testBasicRemove() { // 测试基础删除功能
        val koto = remove("tb_user").byId(1)
        assertEquals(
            mapOf("id" to 1, "updateTime" to koto.paramMap["updateTime"]), koto.paramMap
        )
        assertEquals(
            "delete from tb_user where id = :id", koto.sql
        )
    }

    @Test
    fun testDeleteByIds() { //测试通过id列表删除
        val koto = remove("tb_user").byIds(listOf(1, 2, 3, 4))
        assertEquals(
            mapOf("ids" to listOf(1, 2, 3, 4), "updateTime" to koto.paramMap["updateTime"]), koto.paramMap
        )
        assertEquals(
            "delete from tb_user where id in (:ids)", koto.sql
        )
    }

    @Test
    fun testDeleteByMultiplePairs() { //测试通过多个键值对删除
        val koto = remove("tb_user").by(
            "userName" to "ousc",
            "sex" to "男",
            "city" to "hangzhou"
        )
        assertEquals(
            mapOf(
                "userName" to "ousc",
                "sex" to "男",
                "city" to "hangzhou",
                "updateTime" to koto.paramMap["updateTime"]
            ), koto.paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and `user_name` = :userName and `sex` = :sex and `city` = :city",
            koto.sql.trim()
        )
    }

    @Test
    fun testDeleteByDto() { // 测试通过Dto删除
        val deleteDto = TbUser(
            userName = "XiaoQi"
        )
        val koto = remove(deleteDto).where().build()
        assertEquals(
            mapOf(
                "age" to null,
                "avatar" to null,
                "birthday" to null,
                "email" to null,
                "id" to null,
                "nickname" to null,
                "password" to null,
                "sex" to null,
                "telephone" to null,
                "userName" to "XiaoQi",
                "updateTime" to koto.paramMap["updateTime"]
            ), koto.paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and birthday is null and password is null and sex is null and nickname is null and telephone is null and avatar is null and id is null and `user_name` = :userName and age is null and email is null",
            koto.sql.trim()
        )
    }

    @Test
    fun testSoftDelete() { // 测试逻辑删除
        val koto = remove("tb_user").soft().byId(1)
        assertEquals(
            mapOf("id" to 1, "updateTime" to koto.paramMap["updateTime"]), koto.paramMap
        )
        assertEquals(
            "update tb_user set ${deleted(1)}, update_time = :updateTime where id = :id", koto.sql
        )
    }

    @Test
    fun removeWhereWithLambda() {
        val deleteDto = TbUser(
            userName = "XiaoQi"
        )
        val koto = remove(deleteDto).where {
            it::userName.eq("XiaoLiu")
        }.build()

        assertEquals(
            mapOf(
                "age" to null,
                "avatar" to null,
                "birthday" to null,
                "email" to null,
                "id" to null,
                "nickname" to null,
                "password" to null,
                "sex" to null,
                "telephone" to null,
                "userName" to "XiaoLiu",
                "updateTime" to koto.paramMap["updateTime"]
            ), koto.paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and `user_name` = :userName",
            koto.sql.trim()
        )
    }
}
