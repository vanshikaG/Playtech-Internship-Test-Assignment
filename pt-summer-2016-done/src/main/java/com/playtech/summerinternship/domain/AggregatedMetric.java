package com.playtech.summerinternship.domain;

import java.util.ArrayList;
import java.util.List;

public class AggregatedMetric {
    private String name;
    private List<AggregatedValue> datapoints;

    public AggregatedMetric(String name) {
        this.name = name;
        this.datapoints = new ArrayList<>();
    }

    public AggregatedMetric(String name, List<AggregatedValue> datapoints) {
        this.name = name;
        this.datapoints = datapoints;
    }

    public String getName() {
        return name;
    }

    public List<AggregatedValue> getDatapoints() {
        return datapoints;
    }

    public void addAggregatedValue(AggregatedValue aggregatedValue) {
        this.datapoints.add(aggregatedValue);
    }

    @Override
    public String toString() {
        return "AggregatedMetric{" +
                "name='" + name + '\'' +
                ", datapoints=" + datapoints +
                '}';
    }
}
