package com.kotoframework.function.create

import com.kotoframework.core.condition.eq
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.Where
import com.kotoframework.function.update.update as updateKoto
import com.kotoframework.*
import com.kotoframework.KotoApp.dbType
import com.kotoframework.definition.*
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.annotations.Default
import com.kotoframework.core.annotations.NeedTableIndexes
import com.kotoframework.core.condition.alias
import com.kotoframework.core.condition.arbitrary
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common.deleted
import com.kotoframework.utils.Common.currentTime
import com.kotoframework.utils.Common.getColumnName
import com.kotoframework.utils.Extension.isNullOrEmpty
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.rmRedundantBlk
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

/**
 * Created by ousc on 2022/5/10 02:42
 */
class CreateWhere<T : KPojo>(KPojo: T, kotoJdbcWrapper: KotoJdbcWrapper?) : Where<T>(KPojo, kotoJdbcWrapper) {
    private var updateFields: List<KotoField> = mutableListOf()
    private var onFields: List<KotoField> = mutableListOf()
    private var replaceInto = false
    private var duplicateUpdate = false

    @NeedTableIndexes
    fun onDuplicateUpdate(vararg fields: Field): CreateWhere<T> {
        if (fields.isEmpty()) {
            this.duplicateUpdate = false
            this.replaceInto = true
            this.updateFields = KPojo::class.declaredMemberProperties.map { it.fd }
            return this
        } else {
            this.replaceInto = false
            this.duplicateUpdate = true
            this.updateFields = fields.map { it.fd }
            return this
        }
    }

    @OptIn(NeedTableIndexes::class)
    fun onId(): CreateWhere<T> {
        return this.onDuplicateUpdate()
    }

    fun on(field: Field, vararg fields: Field): CreateWhere<T> {
        this.duplicateUpdate = false
        this.replaceInto = false
        this.onFields = fields.map { it.fd }.toMutableList().apply { add(field.fd) }
        this.updateFields = KPojo::class.declaredMemberProperties.map { it.fd }
        return this
    }

    fun update(field: Field, vararg fields: Field): CreateWhere<T> {
        if (this.replaceInto) {
            this.replaceInto = false
            this.duplicateUpdate = true
        }
        this.updateFields = fields.map { it.fd }.toMutableList().apply { add(field.fd) }.distinct()
        return this
    }

    fun except(field: Field, vararg fields: Field): CreateWhere<T> {
        if (this.replaceInto) {
            this.replaceInto = false
            this.duplicateUpdate = true
        }
        val exceptFields = fields.toMutableList().apply { add(field) }.distinct()
        this.updateFields =
            this.updateFields.filter { update -> !exceptFields.any { except -> update.columnName == except.columnName } }
                .toMutableList()
        return this
    }

    /**
     * It takes a variable number of arguments and returns a map.
     *
     * @param pairs A vararg of Pair<String, Any?>
     * @return The Creation object itself.
     */
    override fun map(vararg pairs: Pair<String, Any?>): CreateWhere<T> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    /**
     * A function that generates a SQL statement.
     *
     * @return A Koto object
     */
    override fun build(): KotoOperationSet<CreateWhere<T>, T> {
        KPojo::class.declaredMemberProperties.forEach {
            conditions.add(it.columnName.lineToHump().eq().alias(it.name))
        }

        conditions.add("updateTime".eq())
        conditions.add("createTime".eq())
        paramMap["updateTime"] = currentTime
        paramMap["createTime"] = currentTime

        conditions = conditions.filterNotNull().distinctBy { it.reName }.toMutableList()

        conditions.forEach {
            val realName = when {
                !it!!.reName.isNullOrBlank() -> it.reName
                else -> it.parameterName
            }!!
            when (it.type) {
                EQUAL -> {
                    if (!it.value.isNullOrEmpty()) {
                        paramMap[realName] = it.value
                    }
                }

                else -> {}
            }
        }

        val (paramNames, reNames) = conditions.filter { it!!.type == EQUAL }
            .map { getColumnName(it!!) to it.reName }.unzip()

        KPojo::class.declaredMemberProperties.forEach {
            val default = it.findAnnotation<Default>()?.value
            if (paramMap[it.name] == null && default != null) {
                paramMap[it.name] = default
            }
        }

        for ((key, value) in finalMap) {
            paramMap[key] = value
        }

        if (replaceInto) {
            return KotoOperationSet(kotoJdbcWrapper,
                "replace into $tableName (`${paramNames.joinToString("`,`")}`) values (${
                    reNames.joinToString(
                        ","
                    ) { ":$it" }
                }) ".rmRedundantBlk(),
                paramMap)
        } else if (duplicateUpdate) {
            return KotoOperationSet(kotoJdbcWrapper,
                "insert into $tableName (`${paramNames.joinToString("`,`")}`) values (${
                    reNames.joinToString(
                        ","
                    ) { ":$it" }
                }) on duplicate key update ${
                    updateFields.joinToString(",") {
                        "`${it.columnName}` = :${it.propertyName}"
                    }
                } ".rmRedundantBlk(),
                paramMap)
        } else if (onFields.isNotEmpty()) {
            return when (dbType) {
                MySql -> KotoOperationSet(
                    kotoJdbcWrapper,
                    "insert into $tableName (`${paramNames.joinToString("`,`")}`) select ${
                        reNames.joinToString(
                            ","
                        ) { ":${it}" }
                    } from dual where not exists (select 1 from $tableName where ${
                        deleted(
                            deleted,
                            kotoJdbcWrapper,
                            tableName
                        )
                    } and ${
                        onFields.joinToString(
                            " and "
                        ) { "`${it.columnName}` = :${it.propertyName}" }
                    }) ".rmRedundantBlk(), paramMap,
                    updateKoto<KPojo>(KPojo, *updateFields.toTypedArray(), jdbcWrapper = kotoJdbcWrapper).except("id")
                        .where(
                            onFields
                                .map { it.columnName.lineToHump().eq().alias(it.propertyName) }
                                .arbitrary()
                        ).build()
                )

                SQLite -> KotoOperationSet(kotoJdbcWrapper,
                    "insert into $tableName (`${paramNames.joinToString("`,`")}`) values (${
                        reNames.joinToString(
                            ","
                        ) { ":$it" }
                    }) on conflict(${onFields.joinToString(",") { "`${it.columnName}`" }}) do update set ${
                        updateFields.joinToString(",") {
                            "`${it.columnName}` = :${it.propertyName}"
                        }
                    } ".rmRedundantBlk(),
                    paramMap)

                else -> throw UnsupportedOperationException("Unsupported database type")
            }
        } else {
            return KotoOperationSet(
                kotoJdbcWrapper,
                "insert into $tableName (`${paramNames.joinToString("`,`")}`) values (${
                    reNames.joinToString(
                        ","
                    ) { ":$it" }
                }) ".rmRedundantBlk(), paramMap)
        }
    }

    companion object{
        @OptIn(NeedTableIndexes::class)
        fun <K : KPojo> Collection<CreateWhere<K>>.onDuplicateUpdate(vararg fields: Field): Collection<CreateWhere<K>> {
            return this.map { it.onDuplicateUpdate(*fields) }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.onId(): Collection<CreateWhere<K>> {
            return this.map { it.onId() }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.on(field: Field, vararg fields: Field): Collection<CreateWhere<K>> {
            return this.map { it.on(field, *fields) }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.update(field: Field, vararg fields: Field): Collection<CreateWhere<K>> {
            return this.map { it.update(field, *fields) }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.except(field: Field, vararg fields: Field): Collection<CreateWhere<K>> {
            return this.map { it.except(field, *fields) }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.map(vararg pairs: Pair<String, Any?>): Collection<CreateWhere<K>> {
            return this.map { it.map(*pairs) }
        }

        fun <K : KPojo> Collection<CreateWhere<K>>.build(): Collection<KotoOperationSet<CreateWhere<K>, K>> {
            return this.map { it.build() }
        }
    }
}
