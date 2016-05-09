package com.playtech.summerinternship.domain;

import java.io.Serializable;

/**
 * Instances of this class will store the average and the maximum for a given timestamp,
 * as well as no of metrics being aggregated.
 */
public class AvgMax implements Serializable {
    private int metricsCount;
    private double average;
    private int max;

    public AvgMax() {
    }

    public AvgMax(int value) {
        this.setAvgMax(value);
    }

    public int getMetricsCount() {
        return metricsCount;
    }

    public void setMetricsCount(int metricsCount) {
        this.metricsCount = metricsCount;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return this.average;
    }

    public int getMax() {
        return this.max;
    }

    public void setAvgMax(int value) {
        this.max = this.max < value ? value : this.max;
        this.average = (this.average * this.metricsCount + value) / (this.metricsCount + 1);
        this.metricsCount++;
    }

    @Override
    public String toString() {
        return "AvgMax{" +
                "metricsCount=" + metricsCount +
                ", average=" + average +
                ", max=" + max +
                '}';
    }
}
