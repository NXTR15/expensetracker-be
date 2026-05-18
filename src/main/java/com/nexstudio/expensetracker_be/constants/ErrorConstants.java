package com.nexstudio.expensetracker_be.constants;

public class ErrorConstants {
    private ErrorConstants() {
        throw new IllegalStateException("Constant Class");
    }

    public static final String DATABASE_ERROR = "sys.error.998";
    public static final String GENERAL_ERROR = "sys.error.999";
    public static final String AUTHENTICATION_ERROR = "usr.error.001";
    public static final String USERNAME_VALIDATION_ERROR = "usr.error.002";
}
