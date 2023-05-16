package com.kotoframework.orm.query

import com.kotoframework.beans.KResultSet
import com.kotoframework.beans.Unknown
import com.kotoframework.definition.Field
import com.kotoframework.interfaces.KPojo
import com.kotoframework.orm.*
import com.kotoframework.orm.query.actions.*
import kotlin.reflect.KClass

/**
 * Created by ousc on 2023/4/3 02:05
 */
val unknown = Unknown()

/*****
 *
 *  from{} APIs
 *
 * ******/
fun <T : KPojo> fromCallBack(qa: QA): KResultSet<T> {
    @Suppress("UNCHECKED_CAST")
    return qa.prepared as KResultSet<T>
}


inline fun <reified T : KPojo> from(
        t: T? = T::class.javaInstance(),
        action: SelectFrom<T>.(T) -> QA
): KResultSet<T> {
    return fromCallBack(SelectFrom(t).action(t!!))
}


inline fun <reified T : KPojo> from(
        action: QueryFrom<T, QA>
): KResultSet<T> {
    val t = T::class.javaInstance<T>()
    return fromCallBack(SelectFrom(t).action(t))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        action: QueryFrom2<T1, T2, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom2(t1, t2).action(t1!!, t2!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo> from(
        action: QueryFrom2<T1, T2, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    return fromCallBack(SelectFrom2(t1, t2).action(t1, t2))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        action: QueryFrom3<T1, T2, T3, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom3(t1, t2, t3).action(t1!!, t2!!, t3!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> from(
        action: QueryFrom3<T1, T2, T3, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    return fromCallBack(SelectFrom3(t1, t2, t3).action(t1, t2, t3))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        action: QueryFrom4<T1, T2, T3, T4, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom4(t1, t2, t3, t4).action(t1!!, t2!!, t3!!, t4!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> from(
        action: QueryFrom4<T1, T2, T3, T4, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    return fromCallBack(SelectFrom4(t1, t2, t3, t4).action(t1, t2, t3, t4))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        action: QueryFrom5<T1, T2, T3, T4, T5, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom5(t1, t2, t3, t4, t5).action(t1!!, t2!!, t3!!, t4!!, t5!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> from(
        action: QueryFrom5<T1, T2, T3, T4, T5, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    return fromCallBack(SelectFrom5(t1, t2, t3, t4, t5).action(t1, t2, t3, t4, t5))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        action: QueryFrom6<T1, T2, T3, T4, T5, T6, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom6(t1, t2, t3, t4, t5, t6).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> from(
        action: QueryFrom6<T1, T2, T3, T4, T5, T6, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    return fromCallBack(SelectFrom6(t1, t2, t3, t4, t5, t6).action(t1, t2, t3, t4, t5, t6))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        action: QueryFrom7<T1, T2, T3, T4, T5, T6, T7, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom7(t1, t2, t3, t4, t5, t6, t7).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> from(
        action: QueryFrom7<T1, T2, T3, T4, T5, T6, T7, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    return fromCallBack(SelectFrom7(t1, t2, t3, t4, t5, t6, t7).action(t1, t2, t3, t4, t5, t6, t7))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        action: QueryFrom8<T1, T2, T3, T4, T5, T6, T7, T8, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom8(t1, t2, t3, t4, t5, t6, t7, t8).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> from(
        action: QueryFrom8<T1, T2, T3, T4, T5, T6, T7, T8, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    return fromCallBack(SelectFrom8(t1, t2, t3, t4, t5, t6, t7, t8).action(t1, t2, t3, t4, t5, t6, t7, t8))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        action: Statement9<T1, T2, T3, T4, T5, T6, T7, T8, T9, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom9(t1, t2, t3, t4, t5, t6, t7, t8, t9).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> from(
        action: Statement9<T1, T2, T3, T4, T5, T6, T7, T8, T9, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    return fromCallBack(SelectFrom9(t1, t2, t3, t4, t5, t6, t7, t8, t9).action(t1, t2, t3, t4, t5, t6, t7, t8, t9))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        action: Statement10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom10(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> from(
        action: Statement10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    return fromCallBack(SelectFrom10(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        action: Statement11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom11(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> from(
        action: Statement11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    return fromCallBack(SelectFrom11(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        t12: T12? = T12::class.javaInstance(),
        action: Statement12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom12(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> from(
        action: Statement12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    val t12 = T12::class.javaInstance<T12>()
    return fromCallBack(SelectFrom12(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        t12: T12? = T12::class.javaInstance(),
        t13: T13? = T13::class.javaInstance(),
        action: Statement13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom13(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> from(
        action: Statement13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    val t12 = T12::class.javaInstance<T12>()
    val t13 = T13::class.javaInstance<T13>()
    return fromCallBack(SelectFrom13(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        t12: T12? = T12::class.javaInstance(),
        t13: T13? = T13::class.javaInstance(),
        t14: T14? = T14::class.javaInstance(),
        action: Statement14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom14(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> from(
        action: Statement14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    val t12 = T12::class.javaInstance<T12>()
    val t13 = T13::class.javaInstance<T13>()
    val t14 = T14::class.javaInstance<T14>()
    return fromCallBack(SelectFrom14(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        t12: T12? = T12::class.javaInstance(),
        t13: T13? = T13::class.javaInstance(),
        t14: T14? = T14::class.javaInstance(),
        t15: T15? = T15::class.javaInstance(),
        action: Statement15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom15(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!, t15!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> from(
        action: Statement15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    val t12 = T12::class.javaInstance<T12>()
    val t13 = T13::class.javaInstance<T13>()
    val t14 = T14::class.javaInstance<T14>()
    val t15 = T15::class.javaInstance<T15>()
    return fromCallBack(SelectFrom15(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> from(
        t1: T1? = T1::class.javaInstance(),
        t2: T2? = T2::class.javaInstance(),
        t3: T3? = T3::class.javaInstance(),
        t4: T4? = T4::class.javaInstance(),
        t5: T5? = T5::class.javaInstance(),
        t6: T6? = T6::class.javaInstance(),
        t7: T7? = T7::class.javaInstance(),
        t8: T8? = T8::class.javaInstance(),
        t9: T9? = T9::class.javaInstance(),
        t10: T10? = T10::class.javaInstance(),
        t11: T11? = T11::class.javaInstance(),
        t12: T12? = T12::class.javaInstance(),
        t13: T13? = T13::class.javaInstance(),
        t14: T14? = T14::class.javaInstance(),
        t15: T15? = T15::class.javaInstance(),
        t16: T16? = T16::class.javaInstance(),
        action: Statement16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, QA>
): KResultSet<T1> {
    return fromCallBack(SelectFrom16(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16).action(t1!!, t2!!, t3!!, t4!!, t5!!, t6!!, t7!!, t8!!, t9!!, t10!!, t11!!, t12!!, t13!!, t14!!, t15!!, t16!!))
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> from(
        action: Statement16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, QA>
): KResultSet<T1> {
    val t1 = T1::class.javaInstance<T1>()
    val t2 = T2::class.javaInstance<T2>()
    val t3 = T3::class.javaInstance<T3>()
    val t4 = T4::class.javaInstance<T4>()
    val t5 = T5::class.javaInstance<T5>()
    val t6 = T6::class.javaInstance<T6>()
    val t7 = T7::class.javaInstance<T7>()
    val t8 = T8::class.javaInstance<T8>()
    val t9 = T9::class.javaInstance<T9>()
    val t10 = T10::class.javaInstance<T10>()
    val t11 = T11::class.javaInstance<T11>()
    val t12 = T12::class.javaInstance<T12>()
    val t13 = T13::class.javaInstance<T13>()
    val t14 = T14::class.javaInstance<T14>()
    val t15 = T15::class.javaInstance<T15>()
    val t16 = T16::class.javaInstance<T16>()
    return fromCallBack(SelectFrom16(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16).action(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16))
}

/**
 * from.select() APIs
 */


inline fun <reified T : KPojo> from(
        table: T? = T::class.javaInstance()
): SelectFrom<T> {
    return SelectFrom(table)
}

inline fun <reified T : KPojo> select(
        table: T? = T::class.javaInstance(),
        vararg fields: Field
): SelectFrom<T> {
    return SelectFrom(table).apply { select(*fields) }
}

inline fun <reified T1 : KPojo, reified T2 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance()
): SelectFrom2<T1, T2> {
    return SelectFrom2(kPojo1, kPojo2)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance()
): SelectFrom3<T1, T2, T3> {
    return SelectFrom3(kPojo1, kPojo2, kPojo3)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance()
): SelectFrom4<T1, T2, T3, T4> {
    return SelectFrom4(kPojo1, kPojo2, kPojo3, kPojo4)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance()
): SelectFrom5<T1, T2, T3, T4, T5> {
    return SelectFrom5(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance()
): SelectFrom6<T1, T2, T3, T4, T5, T6> {
    return SelectFrom6(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance()
): SelectFrom7<T1, T2, T3, T4, T5, T6, T7> {
    return SelectFrom7(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance()
): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8> {
    return SelectFrom8(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance()
): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    return SelectFrom9(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance()
): SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    return SelectFrom10(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance()
): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
    return SelectFrom11(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance(), kPojo12: T12? = T12::class.javaInstance()
): SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {
    return SelectFrom12(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance(), kPojo12: T12? = T12::class.javaInstance(), kPojo13: T13? = T13::class.javaInstance()
): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {
    return SelectFrom13(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance(), kPojo12: T12? = T12::class.javaInstance(), kPojo13: T13? = T13::class.javaInstance(), kPojo14: T14? = T14::class.javaInstance()
): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {
    return SelectFrom14(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance(), kPojo12: T12? = T12::class.javaInstance(), kPojo13: T13? = T13::class.javaInstance(), kPojo14: T14? = T14::class.javaInstance(), kPojo15: T15? = T15::class.javaInstance()
): SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {
    return SelectFrom15(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14, kPojo15)
}

inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> from(
        kPojo1: T1? = T1::class.javaInstance(), kPojo2: T2? = T2::class.javaInstance(), kPojo3: T3? = T3::class.javaInstance(), kPojo4: T4? = T4::class.javaInstance(), kPojo5: T5? = T5::class.javaInstance(), kPojo6: T6? = T6::class.javaInstance(), kPojo7: T7? = T7::class.javaInstance(), kPojo8: T8? = T8::class.javaInstance(), kPojo9: T9? = T9::class.javaInstance(), kPojo10: T10? = T10::class.javaInstance(), kPojo11: T11? = T11::class.javaInstance(), kPojo12: T12? = T12::class.javaInstance(), kPojo13: T13? = T13::class.javaInstance(), kPojo14: T14? = T14::class.javaInstance(), kPojo15: T15? = T15::class.javaInstance(), kPojo16: T16? = T16::class.javaInstance()
): SelectFrom16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
    return SelectFrom16(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14, kPojo15, kPojo16)
}


@Suppress("UNCHECKED_CAST")
fun <T> KClass<*>.javaInstance(): T {
    return java.newInstance() as T
}

