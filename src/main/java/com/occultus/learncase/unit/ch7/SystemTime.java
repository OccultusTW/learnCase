package com.occultus.learncase.unit.ch7;

import java.time.LocalDate;

public class SystemTime {
    private static LocalDate date;

    public static void setDate(LocalDate custom) {
        date = custom;
    }

    public static void reset() {
        date = LocalDate.MIN;
    }

    //如果有設定，就回傳假的時間，沒有就回傳 真實時間
    public static LocalDate now() {
        if(date != LocalDate.MIN) {
            return date;
        }
        return LocalDate.now();
    }
}
