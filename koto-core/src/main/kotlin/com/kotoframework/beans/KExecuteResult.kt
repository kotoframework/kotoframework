package com.kotoframework.beans


/**
 * Created by ousc on 2022/5/28 16:15
 */

/**
 * Koto execute result
 *
 * @property affectRowNumber
 * @property lastInsertId
 * @constructor Create empty Koto execute result
 * @author ousc
 */
data class KExecuteResult(val affectRowNumber: Int, val lastInsertId: Int?)
