package com.kotoframework.core.future

import com.kotoframework.beans.Unknown
import com.kotoframework.function.associate.AssociateWhere
import com.kotoframework.function.associate.associate
import com.kotoframework.function.associate.javaInstance
import com.kotoframework.function.select.SelectAction
import com.kotoframework.function.select.SelectWhere
import com.kotoframework.function.select.select
import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/7/19 22:00
 */

/**
 * From
 *
 * @param A
 * @param kPojo
 * @param statement
 * @return
 */
inline fun <reified A : KPojo> from(
    kPojo: A = A::class.javaInstance(),
    statement: Statement<A, SelectWhere<A>>
): SelectWhere<A> {
    return statement(kPojo)
}

/**
 * Table
 *
 * @param A
 * @param kPojo
 * @param statement
 * @return
 */
inline fun <reified A : KPojo> table(
    kPojo: A = A::class.javaInstance(),
    statement: Statement<A, Unit>
) {
    statement(kPojo)
}

/**
 * From
 *
 * @param A
 * @param statement
 * @return
 */
inline fun <reified A : KPojo> from(
    statement: Statement<A, SelectWhere<A>>
): SelectWhere<A> {
    return A::class.javaInstance<A>().let {
        statement(it)
    }
}

/**
 * Table
 *
 * @param A
 * @param KPojo
 * @param statement
 * @return
 */
inline fun <reified A : KPojo> table(
    statement: Statement<A, SelectWhere<A>>
) {
    statement(A::class.javaInstance())
}

/**
 * From
 *
 * @param T1
 * @param T2
 * @param kPojo1
 * @param kPojo2
 * @param statement
 * @return
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    statement: Statement2<T1, T2>
): AssociateWhere<T1, T2, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, associate(kPojo1, kPojo2))
}

/**
 * From
 *
 * @param T1
 * @param T2
 * @param T3
 * @param kPojo1
 * @param kPojo2
 * @param kPojo3
 * @param statement
 * @return
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    statement: Statement3<T1, T2, T3>
): AssociateWhere<T1, T2, T3, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, associate(kPojo1, kPojo2, kPojo3))
}

/**
 * From
 *
 * @param T1
 * @param T2
 * @param T3
 * @param T4
 * @param kPojo1
 * @param kPojo2
 * @param kPojo3
 * @param kPojo4
 * @param statement
 * @return
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    statement: Statement4<T1, T2, T3, T4>
): AssociateWhere<T1, T2, T3, T4, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, associate(kPojo1, kPojo2, kPojo3, kPojo4))
}

/**
 * From
 *
 * @param T1
 * @param T2
 * @param T3
 * @param T4
 * @param T5
 * @param kPojo1
 * @param kPojo2
 * @param kPojo3
 * @param kPojo4
 * @param kPojo5
 * @param statement
 * @return
 */
inline fun <reified T1 : KPojo, reified T2 : KPojo, reified T3 : KPojo, reified T4 : KPojo, reified T5 : KPojo> from(
    kPojo1: T1 = T1::class.javaInstance(),
    kPojo2: T2 = T2::class.javaInstance(),
    kPojo3: T3 = T3::class.javaInstance(),
    kPojo4: T4 = T4::class.javaInstance(),
    kPojo5: T5 = T5::class.javaInstance(),
    statement: Statement5<T1, T2, T3, T4, T5>
): AssociateWhere<T1, T2, T3, T4, T5, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return statement(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, associate(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5))
}
