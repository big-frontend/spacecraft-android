package com.jamesfchen.kotlin

import org.gradle.api.Project
import org.gradle.api.logging.Logger

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"
const val ANSI_BLACK_BACKGROUND = "\u001B[40m"
const val ANSI_RED_BACKGROUND = "\u001B[41m"
const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
const val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
const val ANSI_BLUE_BACKGROUND = "\u001B[44m"
const val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
const val ANSI_CYAN_BACKGROUND = "\u001B[46m"
const val ANSI_WHITE_BACKGROUND = "\u001B[47m"
private var logger: Logger? = null
fun init(project: Project) {
    logger = project.logger
}

private fun checkLogger() {
    requireNotNull(logger) { "没有初始化Logger" }
}

//    public static void lifecycle(String message) {
//        checkLogger();
//        logger.lifecycle(ANSI_YELLOW + "" + message + "" + ANSI_RESET);
//    }
//    public static void error(String message) {
//        checkLogger();
//        logger.error(message);
//    }
//    public static void warn(String message) {
//        checkLogger();
//        logger.warn(message );
//    }
//
//    public static void info(String message) {
//        checkLogger();
//        logger.info(message);
//    }
//
//    public static void debug(String message) {
//        checkLogger();
//        logger.debug(message);
//    }


fun error(message: Any) {
    println(ANSI_RED + "" + message + "" + ANSI_RESET)
}

fun warn(message: Any) {
    println(ANSI_YELLOW + "" + message + "" + ANSI_RESET)
}

fun info(message: Any) {
    println(ANSI_GREEN + "" + message + "" + ANSI_RESET)
}

fun debug(message: Any) {
    println(ANSI_BLUE + "" + message + "" + ANSI_RESET)
}

fun verbose(message: Any) {
    println(message)
}

fun child(msg: Any) {
    println("👶[ gradle 开始 ] $msg")
}

fun teenager(msg: Any) {
    println("👩‍🎓👨‍🎓[ initialzation ] $msg")
}

fun middleAge(msg: Any) {
    println("👰🤵[ configuration ] $msg")
}

fun theElderly(msg: Any) {
    println("👵👴[ gradle 结束 ] $msg")
}
