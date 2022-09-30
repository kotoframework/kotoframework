package com.kotoframework.function.associate

import com.kotoframework.PureJdbcWrapper.Companion.wrapper
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import javax.sql.DataSource

/**
 * Created by ousc on 2022/5/24 15:12
 */

fun <T : KPojo, E : KPojo> DataSource.associate(
    dto1: T, dto2: E
): AssociateAction<T, E, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K
): AssociateAction<T, E, K, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M
): AssociateAction<T, E, K, M, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z
): AssociateAction<T, E, K, M, Z, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z, dto6: U
): AssociateAction<T, E, K, M, Z, U, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z, dto6: U, dto7: V
): AssociateAction<T, E, K, M, Z, U, V, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z, dto6: U, dto7: V, dto8: Q
): AssociateAction<T, E, K, M, Z, U, V, Q, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z, dto6: U, dto7: V, dto8: Q, dto9: I
): AssociateAction<T, E, K, M, Z, U, V, Q, I, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, jdbcWrapper = this.wrapper())
}

fun <T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo> DataSource.associate(
    dto1: T, dto2: E, dto3: K, dto4: M, dto5: Z, dto6: U, dto7: V, dto8: Q, dto9: I, dto10: J
): AssociateAction<T, E, K, M, Z, U, V, Q, I, J> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10, jdbcWrapper = this.wrapper())
}
