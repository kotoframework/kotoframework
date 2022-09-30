package com.kotoframework.tests.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.Column
import com.kotoframework.core.annotations.Default
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table

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
    val birthday: String? = null,
    @Default("male") val sex: String? = null,
    val age: Int? = null,
    val avatar: String? = null
) : KPojo
