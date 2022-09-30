package com.kotoframework.beans

import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.Column
import com.kotoframework.core.annotations.Table

/**
 * Created by ousc on 2022/4/18 17:54
 */
@Table("user_info")
data class UserInfoDto(
    val id: Int? = null,
    val userName: String? = null,
    val telephone: String? = null,
    val nickName: String? = null,
    @Column("email") val emailAddress: String? = null,
    val active: Boolean? = null,
    val birthday: String? = null,
    val sex: String? = null,
    val habit: String? = null,
    val age: Int? = null,
    val roles: List<String>? = null
) : KPojo
