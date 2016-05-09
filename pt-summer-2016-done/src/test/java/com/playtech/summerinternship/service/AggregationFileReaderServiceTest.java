package com.playtech.summerinternship.service;

import com.playtech.summerinternship.config.PropertiesLoader;
import com.playtech.summerinternship.config.Utils;
import com.playtech.summerinternship.domain.AggregatedMetric;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

public class AggregationFileReaderServiceTest {

    @BeforeTest
    public void beforeTest() {
        PropertiesLoader.load();
    }

    @Test
    public void shouldReturnDataCorrectly() {
        String sourceDir = Utils.EXTERNAL_PROPERTIES.getProperty(Utils.KEYS.SOURCE_DIR);

        AggregatedMetric aggregatedMetric
                = AggregationFileReaderService.retrieveMetric(sourceDir, new File(sourceDir + "diceroll.1MinuteAvg"));

        assertEquals(aggregatedMetric.getName(), "diceroll.1MinuteAvg");
        assertEquals(aggregatedMetric.getDatapoints().size(), 1);
        assertEquals(aggregatedMetric.getDatapoints().get(0).getValue(), 9.5);
        assertEquals(aggregatedMetric.getDatapoints().get(0).getTimestamp(), "1462651620000");
    }
}
