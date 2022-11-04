package test.wrapper.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table

/**
 * @author  Ousc
 * @date  2022-08-03 09:13:52
 * @description  本文件由OUSC的Koto代码生成器自动生成
 */

@Table(name = "tb_good_category")
@SoftDelete(enable = true, column = "deleted")
data class TbGoodCategory (
    val id: Int? = null, // id
    val name: String? = null, // name
): KPojo
