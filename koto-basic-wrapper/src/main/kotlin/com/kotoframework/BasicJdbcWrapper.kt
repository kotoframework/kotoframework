package com.kotoframework

import com.kotoframework.NamedParameterUtils.parseSqlStatement
import com.kotoframework.beans.NoDataSourceSpecifiedException
import com.kotoframework.beans.UnsupportedTypeException
import com.kotoframework.interfaces.KPojo
import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Extension.toKPojo
import javax.sql.DataSource


/**
 * Created by ousc on 2022/9/19 23:24
 */
class BasicJdbcWrapper : KotoJdbcWrapper() {
    var ds: DataSource? = null
    var dynamic: (() -> DataSource)? = null
    fun getDataSource(ds: DataSource? = null): DataSource {
        return ds ?: this.ds ?: dynamic?.invoke() ?: throw NoDataSourceSpecifiedException("DataSource is null")
    }

    val dataSource: DataSource
        get() = ds ?: dynamic?.let { it() } ?: throw NoDataSourceSpecifiedException("DataSource is null")

    override val url: String
        get() {
            val conn = dataSource.connection
            val url = conn.metaData.url
            conn.close()
            return url
        }

    override fun forList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> {
        val (jdbcSql, jdbcParamList) = parseSqlStatement(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(jdbcSql)
        jdbcParamList.forEachIndexed { index, any ->
            ps.setObject(index + 1, any)
        }
        val rs = ps.executeQuery()
        val metaData = rs.metaData
        val columnCount = metaData.columnCount
        val list = mutableListOf<Map<String, Any>>()
        while (rs.next()) {
            val map = mutableMapOf<String, Any>()
            for (i in 1..columnCount) {
                if (rs.getObject(i) != null) {
                    map[metaData.getColumnName(i)] = rs.getObject(i)
                }
            }
            list.add(map)
        }
        rs.close()
        ps.close()
        conn.close()
        return list
    }

    override fun forMap(sql: String, paramMap: Map<String, Any?>): Map<String, Any>? {
        val (jdbcSql, jdbcParamList) = parseSqlStatement(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(jdbcSql)
        jdbcParamList.forEachIndexed { index, any ->
            ps.setObject(index + 1, any)
        }
        val rs = ps.executeQuery()
        val metaData = rs.metaData
        val columnCount = metaData.columnCount
        val list = mutableListOf<Map<String, Any>>()
        while (rs.next()) {
            val map = mutableMapOf<String, Any>()
            for (i in 1..columnCount) {
                map[metaData.getColumnName(i)] = rs.getObject(i)
            }
            list.add(map)
        }
        rs.close()
        ps.close()
        conn.close()
        return list.firstOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> forObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? {
        val map = forMap(sql, paramMap)
        return if (String::class.java == clazz) {
            map?.values?.firstOrNull()?.toString() as T
        } else if (KPojo::class.java.isAssignableFrom(clazz)) {
            map?.toKPojo<T>(clazz)
        } else if (clazz.name == "java.lang.Integer") {
            map?.values?.firstOrNull()?.toString()?.toInt() as T
        } else if (clazz.name == "java.lang.Long") {
            map?.values?.firstOrNull()?.toString()?.toLong() as T
        } else if (clazz.name == "java.lang.Double") {
            map?.values?.firstOrNull()?.toString()?.toDouble() as T
        } else if (clazz.name == "java.lang.Float") {
            map?.values?.firstOrNull()?.toString()?.toFloat() as T
        } else if (clazz.name == "java.lang.Boolean") {
            map?.values?.firstOrNull()?.toString()?.toBoolean() as T
        } else if (clazz.name == "java.lang.Short") {
            map?.values?.firstOrNull()?.toString()?.toShort() as T
        } else if (clazz.name == "java.lang.Byte") {
            map?.values?.firstOrNull()?.toString()?.toByte() as T
        } else if (clazz.name == "java.lang.String") {
            map?.values?.firstOrNull()?.toString() as T
        } else if (clazz.name == "java.util.Date") {
            map?.values?.firstOrNull()?.toString()?.toLong()?.let { java.util.Date(it) } as T
        } else {
            try {
                map?.values?.firstOrNull()?.toString()?.let { clazz.cast(it) }
            } catch (e: Exception) {
                throw UnsupportedTypeException("Unsupported type: ${clazz.name}")
            }
        }
    }

    override fun update(sql: String, paramMap: Map<String, Any?>): Int {
        val (jdbcSql, jdbcParamList) = parseSqlStatement(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(jdbcSql)
        jdbcParamList.forEachIndexed { index, any ->
            ps.setObject(index + 1, any)
        }
        val result = ps.executeUpdate()
        ps.close()
        conn.close()
        return result
    }

    override fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray {
        val (newSql, newParamList) = convertSql(sql, paramMaps)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(newSql)
        newParamList.forEach { paramMap ->
            paramMap.forEachIndexed { index, any ->
                ps.setObject(index + 1, any)
            }
            ps.addBatch()
        }
        val result = ps.executeBatch()
        ps.close()
        conn.close()
        return result
    }

    companion object {
        fun DataSource?.wrapper(): BasicJdbcWrapper? {
            if (this == null) {
                return null
            }
            val wrapper = BasicJdbcWrapper()
            wrapper.ds = this
            return wrapper
        }

        private fun convertSql(sql: String, paramMaps: Array<Map<String, Any?>>): Pair<String, List<List<Any?>>> {
            val (jdbcSql) = parseSqlStatement(sql, paramMaps.first())
            val newParamList = mutableListOf<List<Any?>>()
            paramMaps.forEach {
                newParamList.add(parseSqlStatement(sql, it).jdbcParamList)
            }
            return Pair(jdbcSql, newParamList)
        }

        inline fun <reified T> transact(dataSource: DataSource, block: (DataSource) -> T): T {
            val res: T?
            val conn = dataSource.connection
            conn.autoCommit = false
            try {
                res = block(dataSource)
                conn.commit()
            } catch (e: Exception) {
                conn.rollback()
                throw IllegalStateException()
            } finally {
                conn.close()
            }
            return res!!
        }
    }
}
