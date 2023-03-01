package com.softlocked.kitsuchat.core.logging;

import java.util.Calendar;
import java.util.logging.Level;

public class Logger {
    private static String formatTime(int time) {
        String str = String.valueOf(time);
        if (str.length() < 2) {
            str = "0" + str;
        }
        return str;
    }

    private static String format(String message, Level level) {
        return String.format("[%s:%s:%s] [%s] %s", formatTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)), formatTime(Calendar.getInstance().get(Calendar.MINUTE)), formatTime(Calendar.getInstance().get(Calendar.SECOND)), level.getName().replace("SEVERE", "ERROR"), message);
    }

    public static void info(String message, Object... args) {
        String formattedMessage = format(String.format(message, args), Level.INFO);

        System.out.println(formattedMessage);
    }

    public static void warn(String message, Object... args) {
        String formattedMessage = format(String.format(message, args), Level.WARNING);

        System.out.println(formattedMessage);
    }

    public static void error(String message, Object... args) {
        String formattedMessage = format(String.format(message, args), Level.SEVERE);

        System.err.println(formattedMessage);
    }
}
