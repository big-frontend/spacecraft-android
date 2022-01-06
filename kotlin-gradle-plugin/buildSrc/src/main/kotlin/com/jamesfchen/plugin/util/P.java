package com.jamesfchen.plugin.util;

public final class P {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private static void println(Object message) {
        System.out.println(message);
    }

    public static void error(Object message) {
        println(ANSI_RED + "" + message + "" + ANSI_RESET);
    }

    public static void warn(Object message) {
        println(ANSI_YELLOW + "" + message + "" + ANSI_RESET);
    }

    public static void info(Object message) {
        println(ANSI_GREEN + "" + message + "" + ANSI_RESET);
    }

    public static void debug(Object message) {
        println(ANSI_BLUE + "" + message + "" + ANSI_RESET);
    }

    public static void verbose(Object message) {
        println(message);
    }
    public static void child(Object msg){
        println("👶[ gradle 开始 ] "+msg);
    }
    public static void teenager(Object msg){
        println("👩‍🎓👨‍🎓[ initialzation ] "+msg);
    }
    public static void middleAge(Object msg){
        println("👰🤵[ configuration ] "+msg);
    }
    public static void theElderly(Object msg){
        println("👵👴[ gradle 结束 ] "+msg);
    }
}
