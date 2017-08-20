package com.dg.watcher.base;

import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;


public class Log {
    public static void err(String tag, String msg) {
        log(SEVERE, tag, msg);
    }

    public static void warn(String tag, String msg) {
        log(WARNING, tag, msg);
    }

    public static void info(String tag, String msg) {
        log(INFO, tag, msg);
    }

    private static void log(Level level, String tag, String msg) {
        getLogger(tag).log(level, msg);
    }
}