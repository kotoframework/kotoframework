package com.kotoframework.utils

import com.kotoframework.KotoApp
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table
import com.kotoframework.definition.columnName
import com.kotoframework.interfaces.KPojo
import java.beans.BeanInfo
import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.javaType

/**
 * Created by sundaiyue on 2022/10/12 10:55
 */
object Extension {
    private val constructors = HashMap<Class<*>, KFunction<*>>()

    /* It converts a string with camel case to a string with underscores. */
    internal fun String.humpToLine(): String {
        val matcher: Matcher = Pattern.compile("[A-Z]").matcher(this)
        var sb = StringBuffer()
        var temp: String
        while (matcher.find()) {
            temp = matcher.group()
            matcher.appendReplacement(sb, "_" + temp.lowercase())
        }
        matcher.appendTail(sb)
        if (sb.toString().startsWith("_")) {
            sb = StringBuffer(sb.toString().substring(1))
        }
        return sb.toString()
    }

    /* It converts a string with underscores to a string with camel case. */
    fun String.lineToHump(): String {
        var temp = this
        val linePattern = Pattern.compile("_(\\w)")
        temp = temp.lowercase(Locale.getDefault())
        val matcher = linePattern.matcher(temp)
        val sb = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).uppercase(Locale.getDefault()))
        }
        matcher.appendTail(sb)
        return sb.toString()
    }

    /* It's an extension function of String. It will return a string with redundant spaces removed. */
    internal fun String.rmRedudantBlk(): String {
        return this.replace("\\s+".toRegex(), " ").trim()
    }

    /* It's an extension function of Any?. It will return true if the object is null or blank. */
    internal fun Any?.isNullOrBlank(): Boolean {
        return when (this) {
            null -> true
            is String -> this.isBlank()
            is Collection<*> -> this.isEmpty()
            else -> false
        }
    }

    /* It's an extension function of String. It will return a string with the first letter of each word capitalized. */
    private fun String.kPojoNameToTableName(): String {
        return this.replace(KotoApp.kPojoSuffix, "").replace("$", "").humpToLine()
    }

    private inline fun <reified T : Annotation> KPojo.getAnnotation(): T? {
        return this::class.annotations.firstOrNull { it is T } as T?
    }

    val KPojo.tableMeta: Jdbc.TableMeta
        get() {
            val tableAnnotation = getAnnotation<Table>()
            val softDeleteAnnotation = getAnnotation<SoftDelete>()
            val tableName = tableAnnotation?.name?.ifEmpty { tableName } ?: tableName
            val softDelete = if (softDeleteAnnotation != null) {
                val enabled = softDeleteAnnotation.enable || KotoApp.softDeleteEnabled
                val columnName = softDeleteAnnotation.column.columnName.ifEmpty { KotoApp.softDeleteColumn }
                Jdbc.TableSoftDelete(
                    enabled,
                    columnName
                )
            } else {
                Jdbc.TableSoftDelete(
                    KotoApp.softDeleteEnabled,
                    KotoApp.softDeleteColumn
                )
            }

            return Jdbc.TableMeta(tableName, softDelete)
        }

    val KClass<*>.tableName: String
        get() {
            return findAnnotation<Table>()?.name
                ?: simpleName!!.kPojoNameToTableName()
        }
    val KClass<*>.tableAlias: String
        get() = tableName.lineToHump()

    val KPojo.tableName: String
        get() = this::class.tableName

    val KPojo.tableAlias: String
        get() = this::class.tableName.lineToHump()

    /* It's an extension function of KPojo. It will return a map of the object. */
    fun KPojo?.toMap(vararg pairs: Pair<String, Any?>): Map<String, Any?> {
        return this.toMutableMap(*pairs)
    }

    /* It's an extension function of KPojo. It will return a KPojo of the object. */
    inline fun <reified T : KPojo> KPojo.toKPojo(vararg pairs: Pair<String, Any?>): T {
        return this.toMap().toMutableMap().apply { putAll(pairs) }.toKPojo()
    }

    /* AN extension function of Map. It will return a KPojo of the map. */
    inline fun <reified T> Map<String, *>.toKPojo(): T {
        return this.toKPojo(T::class) as T
    }

    /* AN extension function of Map. It will return a KPojo of the map. */
    @Suppress("UNCHECKED_CAST")
    fun <T> Map<String, *>.toKPojo(clazz: Class<*>): T {
        return this.toKPojo(clazz.kotlin) as T
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun Map<String, *>.toKPojo(clazz: KClass<*>): Any {
        val constructor = constructors[clazz.java] ?: clazz.constructors.first()
        if (constructors[clazz.java] == null) {
            constructors[clazz.java] = constructor
        }
        return try {
            val booleanNames =
                constructor.parameters.filter { it.type.javaType.typeName == "java.lang.Boolean" }.map { it.name }
            constructor.callBy(constructor.parameters.associateWith {
                if (booleanNames.contains(it.name)) {
                    if (this[it.name] is Int)
                        (this[it.name] == 1)
                    else
                        (this[it.name] != null)
                } else {
                    this[it.name] ?: this[it.name?.lineToHump()]
                }
            })!!
        } catch (e: IllegalArgumentException) {
            // compare the argument type of constructor and the given value, print which argument is mismatched
            val mismatchedArgument = constructor.parameters.first {
                if (this[it.name] == null) {
                    !it.isOptional
                } else {
                    it.type.javaType.typeName != this[it.name]!!.javaClass.typeName
                }
            }
            if (this[mismatchedArgument.name] == null) {
                throw IllegalArgumentException("The argument ${clazz.simpleName}.${mismatchedArgument.name} is null, but it's not optional.")
            } else {
                throw IllegalArgumentException("The argument ${clazz.simpleName}.${mismatchedArgument.name} is ${this[mismatchedArgument.name]!!.javaClass.typeName} but expected ${mismatchedArgument.type.javaType.typeName}.")
            }
        }
    }

    /* It's an extension function of KPojo. It will return a map of the object. */
    fun KPojo?.toMutableMap(vararg pairs: Pair<String, Any?>): MutableMap<String, Any?> {
        if (this == null) return mutableMapOf()
        val map: MutableMap<String, Any?> = HashMap()
        val beanInfo: BeanInfo = Introspector.getBeanInfo(this::class.java)
        val propertyDescriptors = beanInfo.propertyDescriptors
        for (i in propertyDescriptors.indices) {
            val descriptor: PropertyDescriptor = propertyDescriptors[i]
            val propertyName: String = descriptor.name
            if (propertyName != "class") {
                map[propertyName] = descriptor.readMethod.invoke(this)
            }
        }
        return map.apply { putAll(pairs) }
    }

    infix fun Any.isAssignableFrom(kClass: KClass<*>): Boolean {
        if (this is KClass<*>) return kClass.java.isAssignableFrom(this.java)
        return kClass.java.isAssignableFrom(this::class.java)
    }

    /* It's an extension function of Boolean. It will return the value of the function if the boolean is true. Otherwise,
    it will return null. */
    inline fun <reified T> Boolean.yes(value: () -> T?): T? {
        return if (this) value() else null
    }

    /* It's an extension function of Boolean. It will return the value of the function if the boolean is true. Otherwise,
    it will return null. */
    inline fun <reified T> Boolean.no(value: () -> T?): T? {
        return if (this) null else value()
    }

    fun <T, K> List<Map<T, K>>.asMutable(): MutableList<MutableMap<T, K>> {
        return this.map { it.toMutableMap() }.toMutableList()
    }
}
