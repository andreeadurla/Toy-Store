package ro.sd.a2.utils;

import ro.sd.a2.entity.Product;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ApplicationUtils {

    public static String printPrettyDate(Date date) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String prettyDate = dateFormat.format(date);

        return prettyDate;
    }

    public static Date convertFromStringYMToDate(String dateString) {

        Date date = null;

        YearMonth yearMonth = YearMonth.parse(dateString);
        date = Date.from(yearMonth
                    .atDay(1)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());;

        return date;
    }

    public static String getCurrentDay() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        return strDate;
    }

    public static Date getStartOfDay(String dateString) {

        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime startOfDay = date.atTime(LocalTime.MIN);

        Instant instant = startOfDay.atZone(ZoneId.of("Europe/Bucharest")).toInstant();

        return Date.from(instant);
    }

    public static Date getEndOfDay(String dateString) {

        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Instant instant = endOfDay.atZone(ZoneId.of("Europe/Bucharest")).toInstant();

        return Date.from(instant);
    }

}
