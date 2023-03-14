package com.kotoframework.function.remove

import com.kotoframework.beans.KotoExecuteResult
import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.core.where.OperateWhere
import com.kotoframework.core.where.Where
import com.kotoframework.definition.AddCondition
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoDataSet
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/5/10 02:31
 */
class RemoveWhere<T : KPojo>(
    KPojo: T, kotoJdbcWrapper: KotoJdbcWrapper? = null, addCondition: AddCondition<T>? = null
) : OperateWhere<T>(KPojo, kotoJdbcWrapper, addCondition) {
    override fun where(where: Where<T>): RemoveWhere<T> {
        super.where(where)
        return this
    }

    override fun build(): KotoOperationSet<RemoveWhere<T>, T> {
        val prepared = super.build()
        return KotoOperationSet(prepared.sql, prepared.paramMap, jdbcWrapper = kotoJdbcWrapper)
    }

    override fun component1() = build()

    fun execute(jdbcWrapper: KotoJdbcWrapper? = kotoJdbcWrapper): KotoExecuteResult {
        val prepared = this.build()
        return Jdbc.execute(jdbcWrapper, prepared.sql, prepared.paramMap)
    }
}
