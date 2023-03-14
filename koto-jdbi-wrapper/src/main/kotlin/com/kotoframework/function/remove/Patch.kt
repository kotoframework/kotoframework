package com.kotoframework.function.remove

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.interfaces.Patch.execute
import org.jdbi.v3.core.Jdbi

/**
 * Created by ousc on 2022/9/20 11:36
 */
object Patch {
    fun RemoveAction<*>.execute(): KotoExecuteResult {
        val (prepared) = this.where()
        return prepared.execute()
    }
}
