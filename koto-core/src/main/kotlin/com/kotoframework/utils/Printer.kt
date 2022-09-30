package com.kotoframework.utils

import java.util.Arrays


/**
 * Created by ousc on 2022/4/12 17:12
 */
object Printer {
    /**
     * 分号
     */
    private const val SEMICOLON = ";"

    /**
     * 换行打印
     *
     * @param txt   信息
     * @param codes 格式化参数
     */
    fun outPrintln(txt: String, vararg codes: PrintCode) {
        println(format(txt, *codes))
    }

    /**
     * 不换行打印
     *
     * @param txt   打印内容
     * @param codes 格式化参数
     */
    fun outPrint(txt: String, vararg codes: PrintCode) {
        print(format(txt, *codes))
    }

    /**
     * 默认红色打印
     *
     * @param txt 信息
     */
    fun errorPrintln(txt: String) {
        System.err.println(format(txt, PrintCode.RED))
    }

    /**
     * 格式化信息
     *
     * @param txt   信息
     * @param codes 参数集合
     * @return 格式化后的信息
     */
    private fun format(txt: String, vararg codes: PrintCode): String {
        val codeStr = java.lang.String.join(
            SEMICOLON,
            *Arrays.stream(codes).map { printCode: PrintCode -> printCode.code.toString() }
                .toArray { Array(it) { "" } })
        return 27.toChar().toString() + "[" + codeStr + "m" + txt + 27.toChar() + "[0m"
    }


    /**
     * 控制台信息格式化参数
     */
    enum class PrintCode(val code: Int) {
        /**
         * 黑色
         */
        BLACK(30),

        /**
         * 黑色背景
         */
//        BLACK_BACKGROUND(40),

        /**
         * 红色
         */
        RED(31),

        /**
         * 红色背景
         */
//        RED_BACKGROUND(41),

        /**
         * 绿色
         */
        GREEN(32),

        /**
         * 绿色背景
         */
//        GREEN_BACKGROUND(42),

        /**
         * 黄色
         */
        YELLOW(33),

        /**
         * 黄色背景
         */
        YELLOW_BACKGROUND(43),

        /**
         * 蓝色
         */
        BLUE(34),

        /**
         * 蓝色背景
         */
        BLUE_BACKGROUND(44),

        /**
         * 品红（洋红）
         */
        MAGENTA(35),

        /**
         * 品红背景
         */
        MAGENTA_BACKGROUND(45),

        /**
         * 蓝绿
         */
        CYAN(36),

        /**
         * 蓝绿背景
         */
//        CYAN_BACKGROUND(46),

        /**
         * 灰色
         */
        GREY(37),

        /**
         * 灰色背景
         */
//        GREY_BACKGROUND(47),

        /**
         * 粗体
         */
        BOLD(1),

        /**
         * 斜体
         */
//        ITALIC(3),

        /**
         * 下划线
         */
//        UNDERLINE(4);

    }

    val BLACK = PrintCode.BLACK
//    val BLACK_BACKGROUND = PrintCode.BLACK_BACKGROUND

    val RED = PrintCode.RED
//    val RED_BACKGROUND = PrintCode.RED_BACKGROUND

    val GREEN = PrintCode.GREEN
//    val GREEN_BACKGROUND = PrintCode.GREEN_BACKGROUND

    val YELLOW = PrintCode.YELLOW
//    val YELLOW_BACKGROUND = PrintCode.YELLOW_BACKGROUND

    val BLUE = PrintCode.BLUE
//    val BLUE_BACKGROUND = PrintCode.BLUE_BACKGROUND

    val MAGENTA = PrintCode.MAGENTA
//    val MAGENTA_BACKGROUND = PrintCode.MAGENTA_BACKGROUND

    val CYAN = PrintCode.CYAN
//    val CYAN_BACKGROUND = PrintCode.CYAN_BACKGROUND

    val GREY = PrintCode.GREY
//    val GREY_BACKGROUND = PrintCode.GREY_BACKGROUND

    val BOLD = PrintCode.BOLD
//    val ITALIC = PrintCode.ITALIC
//    val UNDERLINE = PrintCode.UNDERLINE
}
