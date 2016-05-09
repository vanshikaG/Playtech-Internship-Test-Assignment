package com.playtech.summerinternship.internal.storage;

import com.playtech.summerinternship.config.Utils;
import com.playtech.summerinternship.domain.AggregationKey;
import com.playtech.summerinternship.domain.AggregationType;
import com.playtech.summerinternship.domain.AvgMax;
import com.playtech.summerinternship.domain.Metric;
import com.playtech.summerinternship.service.AggregationFileWriterService;

import java.io.File;
import java.io.IOException;

import static com.playtech.summerinternship.CalendarUtils.clearTime;

public class InternalProcessor {

    private static final String INTERNAL_STORAGE_BY_SECOND = "internal/storage.1Second";
    private static final String INTERNAL_STORAGE_BY_MINUTE = "internal/storage.1Minute";

    public static void aggregate(Metric metric) {
        AvgMax sec = aggregateBy(AggregationType.SECOND, metric, INTERNAL_STORAGE_BY_SECOND);
        AvgMax min = aggregateBy(AggregationType.MINUTE, metric, INTERNAL_STORAGE_BY_MINUTE);
        System.out.println(sec);
        System.out.println(min);
    }

    private static AvgMax aggregateBy(AggregationType type, Metric metric, String storagePath) {
        String sourceDir = Utils.EXTERNAL_PROPERTIES.getProperty(Utils.KEYS.SOURCE_DIR);
        File internalStorage = new File(sourceDir.concat(storagePath));
        InternalAggregations aggregation = new InternalAggregations(internalStorage);

        long cleanedTimestamp = clearTime(metric.getTimestamp(), type);
        AggregationKey key = new AggregationKey(metric.getPath(), cleanedTimestamp);
        if(aggregation.containsKey(key)) {
            aggregation.get(key).setAvgMax(metric.getValue());
        } else {
            aggregation.put(key, new AvgMax(metric.getValue()));
        }

        try {
            AggregationFileWriterService.save(aggregation, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        aggregation.save(internalStorage);

        return aggregation.get(key);
    }

}
