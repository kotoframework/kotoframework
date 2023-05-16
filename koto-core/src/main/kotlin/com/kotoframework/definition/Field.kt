package com.kotoframework.definition

import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.enums.SortType
import kotlin.reflect.KCallable

/**
 * Created by ousc on 2022/5/30 16:10
 */

typealias Field = Any

class NoValueField {
    /**
     * if the criteria is null, ignore it
     */
    val ignore = NoValueStrategy.Ignore

    /**
     * if the criteria is null, always return "false"
     */
    val alwaysFalse = NoValueStrategy.False

    /**
     * if the criteria is null, always return "true"
     */
    val alwaysTrue = NoValueStrategy.True

    /**
     * if the criteria is null, return "field is null"
     */
    val isNull = NoValueStrategy.IsNull

    /**
     * if the criteria is null, return "field is not null"
     */
    val notNull = NoValueStrategy.NotNull

    /**
     * if the criteria is null, return "field = value"
     */
    val smart = NoValueStrategy.Smart
}


val noValueField = NoValueField()
