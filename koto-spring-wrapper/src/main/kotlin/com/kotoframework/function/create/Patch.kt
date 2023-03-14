package com.kotoframework.function.create

import com.kotoframework.SpringDataWrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(): KotoExecuteResult {
        val (prepared) = this
        return prepared.let {
            val result = it.execute()
            it.then?.execute() ?: result
        }
    }
}
