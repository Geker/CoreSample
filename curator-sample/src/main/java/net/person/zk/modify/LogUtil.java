package net.person.zk.modify;

import org.slf4j.Logger;

/**
 * <p> Title: LogUtil </p>
 * <p> Description: </p>
 * <p> Copyright: openlo.cn Copyright (C) 2016 </p>
 *
 * @author brokeneggs
 * @version
 * @since 2016年8月8日
 */
public class LogUtil {

    private static final String LOG_INFO_PATTERN = "## %s ##";
    private static final String LOG_ERROR_PATTERN = "!! %s !!";
    private static final String LOG_DEBUG_PATTERN = "## %s ##";
    private static final String LOG_WARN_PATTERN = "!! %s !!";

    public static void debug(Logger logger, String msg) {
        String logMsg = String.format(LOG_DEBUG_PATTERN, msg);
        logger.debug(logMsg);
    }

    public static void debug(Logger logger, String format, Object... args) {
        String logFormat = String.format(LOG_DEBUG_PATTERN, format);
        logger.debug(logFormat, args);
    }

    public static void info(Logger logger, String msg) {
        String logMsg = String.format(LOG_INFO_PATTERN, msg);
        logger.info(logMsg);
    }

    public static void info(Logger logger, String format, Object... args) {
        String logFormat = String.format(LOG_INFO_PATTERN, format);
        logger.info(logFormat, args);
    }

    public static void warn(Logger logger, String msg) {
        String logMsg = String.format(LOG_WARN_PATTERN, msg);
        logger.warn(logMsg);
    }

    public static void warn(Logger logger, String format, Object... args) {
        String logFormat = String.format(LOG_WARN_PATTERN, format);
        logger.warn(logFormat, args);
    }

    public static void warn(Logger logger, Exception e, String msg) {
        String logMsg = String.format(LOG_WARN_PATTERN, msg);
        logger.warn(logMsg, e);
    }

    public static void error(Logger logger, String msg) {
        String logMsg = String.format(LOG_ERROR_PATTERN, msg);
        logger.error(logMsg);
    }

    public static void error(Logger logger, String format, Object... args) {
        String logFormat = String.format(LOG_ERROR_PATTERN, format);
        logger.error(logFormat, args);
    }

    public static void error(Logger logger, Exception e, String msg) {
        String logMsg = String.format(LOG_ERROR_PATTERN, msg);
        logger.error(logMsg, e);
    }
}
