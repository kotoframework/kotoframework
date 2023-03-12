package com.kotoframework.function.associate

import com.kotoframework.beans.KotoResultSet
import com.kotoframework.definition.*
import com.kotoframework.*
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.*
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Common.smartPagination
import com.kotoframework.utils.Extension.isAssignableFrom
import com.kotoframework.utils.Extension.isNullOrEmpty
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.rmRedundantBlk
import com.kotoframework.utils.Extension.tableAlias
import com.kotoframework.utils.Extension.tableName
import com.kotoframework.utils.Extension.toMutableMap
import com.kotoframework.utils.Jdbc.joinSqlStatement
import com.kotoframework.utils.Jdbc.tableMap
import com.kotoframework.utils.Jdbc.tableMetaKey
import kotlin.reflect.KCallable

/**
 * Created by ousc on 2022/5/12 15:34
 */
class AssociateWhere<T1 : KPojo, T2 : KPojo, T3 : KPojo, T4 : KPojo, T5 : KPojo, T6 : KPojo, T7 : KPojo, T8 : KPojo, T9 : KPojo, T10 : KPojo, T11 : KPojo, T12 : KPojo, T13 : KPojo, T14 : KPojo, T15 : KPojo, T16 : KPojo>(
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
    private var onConditions: Criteria,
    private var tableNames: List<String>,
    var kotoJdbcWrapper: KotoJdbcWrapper? = null
) {
    private var sql: String = ""
    private var fields: MutableList<ColumnMeta> = mutableListOf()
    private var whereConditions: Criteria? = null
    private var finalMap: MutableMap<String, Any?> = mutableMapOf()
    private var joinType = "left"
    private var paramMaps: MutableMap<String, MutableMap<String, Any?>> = mutableMapOf()
    private var groupBy = ""
    private var orderBy = ""
    private var limit: Int? = null
    private var offset: Int? = null
    private var pageIndex: Int? = null
    private var pageSize: Int? = null
    private var limitOne: Boolean = false
    private var kPojos = listOf<KPojo>()


    init {
        kPojos = listOfNotNull(
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
        kPojos.map {
            it.tableName to it.toMutableMap()
        }.forEach {
            this.paramMaps[it.first] = it.second
        }
    }

    private fun findOnByTableName(tableName: String): List<Criteria> {
        val conditions = mutableListOf<Criteria>()
        if (onConditions.type == AND) {
            onConditions.collections.filterNotNull().forEach {
                if (it.tableName == tableName) {
                    conditions.add(it)
                }
            }
        } else {
            if (onConditions.tableName == tableName) {
                conditions.add(onConditions)
            }
        }
        conditions.add(deleted(0, kotoJdbcWrapper, tableName, "`${tableName.lineToHump()}`.").declareSql())
        return conditions
    }

    private fun getParamMapByTableName(a: String, b: String): MutableMap<String, Any?> {
        val paramMap = mutableMapOf<String, Any?>()
        if (paramMaps.containsKey(a)) {
            paramMap.putAll(paramMaps[a]!!)
        }
        if (paramMaps.containsKey(b)) {
            paramMap.putAll(paramMaps[b]!!)
        }
        return paramMap
    }

    fun right(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        joinType = "right"
        return this
    }

    fun inner(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        joinType = "inner"
        return this
    }

    fun cross(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        joinType = "cross"
        return this
    }


    private var suffix: String = ""
    fun suffix(suffix: String): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.suffix = suffix
        return this
    }

    /**
     * It sets the pageIndex value to the finalMap.
     *
     * @param pageIndex The page number to retrieve.
     * @return Nothing.
     */
    fun page(
        pageIndex: Int,
        pageSize: Int
    ): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        return this
    }

    fun limit(
        limit: Int,
        offset: Int? = null
    ): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.limit = limit
        this.offset = offset
        return this
    }

    fun first(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.limitOne = true
        return this
    }

    private var ifNoValueStrategy: (Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }
    fun ifNoValue(strategy: (Criteria) -> NoValueStrategy = { KotoApp.defaultNoValueStrategy }): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.ifNoValueStrategy = strategy
        return this
    }

    private fun getFieldName(field: Field): String {
        return when (field) {
            is String -> field
            is KCallable<*> -> "`${field.receiver.tableAlias}`.`${field.columnName}`"
            is Pair<*, *> -> getFieldName(field.first as Field)
            else -> throw IllegalArgumentException("field type error")
        }
    }

    private fun groupOrOrderBy(type: String, vararg field: Field?): String {
        if (field.none { !it.isNullOrEmpty() }) {
            return ""
        }
        var str = " $type by "
        field.forEach {
            if (it.isNullOrEmpty()) return@forEach
            val fieldName = getFieldName(it!!)
            val direction = if (type === "order") it.direction else ""
            str += "$fieldName $direction,"
        }
        return str.substring(0, str.length - 1)
    }

    fun orderBy(vararg field: Field?): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.orderBy = groupOrOrderBy("order", *field)
        return this
    }

    fun groupBy(vararg field: Field?): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.groupBy = groupOrOrderBy("group", *field)
        return this
    }

    /**
     * It takes a variable number of arguments and adds them to a map.
     *
     * @param pairs An array of key-value pairs.
     * @return A Where object
     */
    fun map(vararg pairs: Pair<String, Any?>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    fun select(vararg field: Field): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        val fieldDistinct = field.map { getFieldSql(it) }.flatten().distinctBy { it.columnName }
        fieldDistinct.forEach { kf ->
            if (fields.find { it.columnName.contains("as `${kf.aliasName}`") } != null) {
                var newFieldAlias = "${kf.aliasName}@"
                while (fields.any { it.columnName.contains("as `$newFieldAlias`") }) {
                    newFieldAlias = newFieldAlias.replaceFirst("@", "@@")
                }
                fields.add(kf.apply {
                    columnName = columnName.replace("as `${kf.aliasName}`", "as `$newFieldAlias`")
                })
            } else {
                fields.add(kf)
            }
        }
        val mainTable = tableNames.first()
        val joinedTables = tableNames.slice(1 until tableNames.size)
        sql =
            "select ${
                fields.joinToString(" , ") { it.columnName }
            } from $mainTable as ${mainTable.lineToHump()}" + joinedTables.joinToString(
                " "
            ) {
                " $joinType join $it as ${it.lineToHump()} on ${
                    joinSqlStatement(
                        findOnByTableName(it),
                        getParamMapByTableName(mainTable, it),
                        ifNoValueStrategy,
                        showAlias = true
                    )
                }"
            }

        return this
    }

    private fun getFieldSql(field: Field): List<ColumnMeta> {
        if (field is String) {
            return listOf(field.toColumn())
        } else if (field isAssignableFrom KPojo::class) {
            val tableName = (field as KPojo).tableName
            val tableAlias = field.tableAlias
            val fields = tableMap[tableMetaKey(kotoJdbcWrapper, tableName)]!!.fields

            return fields.map {
                it.toColumn().apply {
                    columnName = getSql(
                        "",
                        it.name.lineToHump(),
                        it.type,
                        tableAlias,
                        complex
                    )
                }
            }
        } else {
            val (tableName, tableAlias) = when (field) {
                is KCallable<*> -> {
                    Pair(
                        field.receiver.tableName,
                        field.receiver.tableAlias
                    )
                }

                is Pair<*, *> -> {
                    if (field.first is KCallable<*>) {
                        Pair(
                            (field.first as KCallable<*>).receiver.tableName,
                            (field.first as KCallable<*>).receiver.tableAlias
                        )
                    } else {
                        throw IllegalArgumentException("The first argument must be KCallable<*>")
                    }
                }

                else -> {
                    throw IllegalArgumentException("${field.javaClass.simpleName} is not a KPojo")
                }
            }

            return listOf(
                field.toColumn().apply {
                    columnName = getSql(
                        "",
                        field,
                        tableMap[tableMetaKey(
                            kotoJdbcWrapper,
                            tableName
                        )]!!.fields.find { it.name == it.columnName }!!.type,
                        tableAlias,
                        complex
                    )
                }
            )
        }
    }

    private var complex = false

    /**
     * A function that returns an object of type AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>
     *
     * @return The return type is the same as the class type.
     */
    @Deprecated("this method is deprecated, use Pair<KCallable<*>, String> instead")
    fun complex(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        this.complex = true
        return this
    }

    /**
     * > It returns a new instance of the same class, but with the `select` function called on it
     *
     * @return A `AssociateWhere` object.
     */
    fun select(): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *kPojos.toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields<T>
    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields2<T, E>
     * @return AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
    */
     * @return A new instance of the class with the select fields added.
     */
    fun select(addSelect: AddSelectFields<T1>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(*addSelect(kPojo1!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields3<T, E, K>
     * @return A new instance of the class with the select fields added.
     */
    fun select(
        addSelect:
        AddSelectFields2<T1, T2>
    ): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(*addSelect(kPojo1!!, kPojo2!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields3<T, E, K>
     * @return A new instance of the class with the select fields added.
     */
    fun select(addSelect: AddSelectFields3<T1, T2, T3>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(*addSelect(kPojo1!!, kPojo2!!, kPojo3!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields4<T, E, K, M>
     * @return AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
     */
    fun select(addSelect: AddSelectFields4<T1, T2, T3, T4>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(*addSelect(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields6<T, E, K, M, Z, U>
     * @return AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
     */
    fun select(addSelect: AddSelectFields5<T1, T2, T3, T4, T5>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!).toTypedArray()
        )
    }

    fun select(addSelect: AddSelectFields6<T1, T2, T3, T4, T5, T6>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields7<T, E, K, M, Z, U, V>
     * @return AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
     */
    fun select(addSelect: AddSelectFields7<T1, T2, T3, T4, T5, T6, T7>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        /**
         * A function that takes in a function as a parameter and returns a value.
         *
         * @param addSelect AddSelectFields8<T, E, K, M, Z, U, V, Q>
         * @return A new instance of the class `AssociateWhere`
         */
        return this.select(
            *addSelect(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!, kPojo7!!).toTypedArray()
        )
    }

    fun select(addSelect: AddSelectFields8<T1, T2, T3, T4, T5, T6, T7, T8>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields9<T, E, K, M, Z, U, V, Q, I>
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
                kPojo1!!,
                kPojo2!!,
                kPojo3!!,
                kPojo4!!,
                kPojo5!!,
                kPojo6!!,
                kPojo7!!,
                kPojo8!!,
                kPojo9!!
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 10 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
                kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!, kPojo7!!, kPojo8!!, kPojo9!!, kPojo10!!
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 11 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 12 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 13 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 14 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 15 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 16 kPojos and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        return this.select(
            *addSelect(
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
                kPojo16!!
            ).toTypedArray()
        )
    }

    /**
     * If the sql is blank, select all
     */
    private fun ifBlankSelectAll() {
        if (sql.isBlank()) {
            this.select()
        }
    }

    fun where(condition: Criteria?): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = condition!!
        return this
    }

    /**
     * `where` is a function that takes an `AddOnCondition` as a parameter and returns an `AssociateWhere`
     *
     * @param addOnCondition A lambda that takes in the first KPojo and returns a list of conditions.
     * @return The return type is the same as the class type.
     */
    fun where(addOnCondition: AddOnCondition<T1>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!)
        return this
    }


    /**
     * `where` is a function that takes an `AddOnCondition2` as a parameter and returns an `AssociateWhere`
     *
     * @param addOnCondition A lambda function that takes in two kPojos and returns a list of conditions.
     * @return The return type is the next step in the fluent interface.
     */
    fun where(addOnCondition: AddOnCondition2<T1, T2>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!)
        return this
    }

    /**
     * `where` is a function that takes an `AddOnCondition3` as a parameter and returns an `AssociateWhere`
     *
     * @param addOnCondition A lambda function that takes in the three kPojos and returns a list of conditions.
     * @return The return type is the next step in the fluent interface.
     */
    fun where(addOnCondition: AddOnCondition3<T1, T2, T3>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!)
        return this
    }

    /**
     * `fun where(addOnCondition: AddOnCondition4<T, E, K, M>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>`
     *
     * The function takes a lambda function as a parameter. The lambda function takes four parameters of type `T`, `E`,
     * `K`, and `M` respectively. The lambda function returns a `String` value. The function returns an object of type
     * `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>`
     *
     * @param addOnCondition A lambda function that takes in the 4 kPojos and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition4<T1, T2, T3, T4>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!)
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 5 kPojos and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition5<T1, T2, T3, T4, T5>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!)
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 6 kPojos and returns a list of conditions.
     * @return The next step in the query builder.
     */
    fun where(addOnCondition: AddOnCondition6<T1, T2, T3, T4, T5, T6>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!)
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 7 kPojos and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition7<T1, T2, T3, T4, T5, T6, T7>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!, kPojo7!!)
        return this
    }

    /**
     * A function that takes in an AddOnCondition8<T, E, K, M, Z, U, V, Q> and returns an AssociateWhere<T, E, K, M, Z, U,
     * V, Q, I, J>
     *
     * @param addOnCondition A lambda function that takes in the 8 kPojos and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition8<T1, T2, T3, T4, T5, T6, T7, T8>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!, kPojo7!!, kPojo8!!)
        return this
    }

    /**
     * `where` is a function that takes a lambda function as a parameter and returns an `AssociateWhere` object
     *
     * @param addOnCondition A lambda function that takes in the 9 kPojos and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
            addOnCondition(kPojo1!!, kPojo2!!, kPojo3!!, kPojo4!!, kPojo5!!, kPojo6!!, kPojo7!!, kPojo8!!, kPojo9!!)
        return this
    }


    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>`
     */
    fun where(addOnCondition: AddOnCondition10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 11 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>`
     */
    fun where(addOnCondition: AddOnCondition11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 12 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>`
     */
    fun where(addOnCondition: AddOnCondition12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 13 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>`
     */
    fun where(addOnCondition: AddOnCondition13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 14 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>`
     */
    fun where(addOnCondition: AddOnCondition14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 15 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>`
     */
    fun where(addOnCondition: AddOnCondition15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
            )
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 16 kPojos and returns a list of conditions.
     * @return The return type is `AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>`
     */
    fun where(addOnCondition: AddOnCondition16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>): AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {
        ifBlankSelectAll()
        whereConditions =
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
                kPojo16!!
            )
        return this
    }


    /**
     * > If the `where` condition is not empty, then the `where` condition will be added to the `sql` statement, otherwise
     * the `on` condition will be added to the `sql` statement
     *
     * @return A KotoResultSet object.
     */
    fun build(): KotoResultSet<T1> {
        ifBlankSelectAll()
        val onParamMap = mutableMapOf<String, Any?>()
        joinSqlStatement(
            listOf(
                onConditions
            ), onParamMap, ifNoValueStrategy
        )
        val whereParamMap = mutableMapOf<String, Any?>()
        paramMaps.values.forEach { paramMap ->
            paramMap.forEach {
                whereParamMap[it.key] = it.value
            }
        }

        var (paginatedSql, paginatedSuffix, paginatedLimit, paginatedOffset) = smartPagination(
            sql,
            suffix,
            pageIndex,
            pageSize
        )

        limit = paginatedLimit ?: limit

        offset = paginatedOffset ?: offset

        if (limitOne) paginatedSuffix = "$paginatedSuffix limit 1"

        if (limit != null) paginatedSuffix = "$paginatedSuffix limit $limit"

        if (offset != null) paginatedSuffix = "$paginatedSuffix offset $offset"


        return if (whereConditions == null) {
            whereParamMap.putAll(onParamMap)
            whereParamMap.putAll(finalMap)
            KotoResultSet("$paginatedSql $orderBy $paginatedSuffix", whereParamMap, kotoJdbcWrapper, Unknown::class)
        } else {
            val whereSql = joinSqlStatement(
                listOf(
                    whereConditions,
                    deleted(0, kotoJdbcWrapper, kPojo1!!.tableName, "`${kPojo1!!.tableAlias}`.").declareSql()
                ), whereParamMap, ifNoValueStrategy, showAlias = true
            )
            whereParamMap.putAll(onParamMap)
            whereParamMap.putAll(finalMap)
            KotoResultSet(
                "$paginatedSql where $whereSql $groupBy $orderBy $paginatedSuffix".rmRedundantBlk(),
                whereParamMap,
                kotoJdbcWrapper,
                Unknown::class
            )
        }
    }


    inline fun <reified T> queryForList(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<T> {
        return this.build().queryForList<T>(jdbcWrapper)
    }

    fun query(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): List<Map<String, Any>> {
        return this.build().query(jdbcWrapper)
    }

    inline fun <reified T> queryForObject(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T {
        return this.build().queryForObject<T>(jdbcWrapper)
    }

    inline fun <reified T> queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): T? {
        return this.build().queryForObjectOrNull<T>(jdbcWrapper)
    }

    fun <K> withTotal(action: (AssociateWhere<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>) -> K): Pair<K, Int> {
        return action(this) to build().let { KotoResultSet.getTotalCount(kotoJdbcWrapper, it.sql, it.paramMap) }
    }
}
