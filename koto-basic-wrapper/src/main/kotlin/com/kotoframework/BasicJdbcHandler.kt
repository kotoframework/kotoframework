package com.kotoframework

import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.interfaces.KotoQueryHandler
import com.kotoframework.utils.Printer
import com.kotoframework.utils.Extension.isAssignableFrom
import com.kotoframework.utils.Extension.toKPojo
import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc
import javax.sql.DataSource
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/7/20 09:31
 */
class BasicJdbcHandler : KotoQueryHandler() {
    override fun forList(
        jdbc: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any> {
        val wrapper =
            ((jdbc ?: Jdbc.defaultJdbcWrapper) as BasicJdbcWrapper)
        val ds = wrapper.getDataSource()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        return if (kClass isAssignableFrom KPojo::class) {
            Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).map { it.toKPojo(kClass) }
        } else {
            ds.query(sql, paramMap, kClass.java)
        }
    }

    override fun forObject(
        jdbc: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        withoutErrorPrintln: Boolean,
        kClass: KClass<*>
    ): Any {
        val wrapper =
            ((jdbc ?: Jdbc.defaultJdbcWrapper) as BasicJdbcWrapper)
        val ds = wrapper.getDataSource()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        try {
            return if (kClass isAssignableFrom KPojo::class) {
                Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).first().toKPojo(kClass)
            } else {
                ds.query(
                    sql, paramMap, kClass.java
                ).first()
            }
        } catch (e: Exception) {
            when (e) {
                is NullPointerException, is IndexOutOfBoundsException, is NoSuchElementException -> {
                    if (!withoutErrorPrintln){
                        Printer.errorPrintln("You are using 【queryForObject】 to get a single column, but the result set is empty.If you want to query for a nullable column, use 【queryForObjectOrNull】 instead")
                    }
                }
            }
            throw IllegalStateException()
        }
    }

    override fun forObjectOrNull(
        jdbc: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): Any? {
        return try {
            forObject(jdbc, sql, paramMap, true, kClass)
        } catch (e: Exception) {
            when (e) {
                is NullPointerException, is IndexOutOfBoundsException, is NoSuchElementException -> null
                else -> throw IllegalStateException()
            }
        }
    }

    private fun DataSource.query(sql: String, paramMap: Map<String, Any?>, clazz: Class<*>): List<Any> {
        val (jdbcSql, jdbcParamList) = NamedParameterUtils.parseSqlStatement(sql, paramMap)
        val conn = this.connection
        val ps = conn.prepareStatement(jdbcSql)
        jdbcParamList.forEachIndexed { index, any ->
            ps.setObject(index + 1, any)
        }
        val rs = ps.executeQuery()
        val list = mutableListOf<Any>()
        while (rs.next()) {
            list.add(rs.getObject(1, clazz))
        }
        return list
    }
}
