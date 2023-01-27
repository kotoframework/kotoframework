package com.kotoframework

import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc
import com.kotoframework.KotoApp.Config.Companion.createTimeConfig
import com.kotoframework.KotoApp.Config.Companion.deleteTimeConfig
import com.kotoframework.KotoApp.Config.Companion.softDeleteConfig
import com.kotoframework.KotoApp.Config.Companion.updateTimeConfig
import com.kotoframework.core.annotations.CreateTime
import com.kotoframework.core.annotations.DeleteTime
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.UpdateTime
import com.kotoframework.utils.Extension.lineToHump
import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/4/18 21:06
 */
object KotoApp {
    class Config(
        val type: KClass<*>,
        var enabled: Boolean,
        var column: String
    ) {
        companion object {
            internal val softDeleteConfig = Config(SoftDelete::class, false, "deleted")
            internal val createTimeConfig = Config(CreateTime::class, false, "create_time")
            internal val updateTimeConfig = Config(UpdateTime::class, false, "update_time")
            internal val deleteTimeConfig = Config(DeleteTime::class, false, "delete_time")
        }
        val alias get() = column.lineToHump()
    }

    internal var hump2line: Boolean = true
    internal var kPojoSuffix: String = ""
    internal var dbType: DBType = MySql
    internal var defaultNoValueStrategy = Ignore

    /*
    * 'console' or path to logFile, split with ","
     */
    fun setLog(out: String? = "console", simple: Boolean = false): KotoApp {
        Log.out = out
        Log.simpleLog = simple
        return this
    }

    fun setSoftDelete(enabled: Boolean = true, str: String = "deleted"): KotoApp {
        softDeleteConfig.enabled = enabled
        if (enabled) {
            softDeleteConfig.column = str
        } else {
            softDeleteConfig.column = ""
        }
        return this
    }

    fun setCreateTime(enabled: Boolean = true, str: String = "create_time"): KotoApp {
        createTimeConfig.enabled = enabled
        if (enabled) {
            createTimeConfig.column = str
        } else {
            createTimeConfig.column = ""
        }
        return this
    }

    fun setUpdateTime(enabled: Boolean = true, str: String = "update_time"): KotoApp {
        updateTimeConfig.enabled = enabled
        if (enabled) {
            updateTimeConfig.column = str
        } else {
            updateTimeConfig.column = ""
        }
        return this
    }

    fun setDeleteTime(enabled: Boolean = true, str: String = "delete_time"): KotoApp {
        deleteTimeConfig.enabled = enabled
        if (enabled) {
            deleteTimeConfig.column = str
        } else {
            deleteTimeConfig.column = ""
        }
        return this
    }

    fun setKPojoSuffix(suffix: String): KotoApp {
        kPojoSuffix = suffix
        return this
    }

    fun setHump2line(enabled: Boolean): KotoApp {
        hump2line = enabled
        return this
    }

    fun setDBType(dbType: DBType): KotoApp {
        this.dbType = dbType
        return this
    }

    fun setNoValueStrategy(noValueStrategy: NoValueStrategy): KotoApp {
        defaultNoValueStrategy = noValueStrategy
        return this
    }

    fun clearTableMap() {
        Jdbc.tableMap.clear()
    }
}
