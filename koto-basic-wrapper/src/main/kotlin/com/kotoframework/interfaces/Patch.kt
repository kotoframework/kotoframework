package com.kotoframework.interfaces

import com.kotoframework.BasicJdbcWrapper.Companion.wrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.utils.Jdbc
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 11:54
 */
object Patch {
    fun KotoOperationSet<*, *>.execute(ds: DataSource? = null): KotoExecuteResult {
        val result = Jdbc.execute(ds.wrapper(), sql, paramMap)
        then?.execute(ds)
        return result
    }
}
