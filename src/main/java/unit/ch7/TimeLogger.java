package unit.ch7;

import java.time.LocalDate;

public class TimeLogger {
    public static String CreateMessage(String info) {
        return SystemTime.now().toString() + "" + info;
    }
}
