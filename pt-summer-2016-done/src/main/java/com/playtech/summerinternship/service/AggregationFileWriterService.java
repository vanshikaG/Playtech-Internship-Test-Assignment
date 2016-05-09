package com.playtech.summerinternship.service;

import com.playtech.summerinternship.config.Utils;
import com.playtech.summerinternship.domain.AggregationFileExtension;
import com.playtech.summerinternship.domain.AggregationKey;
import com.playtech.summerinternship.domain.AggregationType;
import com.playtech.summerinternship.domain.AvgMax;
import com.playtech.summerinternship.internal.storage.InternalAggregations;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for saving in memory aggregated data
 */
public class AggregationFileWriterService {
    private static class StoredFileLine {
        private long timestamp;
        private double avg;
        private int max;

        public StoredFileLine(long timestamp, double avg, int max) {
            this.timestamp = timestamp;
            this.avg = avg;
            this.max = max;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public double getAvg() {
            return avg;
        }

        public int getMax() {
            return max;
        }
    }

    public static void save(final InternalAggregations aggregation, AggregationType type) throws IOException {
        Map<String, List<StoredFileLine>> toStore = new HashMap<>();
        for(Map.Entry<AggregationKey, AvgMax> entry : aggregation.entrySet()) {
            StoredFileLine storedFileLine
                    = new StoredFileLine(entry.getKey().getTimestamp(), entry.getValue().getAverage(), entry.getValue().getMax());
            if(toStore.containsKey(entry.getKey().getPath())) {
                toStore.get(entry.getKey().getPath()).add(storedFileLine);
            } else {
                List<StoredFileLine> lines = new ArrayList<>();
                lines.add(storedFileLine);
                toStore.put(entry.getKey().getPath(), lines);
            }
        }

        for(Map.Entry<String, List<StoredFileLine>> entry : toStore.entrySet()) {
            String[] paths = buildPath(type, entry.getKey());
            writeAggregations(paths[0], entry.getValue(), true);
            writeAggregations(paths[1], entry.getValue(), false);
        }
    }

    private static String[] buildPath(AggregationType type, String metricPath) {
        String _path = metricPath.replaceAll("\\.", "/");

        String sourceDir = Utils.EXTERNAL_PROPERTIES.getProperty(Utils.KEYS.SOURCE_DIR);
        _path = sourceDir + _path;
        File directories = new File(_path.substring(0, _path.lastIndexOf("/")));
        directories.mkdirs();

        String avgPath = _path.concat(".");
        String maxPath = _path.concat(".");
        switch (type) {
            case SECOND:
                avgPath = avgPath.concat(AggregationFileExtension.AVG_SECOND.getExtension());
                maxPath = maxPath.concat(AggregationFileExtension.MAX_SECOND.getExtension());
                break;
            case MINUTE:
                avgPath = avgPath.concat(AggregationFileExtension.AVG_MINUTE.getExtension());
                maxPath = maxPath.concat(AggregationFileExtension.MAX_MINUTE.getExtension());
                break;
        }

        return new String[] {avgPath, maxPath};
    }

    private static void writeAggregations(final String path, final List<StoredFileLine> lines, boolean isAverage) throws IOException {
        File f = new File(path);
        if(!f.exists()) {
            f.createNewFile();
        }
        Writer output = new BufferedWriter(new FileWriter(path, false));
        for(StoredFileLine line : lines) {
            String l = line.getTimestamp() + " ";
            l = l.concat(String.valueOf(isAverage ? line.getAvg() : line.getMax()));
            output.append(l);
            output.append('\n');
        }
        output.close();
    }
}
