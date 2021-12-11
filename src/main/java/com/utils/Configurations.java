package com.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Configurations {
    private static final Logger LOGGER = Logger.getLogger(Configurations.class.getName());
    private static Configurations instance;
    private final Properties properties = new Properties();

    private Configurations() {}

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public String getRequestURL() {
        return getProperties("starting URL");
    }

    public String getProperties(String key) {
        loadFile("config.properties");
        return properties.getProperty(key);
    }

    private void loadFile(String src) {
        try (FileInputStream inputStream = new FileInputStream(new File(Objects.requireNonNull(getClass().getClassLoader().getResource(src)).toURI()))) {
            properties.load(inputStream);
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Check if list is sorted
     * @param listOfStrings The list to check in
     * @param <T> The type of objects in the list
     * @return true - if list is sorted. Otherwise false
     */
    public <T extends Comparable<T>> boolean isSorted(List<T> listOfStrings) {
        if (listOfStrings.isEmpty() || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<T> iter = listOfStrings.iterator();
        T current;
        T previous = iter.next();

        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public String getRandomString() {
        return RandomStringUtils.randomAlphabetic(20);
    }
}

