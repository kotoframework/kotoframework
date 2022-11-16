package com.kotoframework.utils

import com.kotoframework.EQUAL
import com.kotoframework.KotoApp
import com.kotoframework.NoValueStrategy
import com.kotoframework.definition.Field
import com.kotoframework.definition.columnName
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.core.condition.Criteria
import com.kotoframework.utils.Extension.humpToLine
import com.kotoframework.utils.Jdbc.dbName
import java.beans.IntrospectionException
import java.beans.Introspector
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ousc on 2022/4/18 10:50
 */
object Common {
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
     * @param criteria Criteria
     * @return The parameter name is being returned.
     */
    fun getColumnName(criteria: Criteria): String {
        return criteria.kCallable?.columnName ?: criteria.parameterName.humpToLine()
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
        return if (softDeleteColumn.isBlank()) "true" else "$tableAlias`$softDeleteColumn` = $value"
    }

    fun toSqlDate(str: String?): String? {
        if (str.isNullOrEmpty()) return null
        return str.replace("YYYY", "%Y").replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d")
            .replace("HH", "%H").replace("mm", "%i").replace("ss", "%s").replace("SSS", "%S")
    }

    val currentDate: String get() = SimpleDateFormat("yyyy-MM-dd").format(Date())
    val currentTime: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    val currentTimeM: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Date())
}
