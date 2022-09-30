package com.kotoframework

import com.kotoframework.interfaces.KotoJdbcWrapper
import com.kotoframework.utils.Common.toKPojo
import org.apache.commons.dbcp2.BasicDataSource
import javax.sql.DataSource


/**
 * Created by ousc on 2022/9/19 23:24
 */
class PureJdbcWrapper : KotoJdbcWrapper() {
    var ds: DataSource? = null
    var dynamic: (() -> DataSource)? = null
    fun getDataSource(ds: DataSource? = null): DataSource {
        return ds ?: this.ds ?: dynamic?.invoke() ?: throw RuntimeException("DataSource is null")
    }

    val dataSource: BasicDataSource
        get() = ((ds ?: dynamic?.let { it() }) as BasicDataSource?) ?: throw RuntimeException("dataSource is null")

    override val url: String
        get() = dataSource.url

    override fun queryForList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> {
        val (newSql, newParamList) = convertSql(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(newSql)
        newParamList.forEachIndexed { index, any ->
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
        return list
    }

    override fun <T> queryForObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? {
        val (newSql, newParamList) = convertSql(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(newSql)
        newParamList.forEachIndexed { index, any ->
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
        return if (list.isEmpty()) null else list.first().toKPojo<T>(clazz)
    }

    override fun update(sql: String, paramMap: Map<String, Any?>): Int {
        val (newSql, newParamList) = convertSql(sql, paramMap)
        val conn = getDataSource().connection
        val ps = conn.prepareStatement(newSql)
        newParamList.forEachIndexed { index, any ->
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

    val dbName: String
        get() = (getDataSource() as BasicDataSource).url.split("?").first().split(
            "//"
        )[1].split("/")[1]

    companion object {
        fun DataSource?.wrapper(): PureJdbcWrapper? {
            if (this == null) {
                return null
            }
            val wrapper = PureJdbcWrapper()
            wrapper.ds = this
            return wrapper
        }

        internal fun convertSql(sql: String, paramMap: Map<String, Any?>): Pair<String, List<Any?>> {
            val newSql = StringBuilder()
            val newParamList = mutableListOf<Any?>()
            var index = 0
            while (index < sql.length) {
                val c = sql[index]
                if (c == ':') {
                    val start = index + 1
                    val end = sql.indexOf(' ', start)
                    val key = sql.substring(start, end)
                    newSql.append('?')
                    newParamList.add(paramMap[key])
                    index = end
                } else {
                    newSql.append(c)
                    index++
                }
            }
            return Pair(newSql.toString(), newParamList)
        }

        private fun convertSql(sql: String, paramMaps: Array<Map<String, Any?>>): Pair<String, List<List<Any?>>> {
            val newSql = convertSql(sql, paramMaps.first())
            val newParamList = mutableListOf<List<Any?>>()
            paramMaps.forEach {
                newParamList.add(convertSql(sql, it).second)
            }
            return Pair(newSql.first, newParamList)
        }
    }
}
