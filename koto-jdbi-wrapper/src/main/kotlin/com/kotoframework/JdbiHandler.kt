package com.kotoframework

import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.interfaces.KotoQueryHandler
import com.kotoframework.utils.Extension.asMutable
import com.kotoframework.utils.Printer
import com.kotoframework.utils.Extension.isAssignableFrom
import com.kotoframework.utils.Extension.lineToHump
import com.kotoframework.utils.Extension.toKPojo
import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/7/20 09:31
 */
class JdbiHandler : KotoQueryHandler() {
    override fun forList(
        jdbc: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any> {
        val wrapper =
            ((jdbc ?: Jdbc.defaultJdbcWrapper) as JdbiWrapper)
        val unwrapped = wrapper.getJdbi()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        return if (kClass isAssignableFrom KPojo::class) {
            Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).map { it.lineToHump().toKPojo(kClass) }
        } else {
            unwrapped.withHandle<List<Any>, Exception> { handle ->
                handle.createQuery(sql)
                    .bindMap(paramMap)
                    .mapTo(kClass.java)
                    .list()
            }
        }
    }

    override fun forObject(
        jdbi: KotoJdbcWrapper?,
        sql: String,
        paramMap: Map<String, Any?>,
        withoutErrorPrintln: Boolean,
        kClass: KClass<*>
    ): Any {
        val wrapper =
            ((jdbi ?: Jdbc.defaultJdbcWrapper) as JdbiWrapper)
        val unwrapped = wrapper.getJdbi()
        Log.log(wrapper, sql, listOf(paramMap), "query")
        return try {
            if (kClass isAssignableFrom KPojo::class) {
                Jdbc.queryKotoJdbcData(wrapper, sql, paramMap).first().lineToHump().toKPojo(kClass)
            } else {
                unwrapped.withHandle<Any, Exception> { handle ->
                    handle.createQuery(sql)
                        .bindMap(paramMap)
                        .mapTo(kClass.java)
                        .one()
                }
            }
        } catch (e: NoSuchElementException) {
            if (!withoutErrorPrintln) {
                Printer.errorPrintln("You are using 【queryForObject】 to get a single column, but the result set is empty.If you want to query for a nullable column, use 【queryForObjectOrNull】 instead.")
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
        } catch (e: NoSuchElementException) {
            null
        }
    }

}
