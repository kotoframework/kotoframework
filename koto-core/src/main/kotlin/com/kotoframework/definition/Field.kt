package com.kotoframework.definition

import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/5/30 16:10
 */

typealias Field = Any

typealias AddSelectFields<A> = (table1: A) -> List<Field>
typealias AddSelectFields2<A, B> = (table1: A, table2: B) -> List<Field>
typealias AddSelectFields3<A, B, C> = (table1: A, table2: B, table3: C) -> List<Field>
typealias AddSelectFields4<A, B, C, D> = (table1: A, table2: B, table3: C, table4: D) -> List<Field>
typealias AddSelectFields5<A, B, C, D, E> = (table1: A, table2: B, table3: C, table4: D, table5: E) -> List<Field>
typealias AddSelectFields6<A, B, C, D, E, F> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F) -> List<Field>
typealias AddSelectFields7<A, B, C, D, E, F, G> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G) -> List<Field>
typealias AddSelectFields8<A, B, C, D, E, F, G, H> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H) -> List<Field>
typealias AddSelectFields9<A, B, C, D, E, F, G, H, I> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I) -> List<Field>
typealias AddSelectFields10<A, B, C, D, E, F, G, H, I, J> = (table1: A, table2: B, table3: C, table4: D, table5: E, table6: F, table7: G, table8: H, table9: I, table10: J) -> List<Field>
