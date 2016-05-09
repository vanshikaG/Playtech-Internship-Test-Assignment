package com.playtech.summerinternship;

import com.playtech.summerinternship.domain.AggregationType;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    /**
     * This method clears time function of aggregation type.
     * @param date
     * @param type
     * @return
     */
    public static long clearTime(Date date, AggregationType type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        switch (type) {
            case MINUTE:
                cal.set(Calendar.SECOND, 0);
            case SECOND:
                cal.set(Calendar.MILLISECOND, 0);
                break;
        }

        return cal.getTimeInMillis();
    }

    /**
     * @param c1
     * @param c2
     * @return true if c1 before c2, false otherwise
     */
    public static boolean before(Calendar c1, Calendar c2) {
        return c1.before(c2) || c1.equals(c2);
    }

    /**
     * @param c1
     * @param c2
     * @return true if c1 after c2, false otherwise
     */
    public static boolean after(Calendar c1, Calendar c2) {
        return c1.after(c2) || c1.equals(c2);
    }
}
