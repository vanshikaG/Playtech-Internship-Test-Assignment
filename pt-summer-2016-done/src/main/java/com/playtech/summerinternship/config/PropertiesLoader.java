package com.playtech.summerinternship.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static void load() {
        if(Utils.EXTERNAL_PROPERTIES == null) {
            try {
                String propFileName = "application.properties";

                InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(propFileName);

                if (inputStream != null) {
                    Utils.EXTERNAL_PROPERTIES = new Properties();
                    Utils.EXTERNAL_PROPERTIES.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
                inputStream.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}
