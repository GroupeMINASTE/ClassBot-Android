package me.nathanfallet.classbot.extensions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringExtension {

    public static Date toDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        try {
            return format.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
