package com.kotoframework.function.associate

import com.kotoframework.definition.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.Criteria
import com.kotoframework.utils.Extension.tableMeta
import com.kotoframework.utils.Extension.tableName
import com.kotoframework.utils.Jdbc.initMetaData

/**
 * Created by ousc on 2022/5/12 09:20
 */
class AssociateAction<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo, T15 : KPojo, T16 : KPojo>(
    private var kPojo1: T1? = null,
    private var kPojo2: T2? = null,
    private var kPojo3: T3? = null,
    private var kPojo4: T4? = null,
    private var kPojo5: T5? = null,
    private var kPojo6: T6? = null,
    private var kPojo7: T7? = null,
    private var kPojo8: T8? = null,
    private var kPojo9: T9? = null,
    private var kPojo10: T10? = null,
    private var kPojo11: T11? = null,
    private var kPojo12: T12? = null,
    private var kPojo13: T13? = null,
    private var kPojo14: T14? = null,
    private var kPojo15: T15? = null,
    private var kPojo16: T16? = null,
    private val jdbcWrapper: KotoJdbcWrapper? = null,
) {
    /**
     * > It gets the table names from the kPojos and initializes the metadata for each table
     *
     * @return A list of table names.
     */
    private fun getTableNames(): List<String> {
        val kPojos = listOfNotNull(
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
        kPojos.forEach {
            initMetaData(it.tableMeta, jdbcWrapper)
        }
        return kPojos.map { it.tableName }
    }

    fun on(condition: Criteria?): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            condition!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 16 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>`
     */
    fun on(addOnCondition: AddOnCondition16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!,
                kPojo12!!,
                kPojo13!!,
                kPojo14!!,
                kPojo15!!,
                kPojo16!!,
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>`
     */
    fun on(addOnCondition: AddOnCondition15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!,
                kPojo12!!,
                kPojo13!!,
                kPojo14!!,
                kPojo15!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>`
     */
    fun on(addOnCondition: AddOnCondition14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!,
                kPojo12!!,
                kPojo13!!,
                kPojo14!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>`
     */
    fun on(addOnCondition: AddOnCondition13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!,
                kPojo12!!,
                kPojo13!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>`
     */
    fun on(addOnCondition: AddOnCondition12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!,
                kPojo12!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>`
     */
    fun on(addOnCondition: AddOnCondition11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!,
                kPojo11!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>`
     */
    fun on(addOnCondition: AddOnCondition10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!,
                kPojo10!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 9 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9>`
     */
    fun on(addOnCondition: AddOnCondition9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 8 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8>`
     */
    fun on(addOnCondition: AddOnCondition8<T1, T2, T3, T4, T5, T6, T7, T8>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 7 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7>`
     */
    fun on(addOnCondition: AddOnCondition7<T1, T2, T3, T4, T5, T6, T7>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 6 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6>`
     */
    fun on(addOnCondition: AddOnCondition6<T1, T2, T3, T4, T5, T6>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 5 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5>`
     */
    fun on(addOnCondition: AddOnCondition5<T1, T2, T3, T4, T5>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 4 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4>`
     */
    fun on(addOnCondition: AddOnCondition4<T1, T2, T3, T4>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 3 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2, T3>`
     */
    fun on(addOnCondition: AddOnCondition3<T1, T2, T3>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 2 kPojos and returns a string.
     * @return The return type is `AssociateWhere<T1, T2>`
     */
    fun on(addOnCondition: AddOnCondition2<T1, T2>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return AssociateWhere(
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
            kPojo16,
            addOnCondition(
                kPojo1!!,
                kPojo2!!
            )!!,
            getTableNames(),
            jdbcWrapper
        )
    }
}
