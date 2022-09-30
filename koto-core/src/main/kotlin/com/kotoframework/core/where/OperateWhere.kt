package com.kotoframework.core.where

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.beans.Unknown
import com.kotoframework.definition.AddCondition
import com.kotoframework.function.remove.RemoveWhere
import com.kotoframework.function.update.UpdateWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Jdbc
import com.kotoframework.utils.Printer.errorPrintln

/**
 * Created by ousc on 2022/5/10 02:31
 */
open class OperateWhere<T : KPojo>(
    KPojo: T,
    val kotoJdbcjdbcWrapper: KotoJdbcWrapper? = null,
    val addCondition: AddCondition<T>? = null
) : Where<T>(KPojo, kotoJdbcjdbcWrapper, Unknown::class, addCondition) {

    init {
        super.allowNull(true)
    }

    override fun where(where: Where<T>): OperateWhere<T> {
        super.where(where)
        return this
    }

    override fun map(vararg pairs: Pair<String, Any?>): OperateWhere<T> {
        super.map(*pairs)
        return this
    }

    internal fun getRemoveWhere(): RemoveWhere<T> {
        return RemoveWhere(KPojo, kotoJdbcjdbcWrapper, addCondition).where(this)
    }

    internal fun getUpdateWhere(): UpdateWhere<T> {
        return UpdateWhere(KPojo, kotoJdbcjdbcWrapper, addCondition).where(this)
    }
}
