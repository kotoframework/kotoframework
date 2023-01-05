package com.kotoframework.beans

import com.kotoframework.utils.Common.currentDate
import com.kotoframework.utils.Printer
import java.io.File
import java.io.FileWriter

/**
 * Created by sundaiyue on 2022/11/12 14:21
 */
class LogLine(
    private val text: String,
    private val codes: Array<Printer.PrintCode> = arrayOf(),
    var endLine: Boolean = false,
){
    fun endl(): LogLine {
        endLine = true
        return this
    }

    fun print(){
        if (endLine) {
            Printer.outPrintln(text, *codes)
        } else {
            Printer.outPrint(text, *codes)
        }
    }

    fun write(path: String){
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
