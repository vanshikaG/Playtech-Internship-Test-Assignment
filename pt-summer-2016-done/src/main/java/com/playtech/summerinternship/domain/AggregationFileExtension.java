package com.playtech.summerinternship.domain;

/**
 * Created by gibran on 5/7/16.
 */
public enum AggregationFileExtension {
    AVG_SECOND("1SecongAvg"), AVG_MINUTE("1MinuteAvg"),
    MAX_SECOND("1SecondMax"), MAX_MINUTE("1MinuteMax");

    private String extension;

    AggregationFileExtension(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
