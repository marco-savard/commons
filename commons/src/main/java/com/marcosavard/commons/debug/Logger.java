package com.marcosavard.commons.debug;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private enum Level {ERROR, WARNING, INFO};
    private static final Map<Class, Logger> loggers = new HashMap<>();
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String PATTERN = "{0} {1} [{2}:{3}] {4}.{5}() : {6}";
    private static final String CLASS_NAME = Logger.class.getName();

    private Class claz;

    public static Logger getLogger(Class claz) {
        Logger logger = loggers.get(claz);

        if (logger == null) {
            logger = new Logger(claz);
            loggers.put(claz, logger);
        }

        return logger;
    }

    private Logger(Class claz) {
        this.claz = claz;
    }

    public void error(String text) {
        log(Level.ERROR, text);
    }

    public void warning(String text) {
        log(Level.WARNING, text);
    }

    public void info(String text) {
        log(Level.INFO, text);
    }

    public void log(Level level, String text) {
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(TIME_FORMAT);

        StackTraceElement caller = findCaller();
        String filename = caller.getFileName();
        String line = Integer.toString(caller.getLineNumber());
        String className = caller.getClassName();
        String simpleName = getSimpleName(className);
        String methodName = caller.getMethodName();

        String msg = MessageFormat.format(PATTERN, time, level.name(), filename, line, simpleName, methodName, text);
        System.out.println(msg);
    }

    private String getSimpleName(String className) {
        int idx = className.lastIndexOf('.');
        return className.substring(idx+1);
    }

    private StackTraceElement findCaller() {
        Exception ex = new RuntimeException();
        StackTraceElement[] elements = ex.getStackTrace();
        StackTraceElement caller = null;

        for (int i=0; i<elements.length; i++) {
            caller = elements[i];
            if (! caller.getClassName().equals(CLASS_NAME)) {
                break;
            }
        }

        return caller;
    }

}
