package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.function.remove.remove
import test.wrapper.beans.TbUser
import com.kotoframework.utils.deleted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/4/18 17:55
 */
class RemoveTest {
    init {
        KotoApp.setLog("console")
    }

    @Test
    fun testBasicRemove() { // 测试基础删除功能
        val (sql, paramMap) = remove(TbUser()).byId(1)
        assertEquals(
            mapOf(
                "id" to 1,
                "birthday" to null,
                "password" to null,
                "sex" to null,
                "nickname" to null,
                "telephone" to null,
                "avatar" to null,
                "userName" to null,
                "age" to null,
                "email" to null
            ), paramMap
        )

        assertEquals(
            "delete from tb_user where id = :id", sql
        )
    }

    @Test
    fun testDeleteByIds() { //测试通过id列表删除
        val (sql, paramMap) = remove(TbUser()).byIds(listOf(1, 2, 3, 4))
        assertEquals(
            mapOf(
                "ids" to listOf(1, 2, 3, 4),
                "birthday" to null,
                "password" to null,
                "sex" to null,
                "nickname" to null,
                "telephone" to null,
                "avatar" to null,
                "id" to null,
                "userName" to null,
                "age" to null,
                "email" to null
            ),
            paramMap
        )
        assertEquals(
            "delete from tb_user where id in (:ids)", sql
        )
    }

    @Test
    fun testDeleteByMultiplePairs() { //测试通过多个键值对删除
        val (sql, paramMap) = remove(TbUser()).by(
            "userName" to "ousc",
            "sex" to "男",
            "city" to "hangzhou"
        )
        assertEquals(
            mapOf(
                "userName" to "ousc",
                "sex" to "男",
                "city" to "hangzhou",
                "birthday" to null,
                "password" to null,
                "nickname" to null,
                "telephone" to null,
                "avatar" to null,
                "id" to null,
                "age" to null,
                "email" to null
            ), paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and `user_name` = :userName and `sex` = :sex and `city` = :city",
            sql
        )
    }

    @Test
    fun testDeleteByDto() { // 测试通过Dto删除
        val user = TbUser(
            userName = "XiaoQi"
        )
        val (prepared) = remove(user).where()
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
            ), prepared.paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and birthday is null and password is null and sex is null and nickname is null and telephone is null and avatar is null and id is null and `user_name` = :userName and age is null and email is null",
            prepared.sql
        )
    }

    @Test
    fun testSoftDelete() { // 测试逻辑删除
        val (sql, paramMap) = remove(TbUser()).soft().byId(1)
        assertEquals(
            mapOf(
                "id" to 1,
                "deleteTime" to paramMap["deleteTime"],
                "birthday" to null,
                "password" to null,
                "sex" to null,
                "nickname" to null,
                "telephone" to null,
                "avatar" to null,
                "userName" to null,
                "age" to null,
                "email" to null
            ), paramMap
        )
        assertEquals(
            "update tb_user set ${deleted(1)}, `delete_time` = :deleteTime where id = :id", sql
        )
    }

    @Test
    fun removeWhereWithLambda() {
        val user = TbUser(
            userName = "XiaoQi"
        )
        val (prepared) = remove(user).where {
            it.userName == "XiaoLiu"
        }

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
            ), prepared.paramMap
        )
        assertEquals(
            "delete from tb_user where ${deleted()} and `user_name` = :userName",
            prepared.sql.trim()
        )
    }
}
