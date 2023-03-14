package com.kotoframework.function.associate

import com.kotoframework.beans.Unknown
import com.kotoframework.definition.Field
import com.kotoframework.definition.toColumn
import com.kotoframework.definition.aliasName
import com.kotoframework.interfaces.KPojo
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/5/30 17:51
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance()
): AssociateAction<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance()
): AssociateAction<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance(),
    kPojo12: T12? = T12::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance(),
    kPojo12: T12? = T12::class.javaInstance(),
    kPojo13: T13? = T13::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance(),
    kPojo12: T12? = T12::class.javaInstance(),
    kPojo13: T13? = T13::class.javaInstance(),
    kPojo14: T14? = T14::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance(),
    kPojo12: T12? = T12::class.javaInstance(),
    kPojo13: T13? = T13::class.javaInstance(),
    kPojo14: T14? = T14::class.javaInstance(),
    kPojo15: T15? = T15::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Unknown> {
    return AssociateAction(
        kPojo1,
        kPojo2,
        kPojo3,
        kPojo4,
        kPojo5,
        kPojo6,
        kPojo7,
        kPojo8,
        kPojo9,
        kPojo10,
        kPojo11,
        kPojo12,
        kPojo13,
        kPojo14,
        kPojo15
    )
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> associate(
    kPojo1: T1? = T1::class.javaInstance(),
    kPojo2: T2? = T2::class.javaInstance(),
    kPojo3: T3? = T3::class.javaInstance(),
    kPojo4: T4? = T4::class.javaInstance(),
    kPojo5: T5? = T5::class.javaInstance(),
    kPojo6: T6? = T6::class.javaInstance(),
    kPojo7: T7? = T7::class.javaInstance(),
    kPojo8: T8? = T8::class.javaInstance(),
    kPojo9: T9? = T9::class.javaInstance(),
    kPojo10: T10? = T10::class.javaInstance(),
    kPojo11: T11? = T11::class.javaInstance(),
    kPojo12: T12? = T12::class.javaInstance(),
    kPojo13: T13? = T13::class.javaInstance(),
    kPojo14: T14? = T14::class.javaInstance(),
    kPojo15: T15? = T15::class.javaInstance(),
    kPojo16: T16? = T16::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
    return AssociateAction(
        kPojo1,
        kPojo2,
        kPojo3,
        kPojo4,
        kPojo5,
        kPojo6,
        kPojo7,
        kPojo8,
        kPojo9,
        kPojo10,
        kPojo11,
        kPojo12,
        kPojo13,
        kPojo14,
        kPojo15,
        kPojo16
    )
}


internal fun getSql(
    sql: String, field: Field, type: String, tableAlias: String, complex: Boolean = false
): String {
    val fieldAlias = if (complex) {
        "`$tableAlias${field.aliasName.replaceFirstChar { it.uppercase() }}`"
    } else {
        "`${field.aliasName}`"
    }
    val (columnName) = field.toColumn()
    return when {
        type == "date" -> "$sql DATE_FORMAT(`$tableAlias`.`$columnName`, '%Y-%m-%d') as $fieldAlias"
        type == "datetime" -> "$sql DATE_FORMAT(`$tableAlias`.`$columnName`, '%Y-%m-%d %H:%i:%s') as $fieldAlias"
        else -> {
            if (columnName.contains("(") || columnName.lowercase().contains(" as ")) {
                "$sql $columnName"
            } else "$sql `$tableAlias`.`$columnName` as $fieldAlias"
        }
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> KClass<*>.javaInstance(): T {
    return java.newInstance() as T
}
