package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:19
 */

data class TableObject(
    val fields: List<TableColumn>,
    val meta: TableMeta
)
