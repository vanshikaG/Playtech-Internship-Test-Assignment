package com.playtech.summerinternship.domain;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.AssertJUnit.assertEquals;

public class MetricTest {

    @Test
    public void shouldBuildMetricCorrectly() {
        Metric m = new Metric("local.random.diceroll 12 123456789");

        assertEquals(m.getPath(), "local.random.diceroll");
        assertEquals(m.getTimestamp(), new Date(123456789));
        assertEquals(m.getValue(), 12);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenMessageIsInWrongFormat() {
        new Metric("local.random.diceroll 123456789");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenMessageIsNull() {
        new Metric(null);
    }
}
