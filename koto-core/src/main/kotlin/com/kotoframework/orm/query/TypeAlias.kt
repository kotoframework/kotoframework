package com.kotoframework.orm.query

import com.kotoframework.beans.Unknown
import com.kotoframework.definition.CriteriaField

/**
 * Created by sundaiyue on 2023/4/3 19:32
 */
typealias QA = QueryAction<*, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *>

typealias QueryAction1 <T1> = QueryAction<T1, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria1 <T1> = CriteriaField.(T1) -> Boolean?

typealias QueryAction2 <T1, T2> = QueryAction<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria2 <T1, T2> = CriteriaField.(T1, T2) -> Boolean?

typealias QueryAction3 <T1, T2, T3> = QueryAction<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria3 <T1, T2, T3> = CriteriaField.(T1, T2, T3) -> Boolean?

typealias QueryAction4 <T1, T2, T3, T4> = QueryAction<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria4 <T1, T2, T3, T4> = CriteriaField.(T1, T2, T3, T4) -> Boolean?

typealias QueryAction5 <T1, T2, T3, T4, T5> = QueryAction<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria5 <T1, T2, T3, T4, T5> = CriteriaField.(T1, T2, T3, T4, T5) -> Boolean?

typealias QueryAction6 <T1, T2, T3, T4, T5, T6> = QueryAction<T1, T2, T3, T4, T5, T6, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria6 <T1, T2, T3, T4, T5, T6> = CriteriaField.(T1, T2, T3, T4, T5, T6) -> Boolean?

typealias QueryAction7 <T1, T2, T3, T4, T5, T6, T7> = QueryAction<T1, T2, T3, T4, T5, T6, T7, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria7 <T1, T2, T3, T4, T5, T6, T7> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7) -> Boolean?

typealias QueryAction8 <T1, T2, T3, T4, T5, T6, T7, T8> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria8 <T1, T2, T3, T4, T5, T6, T7, T8> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8) -> Boolean?

typealias QueryAction9 <T1, T2, T3, T4, T5, T6, T7, T8, T9> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria9 <T1, T2, T3, T4, T5, T6, T7, T8, T9> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Boolean?

typealias QueryAction10 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria10 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Boolean?

typealias QueryAction11 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria11 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Boolean?

typealias QueryAction12 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Unknown, Unknown, Unknown, Unknown>
typealias AddCriteria12 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Boolean?

typealias QueryAction13 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Unknown, Unknown, Unknown>
typealias AddCriteria13 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> Boolean?

typealias QueryAction14 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Unknown, Unknown>
typealias AddCriteria14 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> Boolean?

typealias QueryAction15 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = QueryAction<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Unknown>
typealias AddCriteria15 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> Boolean?

typealias AddCriteria16 <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> = CriteriaField.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> Boolean?