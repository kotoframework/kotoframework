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
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DateTimeFormat(val pattern: String = "")
