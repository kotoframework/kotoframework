package test.wrapper.beans

import com.kotoframework.core.annotations.*
import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/4/18 17:54
 */
@Table("tb_user")
@SoftDelete(enable = true, column = "deleted")
data class TbUser(
    val id: Int? = null,
    val userName: String? = null,
    val password: String? = null,
    val nickname: String? = null,
    @Column("phone_number") val telephone: String? = null,
    @Column("email_address") val email: String? = null,
    @Column("birthday") @DateTimeFormat("%Y-%m-%d") val birthday: String? = null,
    @Default("male") val sex: String? = null,
    val age: Int? = null,
    val avatar: String? = null
) : KPojo
