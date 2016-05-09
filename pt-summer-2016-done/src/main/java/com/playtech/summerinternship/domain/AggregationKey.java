package com.playtech.summerinternship.domain;

import java.io.Serializable;

/**
 * Instances of this class represents pair of metric timestamp and name
 * to be used in maps that store aggregations
 */
public class AggregationKey implements Serializable {
    private String path;
    private long timestamp;

    public AggregationKey() {
    }

    public AggregationKey(final String path, final long timestamp) {
        this.path = path;
        this.timestamp = timestamp;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregationKey aggregationKey = (AggregationKey) o;

        return timestamp == aggregationKey.timestamp && path.equals(aggregationKey.path);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AggregationKey{" +
                "path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
