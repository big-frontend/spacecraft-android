package behavioral;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.Consumer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/03/2018  Fri
 */

public class JavaChainOfResponsibility {
    public enum LogLevel {
        INFO, DEBUG, WARNING, ERROR, FUNCTIONAL_MESSAGE, FUNCTIONAL_ERROR
    }

    @FunctionalInterface
    interface Logger {
        void message(String msg, LogLevel severity);

        default Logger appendNext(Logger nextLogger) {
            return (msg, severity) -> {
                message(msg, severity);
                nextLogger.message(msg, severity);
            };
        }

    }

    static Logger consoleLogger(LogLevel... levels) {
        return writeLogger(levels, msg -> System.err.println("Writing to console: " + msg));
    }

    static Logger emailLogger(LogLevel... levels) {
        return writeLogger(levels, msg -> System.err.println("Sending via email: " + msg));
    }

    static Logger fileLogger(LogLevel... levels) {
        return writeLogger(levels, msg -> System.err.println("Writing to Log File: " + msg));
    }


    static Logger writeLogger(LogLevel[] levels, Consumer<String> stringConsumer) {
        EnumSet<LogLevel> set = EnumSet.copyOf(Arrays.asList(levels));
        return (msg, severity) -> {
            if (set.contains(severity)) {
                stringConsumer.accept(msg);
            }
        };
    }

    public static void main(String[] args) {
        // Build an immutable chain of responsibility
        Logger logger = consoleLogger(LogLevel.values())
                .appendNext(emailLogger(LogLevel.FUNCTIONAL_MESSAGE, LogLevel.FUNCTIONAL_ERROR))
                .appendNext(fileLogger(LogLevel.WARNING, LogLevel.ERROR));

        // Handled by consoleLogger since the console has a LogLevel of all
        logger.message("Entering function ProcessOrder().", LogLevel.DEBUG);
        logger.message("Order record retrieved.", LogLevel.INFO);

        // Handled by consoleLogger and emailLogger since emailLogger implements Functional_Error & Functional_Message
        logger.message("Unable to Process Order ORD1 Dated D1 For Customer C1.", LogLevel.FUNCTIONAL_ERROR);
        logger.message("Order Dispatched.", LogLevel.FUNCTIONAL_MESSAGE);

        // Handled by consoleLogger and fileLogger since fileLogger implements Warning & Error
        logger.message("Customer Address details missing in Branch DataBase.", LogLevel.WARNING);
        logger.message("Customer Address details missing in Organization DataBase.", LogLevel.ERROR);
    }
}
