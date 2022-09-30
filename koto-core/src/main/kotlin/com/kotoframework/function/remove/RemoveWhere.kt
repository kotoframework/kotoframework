package com.kotoframework.function.remove

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.OperateWhere
import com.kotoframework.core.where.Where
import com.kotoframework.definition.AddCondition
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/5/10 02:31
 */
class RemoveWhere<T : KPojo>(
    KPojo: T,
    kotoJdbcjdbcWrapper: KotoJdbcWrapper? = null,
    addCondition: AddCondition<T>? = null
) : OperateWhere<T>(KPojo, kotoJdbcjdbcWrapper, addCondition) {
    override fun where(where: Where<T>): RemoveWhere<T> {
        super.where(where)
        return this
    }

    override fun build(): KotoOperationSet<RemoveWhere<T>, T> {
        val result = super.build()
        return KotoOperationSet(result.sql, result.paramMap)
    }

    fun execute(jdbcWrapper: KotoJdbcWrapper? = jdbcjdbcWrapper): KotoExecuteResult {
        val koto = this.build()
        return Jdbc.execute(jdbcWrapper, koto.sql, koto.paramMap)
    }
}
