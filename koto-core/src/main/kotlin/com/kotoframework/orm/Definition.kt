package com.kotoframework.orm

import com.kotoframework.orm.query.actions.*

/**
 * Created by ousc on 2022/7/19 21:59
 */
typealias QueryFrom<T, E> = SelectFrom<T>.(T) -> E
typealias QueryFrom2<T1, T2, E> = SelectFrom2<T1, T2>.(T1, T2) -> E
typealias QueryFrom3<T1, T2, T3, E> = SelectFrom3<T1, T2, T3>.(T1, T2, T3) -> E
typealias QueryFrom4<T1, T2, T3, T4, E> = SelectFrom4<T1, T2, T3, T4>.(T1, T2, T3, T4) -> E
typealias QueryFrom5<T1, T2, T3, T4, T5, E> = SelectFrom5<T1, T2, T3, T4, T5>.(T1, T2, T3, T4, T5) -> E
typealias QueryFrom6<T1, T2, T3, T4, T5, T6, E> = SelectFrom6<T1, T2, T3, T4, T5, T6>.(T1, T2, T3, T4, T5, T6) -> E
typealias QueryFrom7<T1, T2, T3, T4, T5, T6, T7, E> = SelectFrom7<T1, T2, T3, T4, T5, T6, T7>.(T1, T2, T3, T4, T5, T6, T7) -> E
typealias QueryFrom8<T1, T2, T3, T4, T5, T6, T7, T8, E> = SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>.(T1, T2, T3, T4, T5, T6, T7, T8) -> E
typealias Statement9<T1, T2, T3, T4, T5, T6, T7, T8, T9, E> = SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>.(T1, T2, T3, T4, T5, T6, T7, T8, T9) -> E
typealias Statement10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, E> = SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> E
typealias Statement11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, E> = SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> E
typealias Statement12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, E> = SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> E
typealias Statement13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, E> = SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> E
typealias Statement14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, E> = SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> E
typealias Statement15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, E> = SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> E
typealias Statement16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, E> = SelectFrom16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> E

typealias AddJoin2<T1, T2> = SelectFrom2<T1, T2>.(T1, T2) -> SelectFrom2<T1, T2>
typealias AddJoin3<T1, T2, T3> = SelectFrom3<T1, T2, T3>.(T1, T2, T3) -> SelectFrom3<T1, T2, T3>
typealias AddJoin4<T1, T2, T3, T4> = SelectFrom4<T1, T2, T3, T4>.(T1, T2, T3, T4) -> SelectFrom4<T1, T2, T3, T4>
typealias AddJoin5<T1, T2, T3, T4, T5> = SelectFrom5<T1, T2, T3, T4, T5>.(T1, T2, T3, T4, T5) -> SelectFrom5<T1, T2, T3, T4, T5>
typealias AddJoin6<T1, T2, T3, T4, T5, T6> = SelectFrom6<T1, T2, T3, T4, T5, T6>.(T1, T2, T3, T4, T5, T6) -> SelectFrom6<T1, T2, T3, T4, T5, T6>
typealias AddJoin7<T1, T2, T3, T4, T5, T6, T7> = SelectFrom7<T1, T2, T3, T4, T5, T6, T7>.(T1, T2, T3, T4, T5, T6, T7) -> SelectFrom7<T1, T2, T3, T4, T5, T6, T7>
typealias AddJoin8<T1, T2, T3, T4, T5, T6, T7, T8> = SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>.(T1, T2, T3, T4, T5, T6, T7, T8) -> SelectFrom8<T1, T2, T3, T4, T5, T6, T7, T8>
typealias AddJoin9<T1, T2, T3, T4, T5, T6, T7, T8, T9> = SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>.(T1, T2, T3, T4, T5, T6, T7, T8, T9) -> SelectFrom9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
typealias AddJoin10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> = SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> SelectFrom10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
typealias AddJoin11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> = SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> SelectFrom11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
typealias AddJoin12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> = SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> SelectFrom12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
typealias AddJoin13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> = SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> SelectFrom13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
typealias AddJoin14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> = SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> SelectFrom14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
typealias AddJoin15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> = SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> SelectFrom15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
typealias AddJoin16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> = SelectFrom16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>.(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> SelectFrom16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
