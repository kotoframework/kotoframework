package com.kotoframework.function.associate

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/5/24 15:12
 */

fun <T : KPojo, E : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E
): AssociateAction<T, E, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K
): AssociateAction<T, E, K, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M
): AssociateAction<T, E, K, M, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z
): AssociateAction<T, E, K, M, Z, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U
): AssociateAction<T, E, K, M, Z, U, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V
): AssociateAction<T, E, K, M, Z, U, V, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q
): AssociateAction<T, E, K, M, Z, U, V, Q, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I
): AssociateAction<T, E, K, M, Z, U, V, Q, I, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo, O : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L, kPojo12: O
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, O, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo, O : KPojo, P : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L, kPojo12: O, kPojo13: P
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, O, P, Unknown, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo, O : KPojo, P : KPojo, R : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L, kPojo12: O, kPojo13: P, kPojo14: R
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, O, P, R, Unknown, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo, O : KPojo, P : KPojo, R : KPojo, S : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L, kPojo12: O, kPojo13: P, kPojo14: R, kPojo15: S
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, O, P, R, S, Unknown> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14, kPojo15, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo, L : KPojo, O : KPojo, P : KPojo, R : KPojo, S : KPojo, A : KPojo> NamedParameterJdbcTemplate.associate(
    kPojo1: T, kPojo2: E, kPojo3: K, kPojo4: M, kPojo5: Z, kPojo6: U, kPojo7: V, kPojo8: Q, kPojo9: I, kPojo10: J, kPojo11: L, kPojo12: O, kPojo13: P, kPojo14: R, kPojo15: S, kPojo16: A
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J, L, O, P, R, S, A> {
    return AssociateAction(kPojo1, kPojo2, kPojo3, kPojo4, kPojo5, kPojo6, kPojo7, kPojo8, kPojo9, kPojo10, kPojo11, kPojo12, kPojo13, kPojo14, kPojo15, kPojo16, jdbcWrapper = this.wrapper())
}
