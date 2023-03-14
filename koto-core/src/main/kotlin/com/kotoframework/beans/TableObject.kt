package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:19
 */

/**
 * Table object
 *
 * @property fields
 * @property meta
 * @constructor Create empty Table object
 * @author ousc
 */
data class TableObject(
    val fields: List<TableColumn>,
    val meta: TableMeta
)
