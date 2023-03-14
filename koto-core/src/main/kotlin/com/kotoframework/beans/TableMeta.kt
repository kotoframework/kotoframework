package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:20
 */

/**
 * Table meta
 *
 * @property tableName
 * @property softDelete
 * @property createTime
 * @property updateTime
 * @property deleteTime
 * @property primaryKey
 * @constructor Create empty Table meta
 * @author ousc
 */
data class TableMeta(
    var tableName: String,
    var softDelete: Config,
    var createTime: Config,
    var updateTime: Config,
    var deleteTime: Config,
    var primaryKey: TableColumn = TableColumn("id", "int"),
)
