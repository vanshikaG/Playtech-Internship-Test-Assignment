package com.playtech.summerinternship.service;

import com.playtech.summerinternship.config.PropertiesLoader;
import com.playtech.summerinternship.config.Utils;
import com.playtech.summerinternship.domain.AggregatedMetric;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.playtech.summerinternship.CalendarUtils.after;
import static com.playtech.summerinternship.CalendarUtils.before;
import static org.testng.AssertJUnit.assertEquals;

public class FilteringServiceTest {

    @BeforeTest
    public void beforeTest() {
        PropertiesLoader.load();
    }

    @Test
    public void shouldReturnEmptyList_emptyMetricList() {
        List<AggregatedMetric> aggregatedMetrics
                = FilteringService.filterMetrics(new ArrayList<AggregatedMetric>(), "123465789", "123465789");

        assertEquals(aggregatedMetrics.size(), 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForInvalidDates() {
        FilteringService.filterMetrics(new ArrayList<AggregatedMetric>(), null, "123465789");
    }

    @Test
    public void shouldReturnCorrectData() {
        String sourceDir = Utils.EXTERNAL_PROPERTIES.getProperty(Utils.KEYS.SOURCE_DIR);

        final AggregatedMetric secondMetric
                = AggregationFileReaderService.retrieveMetric(sourceDir, new File(sourceDir + "diceroll.1SecondAvg"));
        final AggregatedMetric minuteMetric
                = AggregationFileReaderService.retrieveMetric(sourceDir, new File(sourceDir + "diceroll.1MinuteAvg"));

        List<AggregatedMetric> aggregatedMetrics = new ArrayList() {{
            add(secondMetric);
            add(minuteMetric);
        }};
        List<AggregatedMetric> filtered
                = FilteringService.filterMetrics(aggregatedMetrics, "1461162542693", "1461162682693");

        assertEquals(filtered.size(), 1);
        assertEquals(filtered.get(0).getName(), "diceroll.1SecondAvg");
        assertEquals(filtered.get(0).getDatapoints().get(0).getValue(), 4.5);
        assertEquals(filtered.get(0).getDatapoints().get(0).getTimestamp(), "1461162542000");

        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(Long.valueOf("1461162542693"));
        startDate.clear(Calendar.MILLISECOND);

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(Long.valueOf("1461162682693"));
        endDate.clear(Calendar.MILLISECOND);

        Calendar toBeCompared = Calendar.getInstance();
        toBeCompared.setTimeInMillis(Long.valueOf("1461162542000"));
        toBeCompared.clear(Calendar.MILLISECOND);

        AssertJUnit.assertTrue(before(startDate, toBeCompared));
        AssertJUnit.assertTrue(after(endDate, toBeCompared));
    }
}
