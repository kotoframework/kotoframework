package com.kotoframework.core.condition

import com.kotoframework.*
import com.kotoframework.definition.Field
import com.kotoframework.definition.columnName
import com.kotoframework.enums.ConditionType
import com.kotoframework.enums.NoValueStrategy
import com.kotoframework.utils.tableAlias
import com.kotoframework.utils.tableName
import com.kotoframework.utils.SqlGenerator
import kotlin.reflect.*
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod


/**
 * Created by ousc on 2022/4/18 11:16
 *
 * */

/**
 * Eq
 *
 * @param value
 * @return Criteria
 * @author ousc
 */
fun String.eq(
    value: Any? = null
): Criteria {
    return Condition(this).eq(value)
}

val String.eq: Criteria get() = eq()

/**
 * NotEq
 *
 * @param value
 * @return Criteria
 * @author ousc
 */
fun String.notEq(
    value: Any? = null
): Criteria {
    return Condition(this).notEq(value)
}

val String.notEq: Criteria get() = notEq()

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

fun String.isIn(
    vararg value: Any?
): Criteria {
    return Condition(this).isIn(value.toList())
}

fun String.notIn(
    vararg value: Any?
): Criteria {
    return Condition(this).isIn(value.toList())
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
        type = SQL, aliasName = this, sql = this
    )
}

// String Extension Functions End

// Field Extension Functions Start
infix fun Criteria?.and(condition: Criteria?): Criteria {
    val children = mutableListOf<Criteria?>()
    listOf(this, condition).forEach {
        if (it != null) {
            if (it.type == AND) {
                children.addAll(it.children)
            } else {
                children.add(it)
            }
        }
    }
    return Criteria(
        type = ConditionType.AND
    ).apply {  this.children = children }
}

infix fun Criteria?.and(condition: String?): Criteria? {
    if (condition == null) {
        return this
    }
    return this and condition.declareSql()
}

infix fun String?.and(condition: Criteria?): Criteria? {
    if (this == null) {
        return condition
    }
    return declareSql() and condition
}

infix fun String?.and(condition: String?): Criteria? {
    if (this == null) return condition?.declareSql()
    if (condition == null) return declareSql()
    return declareSql() and condition.declareSql()
}

infix fun Criteria?.or(condition: Criteria?): Criteria? {
    if (this == null && condition == null) return null
    val children = mutableListOf<Criteria?>()
    listOfNotNull(this, condition).forEach {
        if (it.type == OR) {
            children.addAll(it.children)
        } else {
            children.add(it)
        }
    }
    return Criteria(
        type = ConditionType.OR
    ).apply {
        this.children = children
    }
}

infix fun Criteria?.or(condition: String?): Criteria? {
    if (condition == null) {
        return this
    }
    return this or condition.declareSql()
}

infix fun String?.or(condition: Criteria?): Criteria? {
    if (this == null) {
        return condition
    }
    return declareSql() or condition
}

infix fun String?.or(condition: String?): Criteria? {
    if (this == null) {
        return condition?.declareSql()
    }
    if (condition == null) {
        return declareSql()
    }
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
    return Condition(name).eq(value, false, receiver.tableName)
}

val KCallable<*>.eq get() = eq()

fun KCallable<*>.notEq(
    value: Any? = null
): Criteria {
    return Condition(name).notEq(value, receiver.tableName)
}

val KCallable<*>.notEq get() = notEq()
fun KCallable<*>.gt(
    value: Any? = null
): Criteria {
    return Condition(name).gt(value, receiver.tableName)
}

val KCallable<*>.gt get() = gt()

fun KCallable<*>.ge(
    value: Any? = null
): Criteria {
    return Condition(name).ge(value, receiver.tableName)
}

val KCallable<*>.ge get() = ge()

fun KCallable<*>.lt(
    value: Any? = null
): Criteria {
    return Condition(name).lt(value, receiver.tableName)
}

val KCallable<*>.lt get() = lt()

fun KCallable<*>.le(
    value: Any? = null
): Criteria {
    return Condition(name).le(value, receiver.tableName)
}

val KCallable<*>.le get() = le()

fun KCallable<*>.between(
    value: ClosedRange<*>?,
): Criteria {
    return Condition(name).between(value, false, receiver.tableName)
}

fun KCallable<*>.notBetween(
    value: ClosedRange<*>?
): Criteria {
    return Condition(name).notBetween(value, receiver.tableName)
}

fun KCallable<*>.before(
    value: Any? = null
): Criteria {
    return Condition(name).lt(value, receiver.tableName)
}

val KCallable<*>.before get() = before()

fun KCallable<*>.after(
    value: Any? = null
): Criteria {
    return Condition(name).gt(value, receiver.tableName)
}

val KCallable<*>.after get() = after()


fun KCallable<*>.notBefore(
    value: Any? = null
): Criteria {
    return Condition(name).ge(value, receiver.tableName)
}

val KCallable<*>.notBefore get() = notBefore()

fun KCallable<*>.notAfter(
    value: Any? = null
): Criteria {
    return Condition(name).le(value, receiver.tableName)
}

val KCallable<*>.notAfter get() = notAfter()

fun KCallable<*>.isIn(
    value: Collection<*>?
): Criteria {
    return Condition(name).isIn(value, false, receiver.tableName)
}

fun KCallable<*>.notIn(
    value: Collection<*>?
): Criteria {
    return Condition(name).notIn(value, receiver.tableName)
}

fun KCallable<*>.isIn(
    vararg value: Any?
): Criteria {
    return Condition(name).isIn(value.toList(), false, receiver.tableName)
}

fun KCallable<*>.notIn(
    vararg value: Any?
): Criteria {
    return Condition(name).notIn(value.toList(), receiver.tableName)
}

@JvmName("getIsNull")
fun KCallable<*>.isNull(): Criteria {
    return Condition(name).isNull(false, receiver.tableName)
}

val KCallable<*>.isNull get() = isNull()

fun KCallable<*>.notNull(): Criteria {
    return Condition(name).notNull(this.receiver.tableName)
}

val KCallable<*>.notNull get() = notNull()

fun List<Criteria?>.arbitrary(): Criteria {
    val children = this.toMutableList()
    return Criteria(
        type = AND
    ).apply {
        this. children = children
    }
}

fun Criteria?.ifNoValue(strategy: (Criteria) -> NoValueStrategy): Criteria? {
    return this?.apply {
        this.noValueStrategy = strategy(this)
    }
}

fun Criteria.alias(newName: String): Criteria {
    return this.apply {
        aliasName = newName.takeIf { it.isNotBlank() } ?: aliasName
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
        aliasName = newName
    }
}
