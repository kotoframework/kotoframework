package com.kotoframework.function.create

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(ds: DataSource? = null): KotoExecuteResult {
        val koto = this.build()
        koto.then?.let {
            koto.execute(ds)
        }
        return koto.execute(ds)
    }
}
