package com.haduc.quicklibbooksmanagement.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomCodeGenerator {
    private static final int LENGTH = 8;

    public static String generateRandomCode() {
        return RandomStringUtils.randomAlphanumeric(LENGTH).toUpperCase();
    }
}
