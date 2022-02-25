package com.occultus.learncase.unit.ch7;

public class TimeLogger {
    public static String CreateMessage(String info) {
        return SystemTime.now().toString() + "" + info;
    }
}
