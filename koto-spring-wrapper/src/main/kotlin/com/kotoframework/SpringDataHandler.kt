package com.kotoframework

import com.kotoframework.utils.Printer
import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.interfaces.KotoQueryHandler
import com.kotoframework.utils.Common.isAssignableFrom
import com.kotoframework.utils.Common.no
import com.kotoframework.utils.Common.toKPojo
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.SingleColumnRowMapper
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/7/20 09:31
 */
class SpringDataHandler : KotoQueryHandler() {
    override fun forList(
        jdbc: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any> {
        val wrapper =
            ((jdbc?: Jdbc.defaultJdbcWrapper) as SpringDataWrapper)
        val namedJdbc = wrapper.getNamedJdbc()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        return if (kClass isAssignableFrom KPojo::class) {
            Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).map { it.toKPojo(kClass) }
        } else {
            namedJdbc.query(sql, paramMap, SingleColumnRowMapper(kClass.java)) as List<Any>
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
            ((jdbc?: Jdbc.defaultJdbcWrapper) as SpringDataWrapper)
        val namedJdbc = wrapper.getNamedJdbc()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        try {
            return if (kClass isAssignableFrom KPojo::class) {
                Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).first().toKPojo(kClass)
            } else {
                namedJdbc.queryForObject(
                    sql, paramMap, SingleColumnRowMapper(kClass.java)
                )!!
            }
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException, is NullPointerException, is IndexOutOfBoundsException, is NoSuchElementException -> {
                    withoutErrorPrintln.no { Printer.errorPrintln("You are using 【queryForObject】 to get a single column, but the result set is empty.If you want to query for a nullable column, use 【queryForObjectOrNull】 instead.") }
                }

                is IncorrectResultSizeDataAccessException -> {
                    withoutErrorPrintln.no { Printer.errorPrintln("You are using 【queryForObject】 on a query that returns more than one row. This is not supported. Use 【queryForList】 instead.") }
                }
            }
            throw e
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
                is EmptyResultDataAccessException, is NullPointerException, is IndexOutOfBoundsException, is NoSuchElementException -> null
                is IncorrectResultSizeDataAccessException -> {
                    Printer.errorPrintln("You are using 【queryForObjectOrNull】 on a query that returns more than one row. This is not supported. Use 【queryForList】 instead.")
                    throw e
                }

                else -> throw e
            }
        }
    }

}
