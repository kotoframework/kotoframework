package com.kotoframework.definition

import com.kotoframework.SortType
import com.kotoframework.beans.TableColumn
import com.kotoframework.core.annotations.Column
import com.kotoframework.core.condition.Criteria
import com.kotoframework.core.condition.eq
import com.kotoframework.utils.Extension.humpToLine
import com.kotoframework.utils.Extension.lineToHump
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Created by ousc on 2022/8/2 16:50
 */
class ColumnMeta(
    var columnName: String, // 通过注解或者驼峰转下划线获取的数据表列名
    var propertyName: String, // 通过反射获取的属性名称或者columnName的驼峰形式
    var aliasName: String, // 通过pair获取的别名名称，默认为columnName的驼峰形式
    var type: KClass<*>, // 属性类型
    var value: Any // 属性值
){
    operator fun component1() = columnName
}

internal val KCallable<*>.column: String?
    get() {
        return findAnnotation<Column>()?.name
    }

internal val Field.columnName: String
    get() = when (this) {
        is Pair<*, *> -> (first!!.columnName)
        is KCallable<*> -> column ?: name.humpToLine()
        is TableColumn -> name
        is ColumnMeta -> columnName
        is String -> if (lowercase().contains(" as ") || this.contains("(")) {
            this
        } else {
            humpToLine()
        }

        else -> throw IllegalArgumentException("Field $this is not a valid field")
    }

internal val Field.propertyName: String
    get() = when (this) {
        is Pair<*, *> -> first!!.propertyName
        is KCallable<*> -> name
        is String -> this
        is TableColumn -> name.lineToHump()
        is ColumnMeta -> propertyName
        else -> throw IllegalArgumentException("Field $this is not a valid field")
    }

internal val Field.aliasName: String
    get() = when (this) {
        is Pair<*, *> -> second!!.aliasName
        is KCallable<*> -> name
        is String -> this
        is TableColumn -> name.lineToHump()
        is ColumnMeta -> aliasName
        else -> this.toString()
    }

internal val Field.selectBy: Criteria
    get() = this.propertyName.eq(if (this is Pair<*, *>) second else null)

internal val Field.direction: String
    get() =
        if (this is Pair<*, *>) {
            when (second) {
                is SortType -> (second as SortType).name
                else -> throw IllegalArgumentException("Invalid type: The direction must be SortType(ASC/DESC)!")
            }
        } else {
            ""
        }

fun Field.desc(): Pair<Field, SortType> = this to SortType.DESC
fun Field.asc(): Pair<Field, SortType> = this to SortType.ASC


internal fun Field.toColumn(): ColumnMeta {
    return ColumnMeta(
        columnName,
        propertyName,
        aliasName,
        this::class,
        this
    )
}
