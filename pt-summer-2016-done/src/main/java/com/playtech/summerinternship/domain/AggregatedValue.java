package com.playtech.summerinternship.domain;

import java.util.Date;

public class AggregatedValue {

    private double value;
    private String timestamp;

    public AggregatedValue(double value, String timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public AggregatedValue copy() {
        return new AggregatedValue(this.value, this.timestamp);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AggregatedValue{" +
                "value=" + value +
                ", timestamp=" + new Date(Long.valueOf(timestamp)) +
                '}';
    }

}
