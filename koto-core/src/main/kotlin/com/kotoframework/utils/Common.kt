package com.kotoframework.utils

import com.kotoframework.KotoApp
import com.kotoframework.KotoApp.kPojoSuffix
import com.kotoframework.KotoApp.softDeleteColumn
import com.kotoframework.KotoApp.softDeleteEnabled
import com.kotoframework.definition.Field
import com.kotoframework.definition.columnName
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.Table
import com.kotoframework.core.condition.BaseCondition
import com.kotoframework.utils.Jdbc.dbName
import java.beans.BeanInfo
import java.beans.IntrospectionException
import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.javaType


/**
 * Created by ousc on 2022/4/18 10:50
 */
object Common {
    private val constructors = HashMap<Class<*>, KFunction<*>>()

    /* It converts a string with camel case to a string with underscores. */
    fun String.humpToLine(): String {
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

    /* It's an extension function of String. It will return a string with the first letter of each word capitalized. */
    private fun String.kPojoNameToTableName(): String {
        return this.replace(kPojoSuffix, "").replace("$", "").humpToLine()
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
                val enabled = softDeleteAnnotation.enable || softDeleteEnabled
                val columnName = softDeleteAnnotation.column.columnName.ifEmpty { softDeleteColumn }
                Jdbc.TableSoftDelete(
                    enabled,
                    columnName
                )
            } else {
                Jdbc.TableSoftDelete(
                    softDeleteEnabled,
                    softDeleteColumn
                )
            }

            return Jdbc.TableMeta(tableName, softDelete)
        }

    fun LEN(field: Field): String {
        return "LENGTH(${field.columnName})"
    }

    fun SUM(field: Field): String {
        return "SUM(${field.columnName})"
    }

    fun COUNT(field: Field): String {
        return "COUNT(${field.columnName})"
    }

    fun MAX(field: Field): String {
        return "MAX(${field.columnName})"
    }

    fun MIN(field: Field): String {
        return "MIN(${field.columnName})"
    }

    /**
     * It converts the parameter name to a line.
     *
     * @param bc BaseCondition
     * @return The parameter name is being returned.
     */
    fun getParameter(bc: BaseCondition): String {
        return if (bc.humpToLine == true) {
            bc.parameterName!!.humpToLine()
        } else bc.parameterName!!
    }

    /* It's an extension function of String. It will return a string with redundant spaces removed. */
    fun String.rmRedudantBlk(): String {
        return this.replace("\\s+".toRegex(), " ").trim()
    }

    /* It's an extension function of Any?. It will return true if the object is null or blank. */
    fun Any?.isNullOrBlank(): Boolean {
        return when (this) {
            null -> true
            is String -> this.isBlank()
            is Collection<*> -> this.isEmpty()
            else -> false
        }
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

    /** 实现将源类属性拷贝到目标类中
     * @param source
     * @param target
     */
    fun copyProperties(source: Any, target: Any) {
        try {
            //获取目标类的属性信息
            val targetBean = Introspector.getBeanInfo(target.javaClass)
            val propertyDescriptors = targetBean.propertyDescriptors
            //对每个目标类的属性查找set方法，并进行处理
            for (i in propertyDescriptors.indices) {
                val pro = propertyDescriptors[i]
                val wm = pro.writeMethod
                if (wm != null) { //当目标类的属性具有set方法时，查找源类中是否有相同属性的get方法
                    val sourceBean = Introspector.getBeanInfo(source.javaClass)
                    val sourcePds = sourceBean.propertyDescriptors
                    for (j in sourcePds.indices) {
                        if (sourcePds[j].name == pro.name) { //匹配
                            val rm = sourcePds[j].readMethod
                            //如果方法不可访问(get方法是私有的或不可达),则抛出SecurityException
                            if (!Modifier.isPublic(rm.declaringClass.modifiers)) {
                                rm.isAccessible = true
                            }
                            //获取对应属性get所得到的值
                            val value = rm.invoke(source, *arrayOfNulls(0))
                            if (!Modifier.isPublic(wm.declaringClass.modifiers)) {
                                wm.isAccessible = true
                            }
                            //调用目标类对应属性的set方法对该属性进行填充
                            wm.invoke(target, *arrayOf(value))
                            break
                        }
                    }
                }
            }
        } catch (e: IntrospectionException) {
            e.printStackTrace()
        } catch (e: java.lang.IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    /* It's an extension function of KPojo. It will return a map of the object. */
    fun KPojo?.toMap(vararg pairs: Pair<String, Any?>): Map<String, Any?> {
        return this.toMutableMap(*pairs)
    }

    /* It's an extension function of KPojo. It will return a KPojo of the object. */
    inline fun <reified T : KPojo> KPojo.toKPojo(vararg pairs: Pair<String, Any?>): T {
        return this.toMap().toMutableMap().apply { putAll(pairs) }.toKPojo()
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

    fun deleted(
        value: Any = 0,
        jdbcWrapper: KotoJdbcWrapper? = null,
        tableName: String = "",
        tableAlias: String? = ""
    ): String {
        val wrapper = Jdbc.getJdbcWrapper(jdbcWrapper)
        val meta = wrapper.let {
            Jdbc.tableMap["${it.dbName}_$tableName"]?.meta
        }
        val softDeleteColumn = meta?.softDelete?.column ?: KotoApp.softDeleteColumn
        return if (softDeleteColumn.isNullOrBlank()) "true" else "$tableAlias`$softDeleteColumn` = $value"
    }

    val currentDate: String get() = SimpleDateFormat("yyyy-MM-dd").format(Date())
    val currentTime: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    val currentTimeM: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Date())
    internal fun String.toSqlDate(): String? {
        if (this.isEmpty()) return null
        return this.replace("YYYY", "%Y").replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d")
            .replace("HH", "%H").replace("mm", "%i").replace("ss", "%s").replace("SSS", "%S")
    }

    infix fun Any.isAssignableFrom(kClass: KClass<*>): Boolean {
        if (this is KClass<*>) return kClass.java.isAssignableFrom(this.java)
        return kClass.java.isAssignableFrom(this::class.java)
    }

    fun <T, K> List<Map<T, K>>.asMutable(): MutableList<MutableMap<T, K>> {
        return this.map { it.toMutableMap() }.toMutableList()
    }
}
