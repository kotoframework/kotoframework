package com.kotoframework.definition

typealias AddField<F, T> = F.(T) -> Field?
typealias AddField2<F, T1, T2> = F.(T1, T2) -> Field?
typealias AddField3<F, T1, T2, T3> = F.(T1, T2, T3) -> Field?
typealias AddField4<F, T1, T2, T3, T4> = F.(T1, T2, T3, T4) -> Field?
typealias AddField5<F, T1, T2, T3, T4, T5> = F.(T1, T2, T3, T4, T5) -> Field?
typealias AddField6<F, T1, T2, T3, T4, T5, T6> = F.(T1, T2, T3, T4, T5, T6) -> Field?
typealias AddField7<F, T1, T2, T3, T4, T5, T6, T7> = F.(T1, T2, T3, T4, T5, T6, T7) -> Field?
typealias AddField8<F, T1, T2, T3, T4, T5, T6, T7, T8> = F.(T1, T2, T3, T4, T5, T6, T7, T8) -> Field?
typealias AddField9<F, T1, T2, T3, T4, T5, T6, T7, T8, T9> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Field?
typealias AddField10<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Field?
typealias AddField11<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Field?
typealias AddField12<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Field?
typealias AddField13<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> Field?
typealias AddField14<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> Field?
typealias AddField15<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> Field?
typealias AddField16<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> = F.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> Field?

open class SelectField {
    val selectedFields = mutableListOf<Field?>()
    val fields get() = selectedFields.filterNotNull()
    fun addFields(field: Field?) = selectedFields.add(field)
    infix fun <T> T.alias(alias: String) = this

    operator fun Any?.plus(other: Any?) = this
}

val selectField get() = SelectField()