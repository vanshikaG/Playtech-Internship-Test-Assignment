package com.playtech.summerinternship.domain;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class AvgMaxTest {

    @Test
    public void shouldComputeAvgMaxCorrectly() {
        AvgMax avgMax = new AvgMax(0);
        assertEquals(avgMax.getAverage(), 0.0);
        assertEquals(avgMax.getMax(), 0);
        for(int i = 1; i < 10; i++) {
            avgMax.setAvgMax(i);
        }
        assertEquals(avgMax.getAverage(), 4.5);
        assertEquals(avgMax.getMax(), 9);
    }
}
