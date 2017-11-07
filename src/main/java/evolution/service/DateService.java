package evolution.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class DateService {

    @Value("${custom.date.format}")
    private String format;

    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormat.forPattern(format);
    }

    public String getStringDate(Long time) {
        DateTime dateTime = new DateTime(time);
        return dateTime.toString(getDateTimeFormatter());
    }

    public String getStringDateUTC(Long time) {
        DateTime dateTime = new DateTime(time, DateTimeZone.UTC);
        return dateTime.toString(getDateTimeFormatter());
    }

    public String getStringDate(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.toString(getDateTimeFormatter());
    }

    public String getStringDateUTC(Date date) {
        DateTime dateTime = new DateTime(date.getTime(), DateTimeZone.UTC);
        return dateTime.toString(getDateTimeFormatter());
    }

    public DateTime dateToDatetimeUTC(Date date) {
        return new DateTime(date.getTime(), DateTimeZone.UTC);
    }

    public DateTime dateToDatetimeLocale(Date date) {
        return new DateTime(date.getTime());
    }

    public Date dateTimeToDateLocal(DateTime dateTime) {
        return new Date(dateTime.getMillis());
    }

    public Date getCurrentDateInUTC() {
        Long a =  DateTime.now(DateTimeZone.UTC).getMillis();
        return new Date(a);
    }

}
