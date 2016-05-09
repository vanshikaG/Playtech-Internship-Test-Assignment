package com.playtech.summerinternship.domain;

import java.util.Date;

public class Metric {

    private String path;
    private int value;
    private Date timestamp;

    public Metric(String message) {
        if(message == null) {
            throw new IllegalArgumentException();
        }
        String[] messageParts = message.split(" ");
        if(messageParts.length != 3) {
            throw new IllegalArgumentException();
        }

        this.path = messageParts[0];
        this.value = Integer.parseInt(messageParts[1]);
        this.timestamp = new Date(Long.parseLong(messageParts[2]));
    }

    public String getPath() {
        return path;
    }

    public int getValue() {
        return value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "path='" + path + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
