package com.kotoframework.definition

import com.kotoframework.core.condition.Criteria

/**
 * Created by ousc on 2022/5/30 17:05
 */

typealias AddCondition<E> = (KPojo: E) -> Criteria?

typealias AddOnCondition<A> = (table1: A) -> Criteria?
typealias AddOnCondition2<A, B> = (table1: A, table2: B) -> Criteria?
typealias AddOnCondition3<A, B, C> = (table1: A, table2: B, table3: C) -> Criteria?
typealias AddOnCondition4<A, B, C, D> = (table1: A, table2: B, table3: C, table4: D) -> Criteria?
typealias AddOnCondition5<A, B, C, D, E> = (table1: A, table2: B, table3: C, table4: D, table5: E) -> Criteria?
typealias AddOnCondition6<A, B, C, D, E, F> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F) -> Criteria?
typealias AddOnCondition7<A, B, C, D, E, F, G> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G) -> Criteria?
typealias AddOnCondition8<A, B, C, D, E, F, G, H> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H) -> Criteria?
typealias AddOnCondition9<A, B, C, D, E, F, G, H, I> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I) -> Criteria?
typealias AddOnCondition10<A, B, C, D, E, F, G, H, I, J> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J) -> Criteria?
typealias AddOnCondition11<A, B, C, D, E, F, G, H, I, J, K> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K) -> Criteria?
typealias AddOnCondition12<A, B, C, D, E, F, G, H, I, J, K, L> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K, table12: L) -> Criteria?
typealias AddOnCondition13<A, B, C, D, E, F, G, H, I, J, K, L, M> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K, table12: L, table13: M) -> Criteria?
typealias AddOnCondition14<A, B, C, D, E, F, G, H, I, J, K, L, M, N> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K, table12: L, table13: M, table14: N) -> Criteria?
typealias AddOnCondition15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K, table12: L, table13: M, table14: N, table15: O) -> Criteria?
typealias AddOnCondition16<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J, table11: K, table12: L, table13: M, table14: N, table15: O, table16: P) -> Criteria?

