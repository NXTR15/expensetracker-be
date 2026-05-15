package com.nexstudio.expensetracker_be.util;

import java.util.Map;

public class StaticUtils {
    private StaticUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String REGEX = "^[A-Za-z0-9_!]+$";
    public static final Map<String, Integer> DEFAULT_LENGTH = Map.of(
            "username", 20,
            "password", 16
    );
}
