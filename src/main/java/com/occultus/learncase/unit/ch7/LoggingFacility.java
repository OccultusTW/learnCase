package com.occultus.learncase.unit.ch7;

public class LoggingFacility {
    private static ILogger iLogger;

    public static void log(String text) {
        iLogger.log(text);
    }

    public static ILogger getiLogger() {
        return iLogger;
    }

    public static void setiLogger(ILogger iLogger) {
        LoggingFacility.iLogger = iLogger;
    }
}
