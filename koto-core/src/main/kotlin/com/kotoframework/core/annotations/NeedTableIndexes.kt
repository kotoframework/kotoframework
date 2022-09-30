package com.kotoframework.core.annotations

/**
 * Created by ousc on 2022/7/20 15:40
 */

/**
 * Need table indexes
 *
 * @constructor Create empty Need table indexes
 * @author ousc
 */
@RequiresOptIn(level = RequiresOptIn.Level.ERROR, message = "Please add a comment to this function describing the specific index type used.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class NeedTableIndexes
