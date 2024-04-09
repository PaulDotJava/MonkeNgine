package org.monkeg.api.util.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class Log {
    private static Logger logger;

    public static void init() {
        File file = new File("log4j2.xml");

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(file.toURI());

        logger  = (Logger) LogManager.getLogger(Logger.class);
    }

    public static void trace(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.trace(msg);
            case 1 -> logger.trace(msg, args[0]);
            case 2 -> logger.trace(msg, args[0], args[1]);
            case 3 -> logger.trace(msg, args[0], args[1], args[2]);
            case 4 -> logger.trace(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.trace(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.trace(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.trace(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.trace(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.trace(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }

    public static void debug(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.debug(msg);
            case 1 -> logger.debug(msg, args[0]);
            case 2 -> logger.debug(msg, args[0], args[1]);
            case 3 -> logger.debug(msg, args[0], args[1], args[2]);
            case 4 -> logger.debug(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.debug(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.debug(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.debug(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.debug(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.debug(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }

    public static void info(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.info(msg);
            case 1 -> logger.info(msg, args[0]);
            case 2 -> logger.info(msg, args[0], args[1]);
            case 3 -> logger.info(msg, args[0], args[1], args[2]);
            case 4 -> logger.info(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.info(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.info(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.info(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.info(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.info(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }

    public static void warn(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.warn(msg);
            case 1 -> logger.warn(msg, args[0]);
            case 2 -> logger.warn(msg, args[0], args[1]);
            case 3 -> logger.warn(msg, args[0], args[1], args[2]);
            case 4 -> logger.warn(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.warn(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.warn(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.warn(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.warn(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.warn(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }

    public static void error(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.error(msg);
            case 1 -> logger.error(msg, args[0]);
            case 2 -> logger.error(msg, args[0], args[1]);
            case 3 -> logger.error(msg, args[0], args[1], args[2]);
            case 4 -> logger.error(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.error(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.error(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.error(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.error(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.error(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }

    public static void fatal(String msg, Object... args) {
        assert(args.length <= 9) : "Too many arguments!";

        switch (args.length) {
            case 0 -> logger.fatal(msg);
            case 1 -> logger.fatal(msg, args[0]);
            case 2 -> logger.fatal(msg, args[0], args[1]);
            case 3 -> logger.fatal(msg, args[0], args[1], args[2]);
            case 4 -> logger.fatal(msg, args[0], args[1], args[2], args[3]);
            case 5 -> logger.fatal(msg, args[0], args[1], args[2], args[3], args[4]);
            case 6 -> logger.fatal(msg, args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7 -> logger.fatal(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8 -> logger.fatal(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 9 -> logger.fatal(msg, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
        }
    }
}
