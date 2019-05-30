
package com.dzion.simpledb;

/**
 * Debug is a utility class that wraps println statements and allows
 * more or less command line output to be turned on.
 * <p>
 * Change the value of the DEBUG_LEVEL constant using a system property:
 * simpledb.Debug. For example, on the command line, use -Dsimpledb.Debug=x,
 * or simply -Dsimpledb.Debug to enable it at level 0.
 * The log(level, message, ...) method will print to standard output if the
 * level number is less than or equal to the currently set DEBUG_LEVEL.
 * Debug是一个实用程序类，它包装println语句并允许打开或多或少的命令行输出。
 * <p>
 * 使用系统属性更改DEBUG_LEVEL常量的值：simpledb.Debug。 例如，在命令行上，使用-Dsimpledb.Debug = x，或简单地使用-Dsimpledb.Debug在级别0启用它。
 * <p>
 * 如果级别编号小于或等于当前设置的DEBUG_LEVEL，则日志（级别，消息，...）方法将打印到标准输出。
 */

public class Debug {
    private static final int DEBUG_LEVEL;

    static {
        String debug = System.getProperty("simpledb.Debug");
        if (debug == null) {
            // No system property = disabled
            DEBUG_LEVEL = -1;
        } else if (debug == "") {
            // Empty property = level 0
            DEBUG_LEVEL = 0;
        } else {
            DEBUG_LEVEL = Integer.parseInt(debug);
        }
    }

    private static final int DEFAULT_LEVEL = 0;

    /**
     * Log message if the log level >= level. Uses printf.
     */
    public static void log(int level, String message, Object... args) {
        if (isEnabled(level)) {
            System.out.printf(message, args);
            System.out.println();
        }
    }

    /**
     * @return true if level is being logged.
     */
    public static boolean isEnabled(int level) {
        return level <= DEBUG_LEVEL;
    }

    /**
     * @return true if the default level is being logged.
     */
    public static boolean isEnabled() {
        return isEnabled(DEFAULT_LEVEL);
    }

    /**
     * Logs message at the default log level.
     */
    public static void log(String message, Object... args) {
        log(DEFAULT_LEVEL, message, args);
    }
}
