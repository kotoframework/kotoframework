package com.kotoframework.function.associate

import com.kotoframework.beans.Unknown
import com.kotoframework.definition.Field
import com.kotoframework.definition.fd
import com.kotoframework.definition.aliasName
import com.kotoframework.interfaces.KPojo
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/5/30 17:51
 */
inline fun <reified T : KPojo, reified E : KPojo> associate(
    dto1: T? = T::class.javaInstance(), dto2: E? = E::class.javaInstance()
): AssociateAction<T, E, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo> associate(
    dto1: T? = T::class.javaInstance(), dto2: E? = E::class.javaInstance(), dto3: K? = K::class.javaInstance()
): AssociateAction<T, E, K, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance()
): AssociateAction<T, E, K, L, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance()
): AssociateAction<T, E, K, L, M, Unknown, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo, reified N : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance(),
    dto6: N? = N::class.javaInstance()
): AssociateAction<T, E, K, L, M, N, Unknown, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo, reified N : KPojo, reified O : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance(),
    dto6: N? = N::class.javaInstance(),
    dto7: O? = O::class.javaInstance()
): AssociateAction<T, E, K, L, M, N, O, Unknown, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo, reified N : KPojo, reified O : KPojo, reified P : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance(),
    dto6: N? = N::class.javaInstance(),
    dto7: O? = O::class.javaInstance(),
    dto8: P? = P::class.javaInstance()
): AssociateAction<T, E, K, L, M, N, O, P, Unknown, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo, reified N : KPojo, reified O : KPojo, reified P : KPojo, reified Q : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance(),
    dto6: N? = N::class.javaInstance(),
    dto7: O? = O::class.javaInstance(),
    dto8: P? = P::class.javaInstance(),
    dto9: Q? = Q::class.javaInstance()
): AssociateAction<T, E, K, L, M, N, O, P, Q, Unknown> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9)
}

inline fun <reified T : KPojo, reified E : KPojo, reified K : KPojo, reified L : KPojo, reified M : KPojo, reified N : KPojo, reified O : KPojo, reified P : KPojo, reified Q : KPojo, reified R : KPojo> associate(
    dto1: T? = T::class.javaInstance(),
    dto2: E? = E::class.javaInstance(),
    dto3: K? = K::class.javaInstance(),
    dto4: L? = L::class.javaInstance(),
    dto5: M? = M::class.javaInstance(),
    dto6: N? = N::class.javaInstance(),
    dto7: O? = O::class.javaInstance(),
    dto8: P? = P::class.javaInstance(),
    dto9: Q? = Q::class.javaInstance(),
    dto10: R? = R::class.javaInstance()
): AssociateAction<T, E, K, L, M, N, O, P, Q, R> {
    return AssociateAction(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10)
}

internal fun getSql(
    sql: String, field: Field, type: String, tableAlias: String, complex: Boolean = false
): String {
    val fieldAlias = if (complex) {
        "`$tableAlias${field.aliasName.replaceFirstChar { it.uppercase() }}`"
    } else {
        "`${field.aliasName}`"
    }
    return when {
        type == "date" -> "$sql DATE_FORMAT(`$tableAlias`.`${field.fd.columnName}`, '%Y-%m-%d') as $fieldAlias"
        type == "datetime" -> "$sql DATE_FORMAT(`$tableAlias`.`${field.fd.columnName}`, '%Y-%m-%d %H:%i:%s') as $fieldAlias"
        else -> {
            if (field.fd.columnName.contains("(") || field.fd.columnName.lowercase().contains(" as ")) {
                "$sql ${field.fd.columnName}"
            } else "$sql `$tableAlias`.`${field.fd.columnName}` as $fieldAlias"
        }
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> KClass<*>.javaInstance(): T {
    return this.java.newInstance() as T
}
