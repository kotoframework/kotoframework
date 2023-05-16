package com.kotoframework.definition

import com.kotoframework.beans.TableColumn
import com.kotoframework.core.annotations.Column
import com.kotoframework.core.condition.Criteria
import com.kotoframework.enums.SortType
import com.kotoframework.utils.humpToLine
import com.kotoframework.utils.lineToHump
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
) {
    operator fun component1() = columnName
}

internal val KCallable<*>.column: String?
    get() {
        return findAnnotation<Column>()?.name
    }

fun getFieldColumnName(field: Field): String {
    return when (field) {
        is Pair<*, *> -> getFieldColumnName(field.first!!)
        is KCallable<*> -> field.column ?: field.name.humpToLine()
        is TableColumn -> field.name
        is ColumnMeta -> field.columnName
        is String -> if (field.lowercase().contains(" as ") || field.contains("(")) {
            field
        } else {
            field.humpToLine()
        }

        else -> throw IllegalArgumentException("Field $field is not a valid field")
    }
}

internal val Field.columnName: String
    get() = getFieldColumnName(this)

fun getFieldPropertyName(field: Field): String {
    return when (field) {
        is Pair<*, *> -> getFieldPropertyName(field.first!!)
        is KCallable<*> -> field.name
        is String -> field
        is TableColumn -> field.name.lineToHump()
        is ColumnMeta -> field.propertyName
        else -> throw IllegalArgumentException("Field $field is not a valid field")
    }
}

internal val Field.propertyName: String
    get() = getFieldPropertyName(this)

fun getFieldAliasName(field: Field): String {
    return when (field) {
        is Pair<*, *> -> getFieldAliasName(field.second!!)
        is KCallable<*> -> field.name
        is String -> field
        is TableColumn -> field.name.lineToHump()
        is ColumnMeta -> field.aliasName
        else -> field.toString()
    }
}

internal val Field.aliasName: String
    get() = getFieldAliasName(this)

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


internal fun Field.toColumn(): ColumnMeta {
    return ColumnMeta(
            columnName,
            propertyName,
            aliasName,
            this::class,
            this
    )
}
