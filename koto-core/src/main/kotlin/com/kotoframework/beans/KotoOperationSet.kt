package com.kotoframework.beans

import com.kotoframework.core.where.BaseWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoDataSet

/**
 * Created by ousc on 2022/4/18 10:32
 */

/**
 * Koto operation set
 *
 * @param T
 * @param K
 * @property sql
 * @property paramMap
 * @property then
 * @constructor Create empty Koto operation set
 * @author ousc
 */
class KotoOperationSet<T : BaseWhere<K>, K : KPojo>(
    override val sql: String, override val paramMap: Map<String, Any?>, val then: KotoOperationSet<*, *>? = null
) : KotoDataSet
