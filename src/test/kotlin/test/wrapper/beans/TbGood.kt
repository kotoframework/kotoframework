package test.wrapper.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table

/**
 * @author  Ousc
 * @date  2022-08-03 09:13:30
 * @description  本文件由OUSC的Koto代码生成器自动生成
 */

@Table(name = "tb_good")
@SoftDelete(enable = true, column = "deleted")
data class TbGood (
    val id: Int? = null, // id
    val categoryId: Int? = null, // category_id
    val name: String? = null, // name
    val keywords: String? = null, // keywords
    val picture: String? = null, // picture
    val description: String? = null, // description
    val price: String? = null, // price
    val score: String? = null, // score
): KPojo
