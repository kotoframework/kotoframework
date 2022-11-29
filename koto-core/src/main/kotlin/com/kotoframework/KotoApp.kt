package com.kotoframework

import com.kotoframework.utils.Log
import com.kotoframework.utils.Jdbc

/**
 * Created by ousc on 2022/4/18 21:06
 */
object KotoApp {
    internal var softDeleteEnabled: Boolean = false
    internal var hump2line: Boolean = true
    internal var softDeleteColumn: String = "deleted"
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
        softDeleteEnabled = enabled
        if(softDeleteEnabled){
            softDeleteColumn = str
        } else {
            softDeleteColumn = ""
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
