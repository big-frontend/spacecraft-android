@file:JvmName("PExtension")
package com.jamesfchen.plugin

private const val ANSI_RESET = "\u001B[0m"
private const val ANSI_BLACK = "\u001B[30m"
private const val ANSI_RED = "\u001B[31m"
private const val ANSI_GREEN = "\u001B[32m"
private const val ANSI_YELLOW = "\u001B[33m"
private const val ANSI_BLUE = "\u001B[34m"
private const val ANSI_PURPLE = "\u001B[35m"
private const val ANSI_CYAN = "\u001B[36m"
private const val ANSI_WHITE = "\u001B[37m"

private const val ANSI_BLACK_BACKGROUND = "\u001B[40m"
private const val ANSI_RED_BACKGROUND = "\u001B[41m"
private const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
private const val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
private const val ANSI_BLUE_BACKGROUND = "\u001B[44m"
private const val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
private const val ANSI_CYAN_BACKGROUND = "\u001B[46m"
private const val ANSI_WHITE_BACKGROUND = "\u001B[47m"
fun error(message: Any) {
    println("$ANSI_RED  ${message} $ANSI_RESET")
}

fun warn(message: Any) {
    println("$ANSI_YELLOW  ${message} $ANSI_RESET")
}

fun info(message: Any) {
    println("$ANSI_GREEN ${message} $ANSI_RESET")
}

fun debug(message: Any) {
    println("$ANSI_BLUE  ${message} $ANSI_RESET")
}
fun  a(){

}