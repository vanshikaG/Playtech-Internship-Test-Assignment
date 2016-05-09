package com.playtech.summerinternship;

import com.playtech.summerinternship.config.PropertiesLoader;
import com.playtech.summerinternship.domain.Metric;
import com.playtech.summerinternship.internal.storage.InternalProcessor;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader.load();
        for(int i = 0; i < 3; i++) {
            Metric m = new Metric("local.random.diceroll " + 5 + " " + System.currentTimeMillis());
            InternalProcessor.aggregate(m);
            System.out.println();
        }
    }
}

