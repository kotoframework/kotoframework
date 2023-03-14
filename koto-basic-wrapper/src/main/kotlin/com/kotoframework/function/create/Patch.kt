package com.kotoframework.function.create

import com.kotoframework.BasicJdbcWrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(): KotoExecuteResult {
        val (prepared) = this
        prepared.then?.let {
            prepared.execute()
        }
        return prepared.execute()
    }
}
