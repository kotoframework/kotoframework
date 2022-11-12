package com.kotoframework.interfaces

import com.kotoframework.SpringDataWrapper.Companion.wrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.utils.Jdbc
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/20 11:54
 */
object Patch {
    fun KotoOperationSet<*, *>.execute(): KotoExecuteResult {
        val result = Jdbc.execute(jdbcWrapper, sql, paramMap)
        then?.execute()
        return result
    }
}
