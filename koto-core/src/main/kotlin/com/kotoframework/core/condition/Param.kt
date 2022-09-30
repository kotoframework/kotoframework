package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.definition.Field
import com.kotoframework.definition.column
import com.kotoframework.definition.columnName
import com.kotoframework.utils.Common.lineToHump
import com.kotoframework.utils.Common.tableAlias
import com.kotoframework.utils.Common.tableName
import com.kotoframework.utils.Common.yes
import kotlin.reflect.*
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod


/**
 * Created by ousc on 2022/4/18 11:16
 *
 * */

fun String.eq(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).eq(value, reName, iif, humpToLine)
}

val String.eq: BaseCondition? get() = eq()

fun String.notEq(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).notEq(value, reName, iif, humpToLine)
}

val String.notEq: BaseCondition? get() = notEq()

fun String.like(
    expression: String? = null,
    reName: String? = null,
    pos: LikePosition = Both,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): LikeCondition? {
    return Condition(this).like(expression, reName, pos, iif, humpToLine)
}

val String.like: LikeCondition? get() = like()

fun String.notLike(
    expression: String? = null,
    reName: String? = null,
    pos: LikePosition = Both,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): LikeCondition? {
    return Condition(this).notLike(expression, reName, pos, iif, humpToLine)
}

val String.notLike: BaseCondition? get() = notLike()

fun String.gt(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).gt(value, reName, iif, humpToLine)
}

fun String.ge(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).ge(value, reName, iif, humpToLine)
}

fun String.lt(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).lt(value, reName, iif, humpToLine)
}

fun String.le(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).le(value, reName, iif, humpToLine)
}

fun String.between(
    value: ClosedRange<*>,
    reName: String? = null,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).between(value, reName, iif, humpToLine)
}

fun String.notBetween(
    value: ClosedRange<*>?,
    reName: String? = null,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).notBetween(value, reName, iif, humpToLine)
}

fun String.isIn(
    value: Collection<*>?, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).isIn(value, reName, iif, humpToLine)
}

fun String.notIn(
    value: Collection<*>, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).notIn(value, reName, iif, humpToLine)
}

fun String.isNull(
    iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).isNull(iif, humpToLine)
}

val String.isNull: BaseCondition? get() = isNull()
fun String.notNull(
    iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(this).notNull(iif, humpToLine)
}

val String.notNull: BaseCondition? get() = notNull()

fun String.declareSql(): BaseCondition {
    return BaseCondition(
        type = SQL, reName = this, sql = this
    )
}

// String Extension Functions End

// Field Extension Functions Start
infix fun BaseCondition?.and(condition: BaseCondition?): BaseCondition {
    val collections = mutableListOf<BaseCondition?>()
    listOf(this, condition).forEach {
        if (it != null) {
            if (it.type == AND) {
                collections.addAll(it.collections)
            } else {
                collections.add(it)
            }
        }
    }
    return BaseCondition(
        type = ConditionType.AND, collections = collections
    )
}

infix fun BaseCondition?.and(condition: String?): BaseCondition? {
    if (condition == null) return this
    return this and condition.declareSql()
}

infix fun String?.and(condition: BaseCondition?): BaseCondition? {
    if (this == null) return condition
    return declareSql() and condition
}

infix fun String?.and(condition: String?): BaseCondition? {
    if (this == null) return condition?.declareSql()
    if (condition == null) return declareSql()
    return declareSql() and condition.declareSql()
}

infix fun BaseCondition?.or(condition: BaseCondition?): BaseCondition? {
    if (this == null && condition == null) return null
    val collections = mutableListOf<BaseCondition?>()
    listOfNotNull(this, condition).forEach {
        if (it.type == OR) {
            collections.addAll(it.collections)
        } else {
            collections.add(it)
        }
    }
    return BaseCondition(
        type = ConditionType.OR, collections = collections
    )
}

infix fun BaseCondition?.or(condition: String?): BaseCondition? {
    if (condition == null) return this
    return this or condition.declareSql()
}

infix fun String?.or(condition: BaseCondition?): BaseCondition? {
    if (this == null) return condition
    return declareSql() or condition
}

infix fun String?.or(condition: String?): BaseCondition? {
    if (this == null) return condition?.declareSql()
    if (condition == null) return declareSql()
    return declareSql() or condition.declareSql()
}

fun addFields(vararg fields: Field): List<Field> {
    return fields.toList()
}

val KCallable<*>.receiver: KClass<*>
    get() {
        return when (this) {
            is KFunction<*> -> javaMethod!!.declaringClass.kotlin
            is KProperty<*> -> javaField!!.declaringClass.kotlin
            else -> throw IllegalArgumentException("$this is not a function or property")
        }
    }


fun KCallable<*>.eq(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    if (value is KCallable<*>) {
        return BaseCondition(
            type = SQL,
            sql = " ${receiver.tableAlias}.${columnName} = ${value.receiver.tableAlias}.${value.columnName} ",
            tableName = value.receiver.tableName
        )
    }
    return Condition(column?.lineToHump() ?: name).eq(value, reName, iif, humpToLine, false, receiver.tableName)
}

val KCallable<*>.eq get() = eq()

fun KCallable<*>.notEq(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).notEq(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.notEq get() = notEq()

fun KCallable<*>.like(
    expression: String? = null,
    reName: String? = null,
    pos: LikePosition = Both,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): LikeCondition? {
    return Condition(column?.lineToHump() ?: name).like(
        expression,
        reName,
        pos,
        iif,
        humpToLine,
        false,
        receiver.tableName
    )
}

val KCallable<*>.like get() = like()

fun KCallable<*>.notLike(
    expression: String? = null,
    reName: String? = null,
    pos: LikePosition = Both,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): LikeCondition? {
    return Condition(column?.lineToHump() ?: name).notLike(expression, reName, pos, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.notLike get() = notLike()

fun KCallable<*>.gt(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).gt(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.gt get() = gt()

fun KCallable<*>.ge(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).ge(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.ge get() = ge()

fun KCallable<*>.lt(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).lt(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.lt get() = lt()

fun KCallable<*>.le(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).le(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.le get() = le()

fun KCallable<*>.between(
    value: ClosedRange<*>?,
    reName: String? = null,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).between(value, reName, iif, humpToLine, false, receiver.tableName)
}

fun KCallable<*>.notBetween(
    value: ClosedRange<*>?,
    reName: String? = null,
    iif: Boolean? = null,
    humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).notBetween(value, reName, iif, humpToLine, receiver.tableName)
}

fun KCallable<*>.before(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).lt(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.before get() = before()

fun KCallable<*>.after(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).gt(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.after get() = after()


fun KCallable<*>.notBefore(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).ge(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.notBefore get() = notBefore()

fun KCallable<*>.notAfter(
    value: Any? = null, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).le(value, reName, iif, humpToLine, receiver.tableName)
}

val KCallable<*>.notAfter get() = notAfter()

fun KCallable<*>.isIn(
    value: Collection<*>?, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).isIn(value, reName, iif, humpToLine, false, receiver.tableName)
}

fun KCallable<*>.notIn(
    value: Collection<*>?, reName: String? = null, iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).notIn(value, reName, iif, humpToLine, receiver.tableName)
}

fun KCallable<*>.isNull(
    iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).isNull(iif, humpToLine, false, receiver.tableName)
}

val KCallable<*>.isNull get() = isNull()

fun KCallable<*>.notNull(
    iif: Boolean? = null, humpToLine: Boolean = true
): BaseCondition? {
    return Condition(column?.lineToHump() ?: name).notNull(iif, humpToLine, this.receiver.tableName)
}

val KCallable<*>.notNull get() = notNull()

fun List<BaseCondition?>.arbitrary(): BaseCondition {
    return BaseCondition(
        type = AND,
        collections = this
    )
}

fun BaseCondition?.orNull(): BaseCondition? {
    if (this == null) {
        return null
    } else {
        return this.apply {
            this.allowNull = true
        }
    }
}

fun LikeCondition?.matchLeft(): BaseCondition? {
    return this?.apply {
        pos = Left
    }
}

fun LikeCondition?.matchRight(): BaseCondition? {
    return this?.apply {
        pos = Right
    }
}

fun LikeCondition?.matchBoth(): BaseCondition? {
    return this?.apply {
        pos = Both
    }
}

fun BaseCondition?.iif(expression: Boolean): BaseCondition? {
    return expression.yes { this }
}

fun BaseCondition?.reName(newName: String): BaseCondition? {
    return this?.apply {
        reName = newName
    }
}
