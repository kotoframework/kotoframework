package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/12 14:18
 */

data class TableSoftDelete(
    var enabled: Boolean = false,
    var column: String = "deleted",
)

