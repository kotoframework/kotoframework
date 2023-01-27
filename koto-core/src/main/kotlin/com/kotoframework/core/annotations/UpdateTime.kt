package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Update Time
 *
 * @property enable
 * @property column
 * @constructor Create empty Update Time
 * @author ousc
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class UpdateTime(val enable: Boolean = true, val column: String = "update_time")
