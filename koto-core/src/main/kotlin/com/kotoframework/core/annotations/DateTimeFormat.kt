package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:41
 */

/**
 * DateTimeFormat
 *
 * @property pattern
 * @constructor Create empty DateTimeFormat
 * @author ousc
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DateTimeFormat(val pattern: String = "")
