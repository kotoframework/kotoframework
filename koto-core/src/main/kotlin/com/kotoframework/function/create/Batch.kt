package com.kotoframework.function.create

import com.kotoframework.beans.KotoOperationSet
import com.kotoframework.function.remove.RemoveWhere
import com.kotoframework.function.update.UpdateSetClause
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/5/31 17:09
 */
@JvmName("batchExecuteRemoveWhere")
fun Collection<RemoveWhere<*>?>.batchExecute(jdbcWrapper: KotoJdbcWrapper? = null): Int {
    val builds = filterNotNull().map { it.build() }
    return builds.batchExecute(jdbcWrapper)
}

@JvmName("batchExecuteCreateWhere")
fun Collection<CreateWhere<*>?>.batchExecute(jdbcWrapper: KotoJdbcWrapper? = null): Int {
    val builds = filterNotNull().map { it.build() }
    return builds.batchExecute(jdbcWrapper)
}

@JvmName("batchExecuteUpdateWhere")
fun Collection<UpdateSetClause<*>?>.batchExecute(jdbcWrapper: KotoJdbcWrapper? = null): Int {
    val builds = filterNotNull().map { it.build() }
    return builds.batchExecute(jdbcWrapper)
}


@JvmName("batchExecuteKotoOperationSet")
fun Collection<KotoOperationSet<*, *>?>.batchExecute(jdbcWrapper: KotoJdbcWrapper? = null): Int {
    val builds = filterNotNull()
    val result = if (isNotEmpty()) Jdbc.batchExecute(
        jdbcWrapper,
        builds.first().sql,
        builds.map { it.paramMap }.toTypedArray(),
    ) else 0

    if (builds.first().then != null) {
        builds.map { it.then }.batchExecute(jdbcWrapper)
    }

    return result
}
