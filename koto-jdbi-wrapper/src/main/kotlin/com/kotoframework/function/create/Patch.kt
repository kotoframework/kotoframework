package com.kotoframework.function.create

import com.kotoframework.JdbiWrapper
import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import org.jdbi.v3.core.Jdbi
/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(jdbi: Jdbi? = (kotoJdbcWrapper as JdbiWrapper?)?.getJdbi()): KotoExecuteResult {
        val koto = this.build()
        koto.then?.let {
            koto.execute(jdbi)
        }
        return koto.execute(jdbi)
    }
}
