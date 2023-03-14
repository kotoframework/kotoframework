package com.kotoframework.beans

import com.kotoframework.core.annotations.CreateTime
import com.kotoframework.core.annotations.DeleteTime
import com.kotoframework.core.annotations.SoftDelete
import com.kotoframework.core.annotations.UpdateTime
import com.kotoframework.utils.Extension.lineToHump
import kotlin.reflect.KClass

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
    val type: KClass<*>,
    var enabled: Boolean,
    var column: String
) {
    companion object {
        // global config
        internal val softDeleteConfig = Config(SoftDelete::class, false, "deleted")
        internal val createTimeConfig = Config(CreateTime::class, false, "create_time")
        internal val updateTimeConfig = Config(UpdateTime::class, false, "update_time")
        internal val deleteTimeConfig = Config(DeleteTime::class, false, "delete_time")
    }
    val alias get() = column.lineToHump()
}

