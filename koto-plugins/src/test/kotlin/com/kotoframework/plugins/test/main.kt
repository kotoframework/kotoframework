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
    val a = from<User>()
        .select { it.id + it.name + it.age }
        .where {
            it.id == 1 && it.age < 10 && (it.name like "%zhang%" || it.name == "lisi")
        }.prepared

    println(a.sql)
}