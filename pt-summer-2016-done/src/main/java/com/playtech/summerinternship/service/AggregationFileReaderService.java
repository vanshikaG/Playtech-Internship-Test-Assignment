package com.playtech.summerinternship.service;

import com.playtech.summerinternship.config.Utils;
import com.playtech.summerinternship.domain.AggregatedMetric;
import com.playtech.summerinternship.domain.AggregatedValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AggregationFileReaderService {

    public static List<AggregatedMetric> read(String pattern, String start, String end) {
        String sourceDir = Utils.EXTERNAL_PROPERTIES.getProperty(Utils.KEYS.SOURCE_DIR);

        pattern = pattern.trim(); start = start.trim(); end = end.trim();
        List<AggregatedMetric> aggregatedMetrics = new ArrayList<>();

        List<File> response = listFiles(sourceDir, sourceDir, pattern);
        for(File f : response) {
            aggregatedMetrics.add(retrieveMetric(sourceDir, f));
        }

        List<AggregatedMetric> filteredMetrics = FilteringService.filterMetrics(aggregatedMetrics, start, end);

        return filteredMetrics;
    }

    public static AggregatedMetric retrieveMetric(String sourceDir, File file) {
        AggregatedMetric aggregatedMetric = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            aggregatedMetric = new AggregatedMetric(file.getPath().replace(sourceDir, "").replace("/", "."));
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                AggregatedValue aggregatedValue = new AggregatedValue(Double.valueOf(split[1]), split[0]);
                aggregatedMetric.addAggregatedValue(aggregatedValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aggregatedMetric;
    }

    public static List<File> listFiles(String sourceDir, String dir, String pattern) {
        List<File> response = new ArrayList<>();

        File[] files = new File(dir).listFiles();
        if(files == null) {
            files = new File[0];
        }
        for(File file: files){
            if(file.isFile()) {
                String path = file.getPath();
                String replaced = path.replace(sourceDir, "");
                if(replaced.matches(pattern)) {
                    response.add(file);
                }
            } else if(file.isDirectory()){
                response.addAll(listFiles(sourceDir, file.getAbsolutePath(), pattern));
            }
        }

        return response;
    }
}
