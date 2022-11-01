package com.kotoframework.function.create

import com.kotoframework.SpringDataWrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(namedParameterJdbcTemplate: NamedParameterJdbcTemplate? = (kotoJdbcWrapper as SpringDataWrapper?)?.getNamedJdbc()): KotoExecuteResult {
        val koto = this.build()
        koto.then?.let {
            koto.execute(namedParameterJdbcTemplate)
        }
        return koto.execute(namedParameterJdbcTemplate)
    }
}
