package com.kotoframework

import com.kotoframework.interfaces.KotoJdbcWrapper
import org.jdbi.v3.core.Jdbi
import javax.sql.DataSource

/**
 * Created by ousc on 2022/9/19 23:24
 */
class JdbiWrapper : KotoJdbcWrapper() {
    var jdbi: Jdbi? = null
    var dynamic: (() -> Jdbi)? = null
    fun getJdbi(jdbi: Jdbi? = null): Jdbi {
        return jdbi ?: this.jdbi ?: dynamic?.invoke() ?: throw RuntimeException("NamedParameterJdbcTemplate is null")
    }

    val dataSource: DataSource
        get() = (jdbi ?: dynamic?.invoke())?.withHandle<DataSource?, RuntimeException> {
            it.connection.unwrap(DataSource::class.java)
        } ?: throw RuntimeException("DataSource is null")

    override fun forList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> {
        return getJdbi().withHandle<List<Map<String, Any>>, RuntimeException> {
            it.createQuery(sql).bindMap(paramMap).mapToMap().list()
        }
    }

    override fun forMap(sql: String, paramMap: Map<String, Any?>): Map<String, Any>? {
        return getJdbi().withHandle<Map<String, Any>?, RuntimeException> {
            it.createQuery(sql).bindMap(paramMap).mapToMap().firstOrNull()
        }
    }

    override fun <T> forObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? {
        return getJdbi().withHandle<T?, RuntimeException> {
            it.createQuery(sql).bindMap(paramMap).mapTo(clazz).findOne().orElse(null)
        }
    }

    override fun update(sql: String, paramMap: Map<String, Any?>): Int {
        return getJdbi().withHandle<Int, RuntimeException> {
            it.createUpdate(sql).bindMap(paramMap).execute()
        }
    }

    override fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray {
        return getJdbi().withHandle<IntArray, RuntimeException> {
            it.prepareBatch(sql).use { batch ->
                paramMaps.forEach { paramMap ->
                    batch.add().bindMap(paramMap)
                }
                batch.execute()
            }
        }
    }

    override val url: String
        get() = dataSource.connection.metaData.url

    companion object {
        fun Jdbi?.wrapper(): JdbiWrapper? {
            if (this == null) {
                return null
            }
            val wrapper = JdbiWrapper()
            wrapper.jdbi = this
            return wrapper
        }
    }
}
