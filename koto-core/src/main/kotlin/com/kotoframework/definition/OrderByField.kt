package com.kotoframework.definition

class OrderByField : SelectField() {
    fun Field?.desc() = this
    fun Field?.asc() = this
}

val orderByField get() = OrderByField()