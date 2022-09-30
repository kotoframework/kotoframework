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
    dto: A,
    statement: Statement<A, SelectWhere<A>>
): SelectWhere<A> {
    return statement(dto)
}


inline fun <reified A : KPojo> table(
    dto: A,
    statement: Statement<A, Unit>
) {
    statement(dto)
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

inline fun <reified A : KPojo, reified B : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    statement: Statement2<A, B, AssociateWhere<A, B, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2)
}

inline fun <reified A : KPojo, reified B : KPojo> from(
    statement: Statement2<A, B, AssociateWhere<A, B, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(A::class.javaInstance(), B::class.javaInstance())
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    statement: Statement3<A, B, C, AssociateWhere<A, B, C, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo> from(
    statement: Statement3<A, B, C, AssociateWhere<A, B, C, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(A::class.javaInstance(), B::class.javaInstance(), C::class.javaInstance())
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    statement: Statement4<A, B, C, D, AssociateWhere<A, B, C, D, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo> from(
    statement: Statement4<A, B, C, D, AssociateWhere<A, B, C, D, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(A::class.javaInstance(), B::class.javaInstance(), C::class.javaInstance(), D::class.javaInstance())
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    statement: Statement5<A, B, C, D, E, AssociateWhere<A, B, C, D, E, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo> from(
    statement: Statement5<A, B, C, D, E, AssociateWhere<A, B, C, D, E, Unknown, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance()
    )
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    kPojo6: F,
    statement: Statement6<A, B, C, D, E, F, AssociateWhere<A, B, C, D, E, F, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo> from(
    statement: Statement6<A, B, C, D, E, F, AssociateWhere<A, B, C, D, E, F, Unknown, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, Unknown, Unknown, Unknown, Unknown> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance(),
        F::class.javaInstance()
    )
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    kPojo6: F,
    kPojo7: G,
    statement: Statement7<A, B, C, D, E, F, G, AssociateWhere<A, B, C, D, E, F, G, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo> from(
    statement: Statement7<A, B, C, D, E, F, G, AssociateWhere<A, B, C, D, E, F, G, Unknown, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, Unknown, Unknown, Unknown> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance(),
        F::class.javaInstance(),
        G::class.javaInstance()
    )
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    kPojo6: F,
    kPojo7: G,
    kPojo8: H,
    statement: Statement8<A, B, C, D, E, F, G, H, AssociateWhere<A, B, C, D, E, F, G, H, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, H, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo> from(
    statement: Statement8<A, B, C, D, E, F, G, H, AssociateWhere<A, B, C, D, E, F, G, H, Unknown, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, H, Unknown, Unknown> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance(),
        F::class.javaInstance(),
        G::class.javaInstance(),
        H::class.javaInstance()
    )
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo, reified I : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    kPojo6: F,
    kPojo7: G,
    kPojo8: H,
    kPojo9: I,
    statement: Statement9<A, B, C, D, E, F, G, H, I, AssociateWhere<A, B, C, D, E, F, G, H, I, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, H, I, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo, reified I : KPojo> from(
    statement: Statement9<A, B, C, D, E, F, G, H, I, AssociateWhere<A, B, C, D, E, F, G, H, I, Unknown>>
): AssociateWhere<A, B, C, D, E, F, G, H, I, Unknown> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance(),
        F::class.javaInstance(),
        G::class.javaInstance(),
        H::class.javaInstance(),
        I::class.javaInstance()
    )
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo, reified I : KPojo, reified J : KPojo> from(
    kPojo1: A,
    kPojo2: B,
    kPojo3: C,
    kPojo4: D,
    kPojo5: E,
    kPojo6: F,
    kPojo7: G,
    kPojo8: H,
    kPojo9: I,
    kPojo10: J,
    statement: Statement10<A, B, C, D, E, F, G, H, I, J, AssociateWhere<A, B, C, D, E, F, G, H, I, J>>
): AssociateWhere<A, B, C, D, E, F, G, H, I, J> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10)
}

inline fun <reified A : KPojo, reified B : KPojo, reified C : KPojo, reified D : KPojo, reified E : KPojo, reified F : KPojo, reified G : KPojo, reified H : KPojo, reified I : KPojo, reified J : KPojo> from(
    statement: Statement10<A, B, C, D, E, F, G, H, I, J, AssociateWhere<A, B, C, D, E, F, G, H, I, J>>
): AssociateWhere<A, B, C, D, E, F, G, H, I, J> {
    return statement(
        A::class.javaInstance(),
        B::class.javaInstance(),
        C::class.javaInstance(),
        D::class.javaInstance(),
        E::class.javaInstance(),
        F::class.javaInstance(),
        G::class.javaInstance(),
        H::class.javaInstance(),
        I::class.javaInstance(),
        J::class.javaInstance()
    )
}
