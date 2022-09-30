package com.kotoframework.definition

import com.kotoframework.core.condition.BaseCondition

/**
 * Created by ousc on 2022/5/30 17:05
 */

typealias AddCondition<E> = (KPojo: E) -> BaseCondition?

typealias AddOnCondition<A> = (table1: A) -> BaseCondition?
typealias AddOnCondition2<A, B> = (table1: A, table2: B) -> BaseCondition?
typealias AddOnCondition3<A, B, C> = (table1: A, table2: B, table3: C) -> BaseCondition?
typealias AddOnCondition4<A, B, C, D> = (table1: A, table2: B, table3: C, table4: D) -> BaseCondition?
typealias AddOnCondition5<A, B, C, D, E> = (table1: A, table2: B, table3: C, table4: D, table5: E) -> BaseCondition?
typealias AddOnCondition6<A, B, C, D, E, F> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F) -> BaseCondition?
typealias AddOnCondition7<A, B, C, D, E, F, G> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G) -> BaseCondition?
typealias AddOnCondition8<A, B, C, D, E, F, G, H> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H) -> BaseCondition?
typealias AddOnCondition9<A, B, C, D, E, F, G, H, I> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I) -> BaseCondition?
typealias AddOnCondition10<A, B, C, D, E, F, G, H, I, J> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J) -> BaseCondition?
