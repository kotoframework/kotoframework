package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.definition.desc
import com.kotoframework.function.associate.associate
import com.kotoframework.core.condition.and
import com.kotoframework.core.condition.eq
import com.kotoframework.core.future.from
import test.wrapper.beans.TbGoodCategory
import test.wrapper.beans.TbGood
import test.wrapper.beans.TbShoppingCart
import test.wrapper.beans.TbUser
import com.kotoframework.utils.Common.deleted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/5/12 09:37
 */
class AssociateTest {
    init {
        KotoApp
            .setLog("console", true)
            .setSoftDelete(true, "deleted")
    }

    @Test
    fun testAssociate() {
        val (prepared) =
            from<TbUser, TbGoodCategory, TbGood, TbShoppingCart> { user, goodCategory, good, shoppingCart ->
                associate(user, goodCategory, good, shoppingCart).on(
                    user::id.eq(shoppingCart::userId) and
                            good::categoryId.eq(goodCategory::id) and
                            good::id.eq(shoppingCart::goodId)
                ).select(
                    user,
                    user::userName,
                    good::name,
                    good::updateTime,
                    goodCategory::name,
                    goodCategory::updateTime,
                    shoppingCart::qty,
                    shoppingCart::updateTime to "ut"
                ).where(
                    user::userName.eq("ousc")
                ).orderBy(user::age.desc()).groupBy(user::age).page(1, 10)

            }
        assertEquals(
            "select `tbUser`.`age` as `age` , `tbUser`.`avatar` as `avatar` , DATE_FORMAT(`tbUser`.`birthday`, '%Y-%m-%d %H:%i:%s') as `birthday` , `tbUser`.`email_address` as `emailAddress` , `tbUser`.`id` as `id` , `tbUser`.`nickname` as `nickname` , `tbUser`.`password` as `password` , `tbUser`.`sex` as `sex` , `tbUser`.`phone_number` as `phoneNumber` , `tbUser`.`user_name` as `userName` , `tbGood`.`name` as `name` , `tbGood`.`update_time` as `updateTime` , `tbGoodCategory`.`name` as `name@` , `tbGoodCategory`.`update_time` as `updateTime@` , `tbShoppingCart`.`qty` as `qty` , `tbShoppingCart`.`update_time` as `ut` from tb_user as tbUser left join tb_good_category as tbGoodCategory on tbGood.category_id = tbGoodCategory.id left join tb_good as tbGood on left join tb_shopping_cart as tbShoppingCart on tbUser.id = tbShoppingCart.user_id and tbGood.id = tbShoppingCart.good_id where `tbUser`.`deleted` = 0 and `tbGoodCategory`.`deleted` = 0 and `tbGood`.`deleted` = 0 and `tbShoppingCart`.`deleted` = 0 and `tbUser`.`user_name` = :userName group by `tbUser`.`age` order by `tbUser`.`age` DESC limit 10 offset 0",
            prepared.sql
        )
        assertEquals(
            mapOf(
                "userName" to "ousc",
                "age" to null,
                "avatar" to null,
                "birthday" to null,
                "email" to null,
                "id" to null,
                "nickname" to null,
                "password" to null,
                "sex" to null,
                "telephone" to null,
                "name" to null,
                "categoryId" to null,
                "description" to null,
                "keywords" to null,
                "picture" to null,
                "price" to null,
                "score" to null,
                "goodId" to null,
                "qty" to null,
                "userId" to null
            ), prepared.paramMap

        )

    }
}
