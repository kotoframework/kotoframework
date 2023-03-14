package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * ColumnMeta
 *
 * @property name
 * @constructor Create empty ColumnMeta
 * @author ousc
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(val name: String, val type: String = "", val comment: String = "")
