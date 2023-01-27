package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Create Time
 *
 * @property enable
 * @property column
 * @constructor Create empty Create Time
 * @author ousc
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateTime(val enable: Boolean = true, val column: String = "create_time")
