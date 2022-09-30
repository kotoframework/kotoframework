package com.kotoframework.function.associate

import com.kotoframework.beans.KotoResultSet
import com.kotoframework.definition.*
import com.kotoframework.*
import com.kotoframework.beans.Unknown
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.*
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Common.isAssignableFrom
import com.kotoframework.utils.Common.lineToHump
import com.kotoframework.utils.Common.rmRedudantBlk
import com.kotoframework.utils.Common.tableAlias
import com.kotoframework.utils.Common.tableName
import com.kotoframework.utils.Common.toMutableMap
import com.kotoframework.utils.Jdbc.dbName
import com.kotoframework.utils.Jdbc.getJdbcWrapper
import com.kotoframework.utils.Jdbc.joinSqlStatement
import com.kotoframework.utils.Jdbc.tableMap
import kotlin.reflect.KCallable

/**
 * Created by ousc on 2022/5/12 15:34
 */
class AssociateWhere<T : KPojo, E : KPojo, K : KPojo, M : KPojo, Z : KPojo, U : KPojo, V : KPojo, Q : KPojo, I : KPojo, J : KPojo>(
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
    private var onConditions: BaseCondition,
    private var tableNames: List<String>,
    var jdbcjdbcWrapper: KotoJdbcWrapper? = null
) {
    private var sql: String = ""
    private var fields: MutableList<KotoField> = mutableListOf()
    private var whereConditions: BaseCondition? = null
    private var finalMap: MutableMap<String, Any?> = mutableMapOf()
    private var joinType = "left"
    private var paramMaps: MutableMap<String, MutableMap<String, Any?>> = mutableMapOf()
    private var groupBy = ""
    private var orderBy = ""
    private var dtos = listOf<KPojo>()

    init {
        dtos = listOfNotNull(
            dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10
        )
        dtos.map {
            it.tableName to it.toMutableMap()
        }.forEach {
            this.paramMaps[it.first] = it.second
        }
    }

    private fun findOnByTableName(tableName: String): List<BaseCondition> {
        val conditions = mutableListOf<BaseCondition>()
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
        conditions.add(deleted(0, jdbcjdbcWrapper, tableName, "`${tableName.lineToHump()}`.").declareSql())
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

    fun right(): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        joinType = "right"
        return this
    }

    fun inner(): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        joinType = "inner"
        return this
    }

    fun cross(): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        joinType = "cross"
        return this
    }


    private var suffix: String = ""
    fun suffix(suffix: String): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        this.suffix = suffix
        return this
    }

    /**
     * It sets the pageIndex value to the finalMap.
     *
     * @param pageIndex The page number to retrieve.
     * @return Nothing.
     */
    fun page(pageIndex: Int, pageSize: Int): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        finalMap["pageIndex"] = pageIndex
        finalMap["pageSize"] = pageSize
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

    private fun groupOrOrderBy(type: String, vararg field: Field): String {
        var str = " $type by "
        field.forEach {
            val fieldName = getFieldName(it)
            val direction = if (type === "order") it.direction else ""
            str += "$fieldName $direction,"
        }
        return str.substring(0, str.length - 1)
    }

    fun orderBy(vararg field: Field): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        this.orderBy = groupOrOrderBy("order", *field)
        return this
    }

    fun groupBy(vararg field: Field): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        this.groupBy = groupOrOrderBy("group", *field)
        return this
    }

    /**
     * It takes a variable number of arguments and adds them to a map.
     *
     * @param pairs An array of key-value pairs.
     * @return A Where object
     */
    fun map(vararg pairs: Pair<String, Any?>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    fun select(vararg field: Field): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
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
        this.sql =
            "select ${
                fields.map { it.columnName }.joinToString(" , ")
            } from $mainTable as ${mainTable.lineToHump()}" + joinedTables.joinToString(
                " "
            ) {
                " $joinType join $it as ${it.lineToHump()} on ${
                    joinSqlStatement(
                        findOnByTableName(it), getParamMapByTableName(mainTable, it), true, showAlias = true
                    )
                }"
            }

        return this
    }

    private fun getFieldSql(field: Field): List<KotoField> {
        if (field is String) {
            return listOf(field.fd)
        } else if (field isAssignableFrom KPojo::class) {
            val tableName = (field as KPojo).tableName
            val tableAlias = field.tableAlias
            val fields = tableMap[getJdbcWrapper(jdbcjdbcWrapper).dbName + "_" + tableName]!!.fields

            return fields.map {
                it.fd.apply {
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
            val tbKey = getJdbcWrapper(jdbcjdbcWrapper).dbName + "_" + tableName

            return listOf(
                field.fd.apply {
                    columnName = getSql(
                        "",
                        field,
                        tableMap[tbKey]!!.fields.find { it.name == it.columnName }!!.type,
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
    fun complex(): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        this.complex = true
        return this
    }

    /**
     * > It returns a new instance of the same class, but with the `select` function called on it
     *
     * @return A `AssociateWhere` object.
     */
    fun select(): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(
            *listOfNotNull(
                dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8, dto9, dto10
            ).toTypedArray()
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
     * @return AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>
    */
     * @return A new instance of the class with the select fields added.
     */
    fun select(addSelect: AddSelectFields<T>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(*addSelect(dto1!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields3<T, E, K>
     * @return A new instance of the class with the select fields added.
     */
    fun select(
        addSelect:
        AddSelectFields2<T, E>
    ): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(*addSelect(dto1!!, dto2!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields3<T, E, K>
     * @return A new instance of the class with the select fields added.
     */
    fun select(addSelect: AddSelectFields3<T, E, K>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(*addSelect(dto1!!, dto2!!, dto3!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields4<T, E, K, M>
     * @return AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>
     */
    fun select(addSelect: AddSelectFields4<T, E, K, M>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(*addSelect(dto1!!, dto2!!, dto3!!, dto4!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields6<T, E, K, M, Z, U>
     * @return AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>
     */
    fun select(addSelect: AddSelectFields5<T, E, K, M, Z>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(
            *addSelect(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!).toTypedArray()
        )
    }

    fun select(addSelect: AddSelectFields6<T, E, K, M, Z, U>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(
            *addSelect(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields7<T, E, K, M, Z, U, V>
     * @return AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>
     */
    fun select(addSelect: AddSelectFields7<T, E, K, M, Z, U, V>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        /**
         * A function that takes in a function as a parameter and returns a value.
         *
         * @param addSelect AddSelectFields8<T, E, K, M, Z, U, V, Q>
         * @return A new instance of the class `AssociateWhere`
         */
        return this.select(
            *addSelect(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!).toTypedArray()
        )
    }

    fun select(addSelect: AddSelectFields8<T, E, K, M, Z, U, V, Q>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(*addSelect(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!).toTypedArray())
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect AddSelectFields9<T, E, K, M, Z, U, V, Q, I>
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields9<T, E, K, M, Z, U, V, Q, I>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(
            *addSelect(
                dto1!!,
                dto2!!,
                dto3!!,
                dto4!!,
                dto5!!,
                dto6!!,
                dto7!!,
                dto8!!,
                dto9!!
            ).toTypedArray()
        )
    }

    /**
     * A function that takes a function as a parameter.
     *
     * @param addSelect A lambda that takes in the 10 DTOs and returns a list of fields to select.
     * @return A new instance of the class `AssociateWhere`
     */
    fun select(addSelect: AddSelectFields10<T, E, K, M, Z, U, V, Q, I, J>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        return this.select(
            *addSelect(
                dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!, dto9!!, dto10!!
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

    fun where(condition: BaseCondition?): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
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
    fun where(addOnCondition: AddOnCondition<T>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!)
        return this
    }


    /**
     * `where` is a function that takes an `AddOnCondition2` as a parameter and returns an `AssociateWhere`
     *
     * @param addOnCondition A lambda function that takes in two DTOs and returns a list of conditions.
     * @return The return type is the next step in the fluent interface.
     */
    fun where(addOnCondition: AddOnCondition2<T, E>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!)
        return this
    }

    /**
     * `where` is a function that takes an `AddOnCondition3` as a parameter and returns an `AssociateWhere`
     *
     * @param addOnCondition A lambda function that takes in the three DTOs and returns a list of conditions.
     * @return The return type is the next step in the fluent interface.
     */
    fun where(addOnCondition: AddOnCondition3<T, E, K>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!)
        return this
    }

    /**
     * `fun where(addOnCondition: AddOnCondition4<T, E, K, M>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>`
     *
     * The function takes a lambda function as a parameter. The lambda function takes four parameters of type `T`, `E`,
     * `K`, and `M` respectively. The lambda function returns a `String` value. The function returns an object of type
     * `AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>`
    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 5 DTOs and returns a list of conditions.
     * @return The next step in the query.
    */
     *
     * @param addOnCondition A lambda function that takes in the 4 DTOs and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition4<T, E, K, M>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!)
        return this
    }

    fun where(addOnCondition: AddOnCondition5<T, E, K, M, Z>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!)
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 6 DTOs and returns a list of conditions.
     * @return The next step in the query builder.
     */
    fun where(addOnCondition: AddOnCondition6<T, E, K, M, Z, U>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!)
        return this
    }

    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 7 DTOs and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition7<T, E, K, M, Z, U, V>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!)
        return this
    }

    /**
     * A function that takes in an AddOnCondition8<T, E, K, M, Z, U, V, Q> and returns an AssociateWhere<T, E, K, M, Z, U,
     * V, Q, I, J>
     *
     * @param addOnCondition A lambda function that takes in the 8 DTOs and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition8<T, E, K, M, Z, U, V, Q>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!)
        return this
    }

    /**
     * `where` is a function that takes a lambda function as a parameter and returns an `AssociateWhere` object
     *
     * @param addOnCondition A lambda function that takes in the 9 DTOs and returns a list of conditions.
     * @return The next step in the query.
     */
    fun where(addOnCondition: AddOnCondition9<T, E, K, M, Z, U, V, Q, I>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions = addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!, dto9!!)
        return this
    }


    /**
     * A function that takes in a function as a parameter.
     *
     * @param addOnCondition A lambda function that takes in the 10 DTOs and returns a list of conditions.
     * @return The return type is `AssociateWhere<T, E, K, M, Z, U, V, Q, I, J>`
     */
    fun where(addOnCondition: AddOnCondition10<T, E, K, M, Z, U, V, Q, I, J>): AssociateWhere<T, E, K, M, Z, U, V, Q, I, J> {
        ifBlankSelectAll()
        whereConditions =
            addOnCondition(dto1!!, dto2!!, dto3!!, dto4!!, dto5!!, dto6!!, dto7!!, dto8!!, dto9!!, dto10!!)
        return this
    }


    /**
     * > If the `where` condition is not empty, then the `where` condition will be added to the `sql` statement, otherwise
     * the `on` condition will be added to the `sql` statement
     *
     * @return A KotoResultSet object.
     */
    fun build(): KotoResultSet<T> {
        ifBlankSelectAll()
        val onParamMap = mutableMapOf<String, Any?>()
        joinSqlStatement(
            listOf(
                onConditions
            ), onParamMap, true
        )
        val whereParamMap = mutableMapOf<String, Any?>()
        paramMaps.values.forEach { paramMap ->
            paramMap.forEach {
                whereParamMap[it.key] = it.value
            }
        }
        if (finalMap["pageSize"] != null && finalMap["pageIndex"] != null) {
            finalMap["pageIndex"] = (finalMap["pageIndex"] as Int - 1) * finalMap["pageSize"] as Int
            suffix = "$suffix limit :pageIndex,:pageSize"
            if (sql.isNotBlank()) {
                sql = sql.replaceFirst("select", "select SQL_CALC_FOUND_ROWS")
            }
        }

        return if (whereConditions == null) {
            whereParamMap.putAll(onParamMap)
            whereParamMap.putAll(finalMap)
            KotoResultSet("$sql $orderBy $suffix", whereParamMap, jdbcjdbcWrapper, Unknown::class)
        } else {
            val whereSql = joinSqlStatement(
                listOf(
                    whereConditions,
                    deleted(0, jdbcjdbcWrapper, dto1!!.tableName, "`${dto1!!.tableAlias}`.").declareSql()
                ), whereParamMap, true, showAlias = true
            )
            whereParamMap.putAll(onParamMap)
            whereParamMap.putAll(finalMap)
            KotoResultSet(
                "$sql where $whereSql $groupBy $orderBy $suffix".rmRedudantBlk(),
                whereParamMap,
                jdbcjdbcWrapper,
                Unknown::class
            )
        }
    }


    inline fun <reified T> queryForList(jdbcWrapper: KotoJdbcWrapper? = jdbcjdbcWrapper): List<T> {
        return this.build().queryForList<T>(jdbcWrapper)
    }

    fun query(jdbcWrapper: KotoJdbcWrapper? = jdbcjdbcWrapper): List<Map<String, Any>> {
        return this.build().query(jdbcWrapper)
    }

    inline fun <reified T> queryForObject(jdbcWrapper: KotoJdbcWrapper? = jdbcjdbcWrapper): T {
        return this.build().queryForObject<T>(jdbcWrapper)
    }

    inline fun <reified T> queryForObjectOrNull(jdbcWrapper: KotoJdbcWrapper? = jdbcjdbcWrapper): T? {
        return this.build().queryForObjectOrNull<T>(jdbcWrapper)
    }
}
