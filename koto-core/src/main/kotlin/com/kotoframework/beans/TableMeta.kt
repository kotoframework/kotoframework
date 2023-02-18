package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:20
 */

data class TableMeta(
    var tableName: String,
    var softDelete: Config,
    var createTime: Config,
    var updateTime: Config,
    var deleteTime: Config,
    var primaryKey: TableColumn = TableColumn("id", "int"),
)
