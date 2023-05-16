package com.kotoframework.interfaces

import com.kotoframework.beans.KExecuteResult
import com.kotoframework.beans.KOperationSet
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/9/20 11:54
 */
object Patch {
    fun KOperationSet<*, *>.execute(): KExecuteResult {
        val result = Jdbc.execute(jdbcWrapper, sql, paramMap)
        then?.execute()
        return result
    }
}
