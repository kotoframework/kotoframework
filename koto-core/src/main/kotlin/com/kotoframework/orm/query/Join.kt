package com.kotoframework.orm.query

import com.kotoframework.enums.JoinType
import com.kotoframework.interfaces.KPojo
import com.kotoframework.orm.*
import com.kotoframework.orm.query.actions.*


fun <T: QA> T.left() = this to JoinType.LEFT_JOIN
fun <T: QA> T.right() = this to JoinType.RIGHT_JOIN
fun <T: QA> T.full() = this to JoinType.FULL_JOIN
fun <T: QA> T.cross() = this to JoinType.CROSS_JOIN
fun <T: QA> T.inner() = this to JoinType.INNER_JOIN

inline fun <reified K : KPojo, T : KPojo> Pair<SelectFrom<T>, JoinType>.join(
    t2: K = K::class.javaInstance(),
    action: AddJoin2<T, K> = { _, _ -> this }
): SelectFrom2<T, K> {
    return first.join(t2, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo> Pair<SelectFrom2<T1, T2>, JoinType>.join(
    t3: K = K::class.javaInstance(),
    action: AddJoin3<T1, T2, K> = { _, _, _ -> this }
): SelectFrom3<T1, T2, K> {
    return first.join(t3, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo> Pair<SelectFrom3<T1, T2, T3>, JoinType>.join(
    t4: K = K::class.javaInstance(),
    action: AddJoin4<T1, T2, T3, K> = { _, _, _, _ -> this }
): SelectFrom4<T1, T2, T3, K> {
    return first.join(t4, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo> Pair<SelectFrom4<T1, T2, T3, T4>, JoinType>.join(
    t5: K = K::class.javaInstance(),
    action: AddJoin5<T1, T2, T3, T4, K> = { _, _, _, _, _ -> this }
): SelectFrom5<T1, T2, T3, T4, K> {
    return first.join(t5, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo> Pair<SelectFrom5<T1, T2, T3, T4, T5>, JoinType>.join(
    t6: K = K::class.javaInstance(),
    action: AddJoin6<T1, T2, T3, T4, T5, K> = { _, _, _, _, _, _ -> this }
): SelectFrom6<T1, T2, T3, T4, T5, K> {
    return first.join(t6, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo> Pair<SelectFrom6<T1, T2, T3, T4, T5, T6>, JoinType>.join(
    t7: K = K::class.javaInstance(),
    action: AddJoin7<T1, T2, T3, T4, T5, T6, K> = { _, _, _, _, _, _, _ -> this }
): SelectFrom7<T1, T2, T3, T4, T5, T6, K> {
    return first.join(t7, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo> Pair<SelectFrom7<T1, T2, T3, T4, T5, T6, T7>, JoinType>.join(
    t8: K = K::class.javaInstance(),
    action: AddJoin8<T1, T2, T3, T4, T5, T6, T7, K> = { _, _, _, _, _, _, _, _ -> this }
): SelectFrom8<T1, T2, T3, T4, T5, T6, T7, K> {
    return first.join(t8, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo> Pair<SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>, JoinType>.join(
    t9: K = K::class.javaInstance(),
    action: AddJoin9<T1, T2, T3, T4, T5, T6, T7, T8, K> = { _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, K> {
    return first.join(t9, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo> Pair<SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, JoinType>.join(
    t10: K = K::class.javaInstance(),
    action: AddJoin10<T1, T2, T3, T4, T5, T6, T7, T8, T9, K> = { _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, K> {
    return first.join(t10, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo> Pair<SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, JoinType>.join(
    t11: K = K::class.javaInstance(),
    action: AddJoin11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, K> = { _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, K> {
    return first.join(t11, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo> Pair<SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, JoinType>.join(
    t12: K = K::class.javaInstance(),
    action: AddJoin12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, K> = { _, _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, K> {
    return first.join(t12, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo> Pair<SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, JoinType>.join(
    t13: K = K::class.javaInstance(),
    action: AddJoin13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, K> = { _, _, _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, K> {
    return first.join(t13, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo> Pair<SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, JoinType>.join(
    t14: K = K::class.javaInstance(),
    action: AddJoin14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, K> = { _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, K> {
    return first.join(t14, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo> Pair<SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, JoinType>.join(
    t15: K = K::class.javaInstance(),
    action: AddJoin15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, K> = { _, _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, K> {
    return first.join(t15, second, action)
}

inline fun <reified K : KPojo, T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo, T15 : KPojo> Pair<SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, JoinType>.join(
    t16: K = K::class.javaInstance(),
    action: AddJoin16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, K> = { _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> this }
): SelectFrom16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, K> {
    return first.join(t16, second, action)
}
