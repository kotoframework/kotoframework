package com.kotoframework.function.associate

import com.kotoframework.definition.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.BaseCondition
import com.kotoframework.utils.Common.tableMeta
import com.kotoframework.utils.Common.tableName
import com.kotoframework.utils.Jdbc.initMetaData

/**
 * Created by ousc on 2022/5/12 09:20
 */
class AssociateAction<T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo>(
    private var dto1: T? = null,
    private var dto2: E? = null,
    private var dto3: K? = null,
    private var dto4: M? = null,
    private var dto5: Z? = null,
    private var dto6: U? = null,
    private var dto7: V? = null,
    private var dto8: Q? = null,
    private var dto9: I? = null,
    private var dto10: J? = null,
    private val jdbcWrapper: KotoJdbcWrapper? = null,
) {
    /**
     * > It gets the table names from the DTOs and initializes the metadata for each table
     *
     * @return A list of table names.
     */
    private fun getTableNames(): List<String> {
        val dtos = listOfNotNull(
            dto1,
            dto2,
            dto3,
            dto4,
            dto5,
            dto6,
            dto7,
            dto8,
            dto9,
            dto10
        )
        dtos.forEach {
            initMetaData(it.tableMeta, jdbcWrapper)
        }
        return dtos.map { it.tableName }
    }

    fun on(condition: BaseCondition?): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            condition!!, getTableNames(), jdbcWrapper
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 DTOs and returns a string.
     * @return The return type is `AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>`
     */
    fun on(addOnCondition: AddOnCondition10<T, E, K, M, Z, U, V, Q, I, J>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!, dto9!!, dto10!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * A function that takes an AddOnCondition9 as a parameter and returns an AssociateWhere.
     *
     * @param addOnCondition A lambda function that takes in the KPojo's and returns a Where object.
     * @return The return type is `AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>`
     */
    fun on(addOnCondition: AddOnCondition9<T, E, K, M, Z, U, V, Q, I>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!, dto9!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * A function that takes a function as a parameter and returns an object of type AssociateWhere.
     *
     * @param addOnCondition A lambda function that takes in the DTOs and returns a Where object.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition8<T, E, K, M, Z, U, V, Q>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * A function that takes an AddOnCondition7 as a parameter and returns an AssociateWhere.
     *
     * @param addOnCondition A lambda function that takes in the DTOs and returns a Where object.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition7<T, E, K, M, Z, U, V>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * A function that takes an AddOnCondition6 as a parameter and returns an AssociateWhere.
     *
     * @param addOnCondition A lambda function that takes in the DTOs and returns a string.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition6<T, E, K, M, Z, U>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!)!!,
            getTableNames(), jdbcWrapper
        )
    }

    /**
     * It returns an object of type AssociateWhere.
     *
     * @param addOnCondition A lambda function that takes in the DTOs and returns a Where object.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition5<T, E, K, M, Z>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * A function that takes an AddOnCondition4<T, E, K, M> as a parameter and returns an AssociateWhere<T, E, K, M, Z, U,
     * V, Q, I, J>
     *
     * @param addOnCondition A lambda that takes in the DTOs and returns a Where object.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition4<T, E, K, M>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * It returns an object of type AssociateWhere.
     *
     * @param addOnCondition A lambda that takes in the DTOs and returns a Where object.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition3<T, E, K>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!, dto3!!)!!,
            getTableNames(), jdbcWrapper
        )
    }


    /**
     * It returns an object of type AssociateWhere.
     *
     * @param addOnCondition A lambda that takes in the two DTOs and returns a WhereCondition.
     * @return A new instance of the class `AssociateWhere`
     */
    fun on(addOnCondition: AddOnCondition2<T, E>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return AssociateWhere(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10,
            addOnCondition(dto1!!, dto2!!)!!,
            getTableNames(), jdbcWrapper
        )
    }
}
