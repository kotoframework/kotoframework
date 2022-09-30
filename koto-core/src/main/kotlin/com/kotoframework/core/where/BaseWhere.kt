package com.kotoframework.core.where

import com.kotoframework.definition.AddCondition
import com.kotoframework.core.condition.BaseCondition
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoDataSet
import com.kotoframework.utils.Common.tableName
import com.kotoframework.utils.Common.toMutableMap

/**
 * Created by ousc on 2022/5/9 23:52
 */

abstract class BaseWhere<T : KPojo>(
    KPojo: T,
    addCondition: AddCondition<T>? = null
) {
    internal var conditions = mutableListOf<BaseCondition?>()
    internal var paramMap = mutableMapOf<String, Any?>()
    internal var tableName: String = KPojo.tableName
    internal var finalMap = mutableMapOf<String, Any?>()

    init {
        paramMap = KPojo.toMutableMap()
        if (addCondition != null) {
            conditions.add(addCondition(KPojo))
        }
    }


    /**
     * It sets the parameters for the query.
     *
     * @param pairs A pairs list of parameters.
     * @return Nothing.
     */
    open fun map(vararg pairs: Pair<String, Any?>): BaseWhere<T> {
        pairs.forEach {
            finalMap[it.first] = it.second
        }
        return this
    }

    abstract fun build(): KotoDataSet
}
