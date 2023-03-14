package com.kotoframework.beans

/**
 * Created by sundaiyue on 2022/11/30 00:23
 */

/**
 * Invalid sql exception
 * @param message
 * @constructor Create empty Invalid sql exception
 * @author ousc
 */
class InvalidSqlException(message: String) : RuntimeException(message)
