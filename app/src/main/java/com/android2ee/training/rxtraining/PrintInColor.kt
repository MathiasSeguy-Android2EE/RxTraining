package com.android2ee.training.rxtraining

import java.io.PrintStream


// 
/** Created by Mathias Seguy also known as Android2ee on 20/03/2020.
 * The goal of this class is to :
 *
 */

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"

fun println(message: String, color: String) {
    println(color + message + ANSI_RESET)
    System.out
}

fun PrintStream.printLn(message: String, color: String) {
    println(color + message + ANSI_RESET)
    System.out
}