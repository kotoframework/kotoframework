package com.kotoframework.interfaces

import com.kotoframework.beans.KExecuteResult
import com.kotoframework.utils.Jdbc
import kotlin.reflect.KClass

/**
 * Created by sundaiyue on 2023/4/5 09:49
 */
abstract class Executable<T : KPojo>(open val kJdbcWrapper: KJdbcWrapper? = null, val kClass: KClass<*>) : Buildable() {
    fun execute(jdbcWrapper: KJdbcWrapper? = kJdbcWrapper): KExecuteResult {
        val prepared = this.build()
        return Jdbc.execute(jdbcWrapper, prepared.sql, prepared.paramMap)
    }
}
