package com.kotoframework

import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc
import com.kotoframework.beans.Config.Companion.createTimeConfig
import com.kotoframework.beans.Config.Companion.deleteTimeConfig
import com.kotoframework.beans.Config.Companion.softDeleteConfig
import com.kotoframework.beans.Config.Companion.updateTimeConfig
import com.kotoframework.enums.DBType
import com.kotoframework.enums.NoValueStrategy

/**
 * Created by ousc on 2022/4/18 21:06
 */
object KotoApp {
    internal var hump2line: Boolean = true
    internal var kPojoSuffix: String = ""
    internal var dbType: DBType = MySql
    internal var defaultNoValueStrategy = Smart

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
