package com.kotoframework.plugins.test

import com.kotoframework.core.annotations.Column
import com.kotoframework.interfaces.KPojo
import com.kotoframework.orm.query.from

data class User(
    val id: Int? = null,
    @Column("user_name")
    val name: String? = null,
    val age: Int? = null,
) : KPojo

fun main() {
    from<User>().select {
        it.id + it.name + "max(id)" + "count(id)" + "age" + "update_time as updateTime" + "create_time as createTime"
    }
}