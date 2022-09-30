package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Default
 *
 * @property value
 * @constructor Create empty Default
 * @author ousc
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Default(val value: String)
