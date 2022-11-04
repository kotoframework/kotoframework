package test.wrapper.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table

/**
 * @author  Ousc
 * @date  2022-08-03 09:13:58
 * @description  本文件由OUSC的Koto代码生成器自动生成
 */

@Table(name = "tb_shopping_cart")
@SoftDelete(enable = true, column = "deleted")
data class TbShoppingCart(
    val id: Int? = null, // id
    val userId: Int? = null, // user_id
    val goodId: Int? = null, // good_id
    val qty: Int? = null, // qty
) : KPojo
