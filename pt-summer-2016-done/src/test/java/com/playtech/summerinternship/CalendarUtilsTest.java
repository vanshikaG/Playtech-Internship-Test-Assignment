package com.playtech.summerinternship;

import com.playtech.summerinternship.domain.AggregationType;
import org.testng.annotations.Test;

import java.util.Calendar;

import static org.testng.AssertJUnit.assertEquals;

public class CalendarUtilsTest {
    @Test
    public void shouldntHaveSecondsAndMilliseconds() {
        Calendar calendar = Calendar.getInstance();
        long l = CalendarUtils.clearTime(calendar.getTime(), AggregationType.MINUTE);

        Calendar response = Calendar.getInstance();
        response.setTimeInMillis(l);
        assertEquals(response.get(Calendar.SECOND), 0);
        assertEquals(response.get(Calendar.MILLISECOND), 0);
    }

    @Test
    public void shouldntHaveMilliseconds() {
        Calendar calendar = Calendar.getInstance();
        long l = CalendarUtils.clearTime(calendar.getTime(), AggregationType.MINUTE);

        Calendar response = Calendar.getInstance();
        response.setTimeInMillis(l);
        assertEquals(response.get(Calendar.MILLISECOND), 0);
    }
}
