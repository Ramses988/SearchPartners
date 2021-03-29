package com.search_partners.util;

import com.search_partners.model.Post;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static void getDuration(Post post) {
        LocalDateTime date = LocalDateTime.now();
        long value = post.getDate().until(date, ChronoUnit.MONTHS);
        if (value >= 1) {
            post.setDurationShort(value + " мес");
            post.setDuration(getText(value, " месяц", " месяца", " месяцев"));
            return;
        }
        value = post.getDate().until(date, ChronoUnit.DAYS);
        if (value >= 1) {
            post.setDurationShort(value + " дн");
            post.setDuration(getText(value, " день", " дня", " дней"));
            return;
        }
        value = post.getDate().until(date, ChronoUnit.HOURS);
        if (value >= 1) {
            post.setDurationShort(value + " час");
            post.setDuration(getText(value, " час", " часа", " часов"));
            return;
        }
        value = post.getDate().until(date, ChronoUnit.MINUTES);
        if (value >= 1) {
            post.setDurationShort(value + " мин");
            post.setDuration(getText(value, " минуту", " минуты", " минут"));
            return;
        }
        post.setDurationShort("только что");
        post.setDuration("только что");
    }

    private static String getText(long num, String form1, String form2, String form3) {
        num = Math.abs(num) % 100;
        long num1 = num % 10;
        if (num > 10 && num < 20) return num + form3;
        if (num1 > 1 && num1 < 5) return num + form2;
        if (num1 == 1) return num + form1;
        return num + form3;
    }

}
