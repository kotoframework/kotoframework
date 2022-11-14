package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:20
 */


data class TableMeta(
    var tableName: String,
    var softDelete: TableSoftDelete = TableSoftDelete(),
)
