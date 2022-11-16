package com.kotoframework.core.where

import com.kotoframework.IsNull
import com.kotoframework.beans.Unknown
import com.kotoframework.definition.AddCondition
import com.kotoframework.function.remove.RemoveWhere
import com.kotoframework.function.update.UpdateWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper

/**
 * Created by ousc on 2022/5/10 02:31
 */
open class OperateWhere<T : KPojo>(
    KPojo: T,
    override var kotoJdbcWrapper: KotoJdbcWrapper? = null,
    val addCondition: AddCondition<T>? = null
) : Where<T>(KPojo, kotoJdbcWrapper, Unknown::class, addCondition) {

    init {
        super.ifNoValue { IsNull }
    }

    override fun where(where: Where<T>): OperateWhere<T> {
        super.where(where)
        return this
    }

    override fun map(vararg pairs: Pair<String, Any?>): OperateWhere<T> {
        super.map(*pairs)
        return this
    }

    /**
     * The `RemoveWhere` class is a class that is used to build the `WHERE` clause of an `Delete` statement. It has a
     * `where()` function that returns a `RemoveWhere<T>` object
     *
     * @return A RemoveWhere object
     */
    internal fun getRemoveWhere(): RemoveWhere<T> {
        return RemoveWhere(KPojo, kotoJdbcWrapper, addCondition).where(this)
    }

    /**
     *
     * The `UpdateWhere` class is a class that is used to build the `WHERE` clause of an `UPDATE` statement. It has a
     * `where()` function that returns a `UpdateWhere<T>` object
     *
     * @return UpdateWhere<T>
     */
    internal fun getUpdateWhere(): UpdateWhere<T> {
        return UpdateWhere(KPojo, kotoJdbcWrapper, addCondition).where(this)
    }
}
