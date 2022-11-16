package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.definition.Field
import com.kotoframework.definition.columnName
import com.kotoframework.utils.Extension.tableAlias
import com.kotoframework.utils.Extension.tableName
import com.kotoframework.utils.SqlGenerator
import kotlin.reflect.*
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod


/**
 * Created by ousc on 2022/4/18 11:16
 *
 * */

fun String.eq(
    value: Any? = null
): Criteria {
    return Condition(this).eq(value)
}

val String.eq: Criteria get() = eq()

fun String.notEq(
    value: Any? = null
): Criteria {
    return Condition(this).notEq(value)
}

val String.notEq: Criteria get() = notEq()

fun String.like(
    value: String? = null
): LikeCriteria {
    return Condition(this).like(value)
}

val String.like: LikeCriteria get() = like()

fun String.notLike(
    value: String? = null
): LikeCriteria {
    return Condition(this).notLike(value)
}

val String.notLike: Criteria get() = notLike()

fun String.gt(
    value: Any? = null
): Criteria {
    return Condition(this).gt(value)
}

fun String.ge(
    value: Any? = null
): Criteria {
    return Condition(this).ge(value)
}

fun String.lt(
    value: Any? = null
): Criteria {
    return Condition(this).lt(value)
}

fun String.le(
    value: Any? = null
): Criteria {
    return Condition(this).le(value)
}

fun String.between(
    value: ClosedRange<*>
): Criteria {
    return Condition(this).between(value)
}

fun String.notBetween(
    value: ClosedRange<*>?
): Criteria {
    return Condition(this).notBetween(value)
}

fun String.isIn(
    value: Collection<*>?
): Criteria {
    return Condition(this).isIn(value)
}

fun String.notIn(
    value: Collection<*>?
): Criteria {
    return Condition(this).notIn(value)
}

@JvmName("getIsNull")
fun String.isNull(): Criteria {
    return Condition(this).isNull()
}

val String.isNull: Criteria get() = isNull()
fun String.notNull(): Criteria {
    return Condition(this).notNull()
}

val String.notNull: Criteria get() = notNull()

fun String.declareSql(): Criteria {
    return Criteria(
        type = SQL, reName = this, sql = this
    )
}

// String Extension Functions End

// Field Extension Functions Start
infix fun Criteria?.and(condition: Criteria?): Criteria {
    val collections = mutableListOf<Criteria?>()
    listOf(this, condition).forEach {
        if (it != null) {
            if (it.type == AND) {
                collections.addAll(it.collections)
            } else {
                collections.add(it)
            }
        }
    }
    return Criteria(
        type = ConditionType.AND, collections = collections
    )
}

infix fun Criteria?.and(condition: String?): Criteria? {
    if (condition == null) return this
    return this and condition.declareSql()
}

infix fun String?.and(condition: Criteria?): Criteria? {
    if (this == null) return condition
    return declareSql() and condition
}

infix fun String?.and(condition: String?): Criteria? {
    if (this == null) return condition?.declareSql()
    if (condition == null) return declareSql()
    return declareSql() and condition.declareSql()
}

infix fun Criteria?.or(condition: Criteria?): Criteria? {
    if (this == null && condition == null) return null
    val collections = mutableListOf<Criteria?>()
    listOfNotNull(this, condition).forEach {
        if (it.type == OR) {
            collections.addAll(it.collections)
        } else {
            collections.add(it)
        }
    }
    return Criteria(
        type = ConditionType.OR, collections = collections
    )
}

infix fun Criteria?.or(condition: String?): Criteria? {
    if (condition == null) return this
    return this or condition.declareSql()
}

infix fun String?.or(condition: Criteria?): Criteria? {
    if (this == null) return condition
    return declareSql() or condition
}

infix fun String?.or(condition: String?): Criteria? {
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
    value: Any? = null
): Criteria {
    if (value is KCallable<*>) {
        return Criteria(
            type = SQL,
            sql = " ${receiver.tableAlias}.${columnName} = ${value.receiver.tableAlias}.${value.columnName} ",
            tableName = value.receiver.tableName
        )
    }
    return Condition(name, this).eq(value, false, receiver.tableName)
}

val KCallable<*>.eq get() = eq()

fun KCallable<*>.notEq(
    value: Any? = null
): Criteria {
    return Condition(name, this).notEq(value, receiver.tableName)
}

val KCallable<*>.notEq get() = notEq()

fun KCallable<*>.like(
    expression: String? = null,
): LikeCriteria {
    return Condition(name, this).like(
        expression,
        false,
        receiver.tableName
    )
}

val KCallable<*>.like get() = like()

fun KCallable<*>.notLike(
    expression: String? = null,
): LikeCriteria {
    return Condition(name, this).notLike(expression, receiver.tableName)
}

val KCallable<*>.notLike get() = notLike()

fun KCallable<*>.gt(
    value: Any? = null
): Criteria {
    return Condition(name, this).gt(value, receiver.tableName)
}

val KCallable<*>.gt get() = gt()

fun KCallable<*>.ge(
    value: Any? = null
): Criteria {
    return Condition(name, this).ge(value, receiver.tableName)
}

val KCallable<*>.ge get() = ge()

fun KCallable<*>.lt(
    value: Any? = null
): Criteria {
    return Condition(name, this).lt(value, receiver.tableName)
}

val KCallable<*>.lt get() = lt()

fun KCallable<*>.le(
    value: Any? = null
): Criteria {
    return Condition(name, this).le(value, receiver.tableName)
}

val KCallable<*>.le get() = le()

fun KCallable<*>.between(
    value: ClosedRange<*>?,
): Criteria {
    return Condition(name, this).between(value, false, receiver.tableName)
}

fun KCallable<*>.notBetween(
    value: ClosedRange<*>?
): Criteria {
    return Condition(name, this).notBetween(value, receiver.tableName)
}

fun KCallable<*>.before(
    value: Any? = null
): Criteria {
    return Condition(name, this).lt(value, receiver.tableName)
}

val KCallable<*>.before get() = before()

fun KCallable<*>.after(
    value: Any? = null
): Criteria {
    return Condition(name, this).gt(value, receiver.tableName)
}

val KCallable<*>.after get() = after()


fun KCallable<*>.notBefore(
    value: Any? = null
): Criteria {
    return Condition(name, this).ge(value, receiver.tableName)
}

val KCallable<*>.notBefore get() = notBefore()

fun KCallable<*>.notAfter(
    value: Any? = null
): Criteria {
    return Condition(name, this).le(value, receiver.tableName)
}

val KCallable<*>.notAfter get() = notAfter()

fun KCallable<*>.isIn(
    value: Collection<*>?
): Criteria {
    return Condition(name, this).isIn(value, false, receiver.tableName)
}

fun KCallable<*>.notIn(
    value: Collection<*>?
): Criteria {
    return Condition(name, this).notIn(value, receiver.tableName)
}

@JvmName("getIsNull")
fun KCallable<*>.isNull(): Criteria {
    return Condition(name, this).isNull(false, receiver.tableName)
}

val KCallable<*>.isNull get() = isNull()

fun KCallable<*>.notNull(): Criteria {
    return Condition(name, this).notNull(this.receiver.tableName)
}

val KCallable<*>.notNull get() = notNull()

fun List<Criteria?>.arbitrary(): Criteria {
    return Criteria(
        type = AND,
        collections = this
    )
}

fun Criteria?.ifNoValue(strategy: (Criteria) -> NoValueStrategy): Criteria? {
    return this?.apply {
        this.noValueStrategy = strategy(this)
    }
}

fun LikeCriteria?.matchLeft(): Criteria? {
    return this?.apply {
        pos = Left
    }
}

fun LikeCriteria?.matchRight(): Criteria? {
    return this?.apply {
        pos = Right
    }
}

fun LikeCriteria?.matchBoth(): Criteria? {
    return this?.apply {
        pos = Both
    }
}

fun Criteria.alias(newName: String): Criteria {
    return this.apply {
        reName = newName.takeIf { it.isNotBlank() } ?: reName
        sql = SqlGenerator.generate(this)
    }
}


@Deprecated(
    "The 'iif(expression)' is deprecated, please use takeIf(expression) instead",
    ReplaceWith("this.takeIf { expression }")
)
fun Criteria?.iif(expression: Boolean): Criteria? {
    return this?.takeIf { expression }
}

@Deprecated(
    "The 'reName(newName)' is deprecated, please use alias(newName) instead",
    ReplaceWith("this.alias(newName)")
)
fun Criteria?.reName(newName: String): Criteria? {
    return this?.apply {
        reName = newName
    }
}
