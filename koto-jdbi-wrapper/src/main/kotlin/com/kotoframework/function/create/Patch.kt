package com.kotoframework.function.create

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
/**
 * Created by ousc on 2022/9/20 11:31
 */
object Patch {
    fun CreateWhere<*>.execute(): KotoExecuteResult {
        val koto = this.build()
        koto.then?.let {
            koto.execute()
        }
        return koto.execute()
    }
}
