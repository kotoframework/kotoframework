package com.kotoframework.core.future

import com.kotoframework.beans.Unknown
import com.kotoframework.function.associate.AssociateWhere
import com.kotoframework.function.associate.javaInstance
import com.kotoframework.function.select.SelectWhere
import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/7/19 22:00
 */

inline fun <reified A : KPojo> from(
    KPojo: A,
    statement: Statement<A, SelectWhere<A>>
): SelectWhere<A> {
    return statement(KPojo)
}


inline fun <reified A : KPojo> table(
    kPojo: A,
    statement: Statement<A, Unit>
) {
    statement(kPojo)
}

inline fun <reified A : KPojo> from(
    statement: Statement<A, SelectWhere<A>>
): SelectWhere<A> {
    return statement(A::class.javaInstance())
}


inline fun <reified A : KPojo> table(
    statement: Statement<A, Unit>
) {
    statement(A::class.javaInstance())
}

/*
    * The function `from` is a function that takes two kPojo objects as parameters and returns a `AssociateWhere<T1, T2>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    statement: Statement2<T1, T2, AssociateWhere<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2)
}

/*
    * The function `from` is a function that takes three kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    statement: Statement3<T1, T2, T3, AssociateWhere<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3)
}

/*
    * The function `from` is a function that takes four kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    statement: Statement4<T1, T2, T3, T4, AssociateWhere<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4)
}

/*
    * The function `from` is a function that takes five kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    statement: Statement5<T1, T2, T3, T4, T5, AssociateWhere<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5)
}

/*
    * The function `from` is a function that takes six kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    statement: Statement6<T1, T2, T3, T4, T5, T6, AssociateWhere<T1, T2, T3, T4, T5, T6, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6)
}

/*
    * The function `from` is a function that takes seven kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    statement: Statement7<T1, T2, T3, T4, T5, T6, T7, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7)
}

/*
    * The function `from` is a function that takes eight kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    statement: Statement8<T1, T2, T3, T4, T5, T6, T7, T8, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8)
}

/*
    * The function `from` is a function that takes nine kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    statement: Statement9<T1, T2, T3, T4, T5, T6, T7, T8, T9, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9)
}

/*
    * The function `from` is a function that takes ten kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    statement: Statement10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10)
}

/*
    * The function `from` is a function that takes eleven kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    statement: Statement11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11)
}

/*
    * The function `from` is a function that takes twelve kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    kPojo12: T12 = T12::class.javaInstance(),
    statement: Statement12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12)
}

/*
    * The function `from` is a function that takes thirteen kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    kPojo12: T12 = T12::class.javaInstance(),
    kPojo13: T13 = T13::class.javaInstance(),
    statement: Statement13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Unknown, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, Unknown, Unknown, Unknown> {
    return statement(
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
        kPojo13
    )
}

/*
    * The function `from` is a function that takes fourteen kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    kPojo12: T12 = T12::class.javaInstance(),
    kPojo13: T13 = T13::class.javaInstance(),
    kPojo14: T14 = T14::class.javaInstance(),
    statement: Statement14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Unknown, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Unknown, Unknown> {
    return statement(
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
        kPojo14
    )
}

/*
    * The function `from` is a function that takes fifteen kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    kPojo12: T12 = T12::class.javaInstance(),
    kPojo13: T13 = T13::class.javaInstance(),
    kPojo14: T14 = T14::class.javaInstance(),
    kPojo15: T15 = T15::class.javaInstance(),
    statement: Statement15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Unknown>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, Unknown> {
    return statement(
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

/*
    * The function `from` is a function that takes sixteen kPojo objects as parameters and returns a `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>` object
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo, reified T6 : KPojo, reified T7 : KPojo, reified T8 : KPojo, reified T9 : KPojo, reified T10 : KPojo, reified T11 : KPojo, reified T12 : KPojo, reified T13 : KPojo, reified T14 : KPojo, reified T15 : KPojo, reified T16 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    kPojo6: T6 = T6::class.javaInstance(),
    kPojo7: T7 = T7::class.javaInstance(),
    kPojo8: T8 = T8::class.javaInstance(),
    kPojo9: T9 = T9::class.javaInstance(),
    kPojo10: T10 = T10::class.javaInstance(),
    kPojo11: T11 = T11::class.javaInstance(),
    kPojo12: T12 = T12::class.javaInstance(),
    kPojo13: T13 = T13::class.javaInstance(),
    kPojo14: T14 = T14::class.javaInstance(),
    kPojo15: T15 = T15::class.javaInstance(),
    kPojo16: T16 = T16::class.javaInstance(),
    statement: Statement16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>
): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
    return statement(
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
