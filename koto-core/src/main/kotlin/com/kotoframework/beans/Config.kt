package com.kotoframework.beans

import com.kotoframework.utils.lineToHump

/**
 * Created by sundaiyue on 2023/1/28 18:21
 */

/**
 * Koto config
 *
 * @param type annotation type
 * @param enabled whether to enable this annotation
 * @param column column name
 * @constructor Create empty Koto config
 * @author ousc
 */
class Config(
    val type: String,
    var enabled: Boolean,
    var column: String
) {
    companion object {
        // global config
        internal val softDeleteConfig = Config("SoftDelete", false, "deleted")
        internal val createTimeConfig = Config("CreateTime", false, "create_time")
        internal val updateTimeConfig = Config("UpdateTime", false, "update_time")
        internal val deleteTimeConfig = Config("DeleteTime", false, "delete_time")
    }

    val alias get() = column.lineToHump()
}

