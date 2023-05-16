package com.kotoframework.utils

import com.kotoframework.*
import com.kotoframework.beans.Config.Companion.softDeleteConfig
import com.kotoframework.interfaces.KJdbcWrapper
import com.kotoframework.core.condition.Criteria
import com.kotoframework.utils.Jdbc.tableMetaKey
import java.beans.IntrospectionException
import java.beans.Introspector
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ousc on 2022/4/18 10:50
 */

/**
 * It converts the parameter name to a line.
 *
 * @param criteria Criteria
 * @return The parameter name is being returned.
 */
fun getColumnName(criteria: Criteria): String {
    return criteria.parameterName.humpToLine()
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
    jdbcWrapper: KJdbcWrapper? = null,
    tableName: String = "",
    tableAlias: String? = ""
): String {
    val tableMeta = Jdbc.tableMap[tableMetaKey(jdbcWrapper, tableName)]?.meta
    val softDeleteColumn = tableMeta?.softDelete?.column ?: softDeleteConfig.column
    return if (softDeleteColumn.isBlank()) "true" else "$tableAlias`$softDeleteColumn` = $value"
}

fun toSqlDate(str: String?): String? {
    if (str.isNullOrEmpty()) return null
    return str!!.replace("YYYY", "%Y").replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d")
        .replace("HH", "%H").replace("mm", "%i").replace("ss", "%s").replace("SSS", "%S")
}

val currentDate: String get() = SimpleDateFormat("yyyy-MM-dd").format(Date())
val currentTime: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
val currentTimeM: String get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Date())

data class KotoPagination(
    val prefix: String,
    var suffix: String? = null,
    val limit: Int? = null,
    val offset: Int? = null
)

fun smartPagination(
    prefix: String,
    suffix: String?,
    pageIndex: Int?,
    pageSize: Int?
): KotoPagination {
    if (pageIndex != null && pageSize != null) {
        when (KotoApp.dbType) {
            MySql, PostgreSQL, SQLite, OceanBase -> {
                return KotoPagination(
                    prefix,
                    suffix,
                    pageSize,
                    (pageIndex - 1) * pageSize
                )
            }

            Oracle -> {
                val rowNumMin = (pageIndex - 1) * pageSize + 1
                val rowNumMax = pageIndex * pageSize
                return KotoPagination(
                    prefix.replaceFirst("select", "select * from (select rownum rn, t.* from (select"),
                    "$suffix ) t ) where $rowNumMin < RN <= $rowNumMax",
                )
            }

            MSSql -> {
                val offset = (pageIndex - 1) * pageSize
                val next = pageIndex * pageSize
                return KotoPagination(prefix, "$suffix offset $offset rows fetch next $next rows only")
            }

            else -> throw UnsupportedOperationException("Unsupported database type")
        }
    } else return KotoPagination(prefix, suffix)
}