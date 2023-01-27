package com.kotoframework.beans

import com.kotoframework.KotoApp

/**
 * Created by sundaiyue on 2022/11/12 14:20
 */


data class TableMeta(
    var tableName: String,
    var softDelete: KotoApp.Config,
    var createTime: KotoApp.Config,
    var updateTime: KotoApp.Config,
    var deleteTime: KotoApp.Config,
)
