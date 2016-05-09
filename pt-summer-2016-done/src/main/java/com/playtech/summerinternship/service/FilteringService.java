package com.playtech.summerinternship.service;

import com.playtech.summerinternship.domain.AggregatedMetric;
import com.playtech.summerinternship.domain.AggregatedValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.playtech.summerinternship.CalendarUtils.after;
import static com.playtech.summerinternship.CalendarUtils.before;

public class FilteringService {

    public static List<AggregatedMetric> filterMetrics(List<AggregatedMetric> metrics, String start, String end) {
        if(start == null || start.isEmpty() || end == null || start.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<AggregatedMetric> filtered = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(Long.valueOf(start));
        startDate.clear(Calendar.MILLISECOND);

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(Long.valueOf(end));
        endDate.clear(Calendar.MILLISECOND);

        for(AggregatedMetric m : metrics) {
            List<AggregatedValue> aggregatedValues = new ArrayList<>();
            Calendar metricTimestamp = Calendar.getInstance();
            for(AggregatedValue aggregatedValue : m.getDatapoints()) {
                metricTimestamp.setTimeInMillis(Long.valueOf(aggregatedValue.getTimestamp()));
                if(before(startDate, metricTimestamp) && after(endDate, metricTimestamp)) {
                    aggregatedValues.add(aggregatedValue.copy());
                }
            }

            if(!aggregatedValues.isEmpty()) {
                filtered.add(new AggregatedMetric(m.getName(), aggregatedValues));
            }
        }

        return filtered;
    }
}
