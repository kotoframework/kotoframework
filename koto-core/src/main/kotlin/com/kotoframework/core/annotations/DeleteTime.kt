package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Delete Time
 *
 * @property enable
 * @property column
 * @constructor Create empty Delete Time
 * @author ousc
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DeleteTime(val enable: Boolean = true, val column: String = "delete_time")
