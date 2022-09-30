package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Soft delete
 *
 * @property enable
 * @property column
 * @constructor Create empty Soft delete
 * @author ousc
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SoftDelete(val enable: Boolean = true, val column: String = "deleted")
