package com.kotoframework.interfaces

/**
 * Created by ousc on 2022/4/18 11:01
 */
interface KotoDataSet {
    val sql: String
    val paramMap: Map<String, Any?>
}
