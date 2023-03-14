package com.kotoframework.beans

import com.kotoframework.core.where.BaseWhere
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoDataSet
import com.kotoframework.interfaces.KotoJdbcWrapper

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
    override val sql: String,
    override val paramMap: Map<String, Any?>,
    val then: KotoOperationSet<*, *>? = null,
    val jdbcWrapper: KotoJdbcWrapper? = null
) : KotoDataSet {
    override operator fun component1() = sql
    override operator fun component2() = paramMap
    operator fun component3() = then
}
