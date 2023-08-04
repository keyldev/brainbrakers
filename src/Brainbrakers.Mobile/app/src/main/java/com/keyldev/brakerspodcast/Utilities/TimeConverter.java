package com.keyldev.brakerspodcast.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeConverter {
    public static String fromMillisToHours(int millis){
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
    private static String[] phases = new String[]{
      "Доброе утро \uD83D\uDC4B",
            "Добрый день \uD83D\uDC40",
            "Добрый вечер \uD83C\uDF1A",
            "Доброй ночи \uD83C\uDF12"
    };
    public static String getTimeOfDay(){
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 5 && hourOfDay < 11) {
            return phases[0];
        } else if (hourOfDay >= 11 && hourOfDay < 15) {
            return phases[1];
        } else if (hourOfDay >= 15 && hourOfDay < 20) {
            return phases[2];
        } else {
            return phases[3];
        }
    }
}
