package com.kotoframework.function.associate

import com.kotoframework.beans.Unknown
import com.kotoframework.definition.Field
import com.kotoframework.definition.fd
import com.kotoframework.definition.aliasName
import com.kotoframework.interfaces.KPojo
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/5/30 17:51
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(), dto2: T2? = T2::class.javaInstance()
): AssociateAction<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(), dto2: T2? = T2::class.javaInstance(), dto3: T3? = T3::class.javaInstance()
): AssociateAction<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10, dto11)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance(),
    dto12: T12? = T12::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10, dto11, dto12)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance(),
    dto12: T12? = T12::class.javaInstance(),
    dto13: T13? = T13::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10, dto11, dto12, dto13)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance(),
    dto12: T12? = T12::class.javaInstance(),
    dto13: T13? = T13::class.javaInstance(),
    dto14: T14? = T14::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10, dto11, dto12, dto13, dto14)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance(),
    dto12: T12? = T12::class.javaInstance(),
    dto13: T13? = T13::class.javaInstance(),
    dto14: T14? = T14::class.javaInstance(),
    dto15: T15? = T15::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Unknown> {
    return AssociateAction(
        dto1,
        dto2,
        dto3,
        dto4,
        dto5,
        dto6,
        dto7,
        dto8,
        dto9,
        dto10,
        dto11,
        dto12,
        dto13,
        dto14,
        dto15
    )
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> associate(
    dto1: T1? = T1::class.javaInstance(),
    dto2: T2? = T2::class.javaInstance(),
    dto3: T3? = T3::class.javaInstance(),
    dto4: T4? = T4::class.javaInstance(),
    dto5: T5? = T5::class.javaInstance(),
    dto6: T6? = T6::class.javaInstance(),
    dto7: T7? = T7::class.javaInstance(),
    dto8: T8? = T8::class.javaInstance(),
    dto9: T9? = T9::class.javaInstance(),
    dto10: T10? = T10::class.javaInstance(),
    dto11: T11? = T11::class.javaInstance(),
    dto12: T12? = T12::class.javaInstance(),
    dto13: T13? = T13::class.javaInstance(),
    dto14: T14? = T14::class.javaInstance(),
    dto15: T15? = T15::class.javaInstance(),
    dto16: T16? = T16::class.javaInstance()
): AssociateAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
    return AssociateAction(
        dto1,
        dto2,
        dto3,
        dto4,
        dto5,
        dto6,
        dto7,
        dto8,
        dto9,
        dto10,
        dto11,
        dto12,
        dto13,
        dto14,
        dto15,
        dto16
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
    return when {
        type == "date" -> "$sql DATE_FORMAT(`$tableAlias`.`${field.fd.columnName}`, '%Y-%m-%d') as $fieldAlias"
        type == "datetime" -> "$sql DATE_FORMAT(`$tableAlias`.`${field.fd.columnName}`, '%Y-%m-%d %H:%i:%s') as $fieldAlias"
        else -> {
            if (field.fd.columnName.contains("(") || field.fd.columnName.lowercase().contains(" as ")) {
                "$sql ${field.fd.columnName}"
            } else "$sql `$tableAlias`.`${field.fd.columnName}` as $fieldAlias"
        }
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> KClass<*>.javaInstance(): T {
    return this.java.newInstance() as T
}
