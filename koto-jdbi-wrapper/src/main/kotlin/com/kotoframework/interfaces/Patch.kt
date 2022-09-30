package com.kotoframework.interfaces

import com.kotoframework.JdbiWrapper.Companion.wrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.utils.Jdbc
import org.jdbi.v3.core.Jdbi
/**
 * Created by ousc on 2022/9/20 11:54
 */
object Patch {
    fun KotoOperationSet<*, *>.execute(jdbi: Jdbi? = null): KotoExecuteResult {
        val result = Jdbc.execute(jdbi.wrapper(), sql, paramMap)
        then?.execute(jdbi)
        return result
    }
}
