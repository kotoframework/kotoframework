package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.beans.KResultSet.Companion.convertCountSql
import com.kotoframework.interfaces.orm
import com.kotoframework.orm.query.from
import com.kotoframework.orm.query.join
import com.kotoframework.orm.query.left
import com.kotoframework.utils.deleted
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import test.wrapper.beans.TbShoppingCart
import test.wrapper.beans.TbUser

class QueryChainTest {
    init {
        KotoApp.setLog("console")
        KotoApp.setSoftDelete(true, "deleted")
    }

    @Test
    fun testSimpleQuery() {
        val (sql, paramMap) = from<TbUser>().select { it.userName }.where { it.id == 1 }.prepared

        assertEquals(
            "select `user_name` as `userName` from tb_user where `id` = :id and ${deleted()}", sql.trim()
        )
        assertEquals(mapOf("id" to 1), paramMap)
    }

    @Test
    fun testQuery() {
        // 1. select,where
        val case1 = from<TbUser>().select { it.userName }.where { it.id == 1 }.prepared

        assertEquals(
            "select `user_name` as `userName` from tb_user where `id` = :id and ${deleted()}", case1.sql.trim()
        )
        assertEquals(mapOf("id" to 1), case1.paramMap)

        // 2. select,distinct,order by,page
        val case2 =
            from<TbUser>()
                .select {
                    it.userName + it.birthday + it.id
                }
                .where { it.id == 1 }
                .page(1, 10)
                .orderBy { it.updateTime.desc() }
                .distinct()
                .prepared

        assertEquals(
            "select distinct `user_name` as `userName`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `id` from tb_user where `id` = :id and ${deleted()} order by `update_time` DESC limit 10 offset 0",
            case2.sql.trim()
        )

        assertEquals(
            "select count(*) from (select 1 from tb_user where `id` = :id and ${deleted()} order by `update_time` DESC ) as t",
            convertCountSql(case2.sql.trim())
        )

        assertEquals(mapOf("id" to 1), case2.paramMap)

        // 3. select all, auto where, group by, limit
        val case3 = from(TbUser(id = 1)).select().where().groupBy { it.age }.limit(100).prepared
        assertEquals(
            "select `age`, `avatar`, DATE_FORMAT(`birthday`, '%Y-%m-%d') as `birthday`, `email_address` as `email`, `id`, `nickname`, `password`, `sex`, `phone_number` as `telephone`, `user_name` as `userName` from tb_user where `id` = :id and ${deleted()} group by `age` limit 100",
            case3.sql.trim()
        )
        assertEquals(mapOf("id" to 1), case3.paramMap)

        // 4. select some, auto where, group by, limit
        val case4 =
            TbUser().orm()
                .left().join(TbShoppingCart()).on { user, cart -> user.id == cart.id && user.age > 3 }
                .select { user, cart ->
                    user.id + user.age + cart.id
                }
                .where { user, cart ->
                    user.id == 1 &&
                            user.age >= 20 &&
                            user.email like "%@qq.com" &&
                            user.telephone notLike "159%" &&
                            (user.userName in listOf("a", "b", "c") || user.id !in listOf(1, 2, 3)) &&
                            user.nickname.notNull &&
                            user.age between 1..2 &&
                            user.age notBetween 1..2
                }
                .groupBy { user, _ -> user.age }
                .page(1, 100)
                .prepared

        assertEquals(
            "select `id`, `age` from tb_user where `id` = :id and ${deleted()} group by `age` limit 100",
            case4.sql.trim()
        )
        assertEquals(mapOf("id" to 1), case4.paramMap)

        val case5 = from<TbUser>(TbUser(id = 1)) {
            select { it.id + it.age }
            where { it.id == 1 && it.age >= 2 }
            groupBy { it.age }
            orderBy { it.age.desc() }
            limit(100)
            left().join(TbShoppingCart()) { user, cart ->
                on { _, _ -> user.id == cart.id && user.age > 3 }
            }
        }

        assertEquals(
            "select `id`, `age` from tb_user where `id` = :id and ${deleted()} group by `age` limit 100",
            case5.sql.trim()
        )

        assertEquals(mapOf("id" to 1), case5.paramMap)
    }
}