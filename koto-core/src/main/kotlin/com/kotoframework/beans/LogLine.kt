package com.kotoframework.beans

import com.kotoframework.utils.Common.currentDate
import com.kotoframework.utils.Printer
import java.io.File
import java.io.FileWriter

/**
 * Created by sundaiyue on 2022/11/12 14:21
 */

/**
 * Log line
 * @property text
 * @property codes
 * @property endLine
 * @constructor Create empty Log line
 * @author ousc
 */
class LogLine(
    private val text: String,
    private val codes: Array<Printer.PrintCode> = arrayOf(),
    var endLine: Boolean = false,
) {

    /**
     * Endl log line
     *
     * @return Log line
     */
    fun endl(): LogLine {
        endLine = true
        return this
    }

    /**
     * Print
     *
     * @return Log line
     */
    fun print() {
        if (endLine) {
            Printer.outPrintln(text, *codes)
        } else {
            Printer.outPrint(text, *codes)
        }
    }

    /**
     * Write
     *
     * @param path
     */
    fun write(path: String) {
        val file = File(path, "koto-${currentDate}.log")
        if (!file.exists()) {
            file.createNewFile()
        }
        val writer = FileWriter(file, true)
        if (endLine) {
            writer.write(text + "\r")
        } else {
            writer.write(text)
        }
        writer.flush()
        writer.close()
    }
}
