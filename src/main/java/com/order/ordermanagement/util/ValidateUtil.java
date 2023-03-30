package com.order.ordermanagement.util;

import java.util.regex.Pattern;

public class ValidateUtil {
    private final static Pattern emailPattern = Pattern.compile("^(.+)@(\\S+)$");

    public static void validateEmail(String email) {
        boolean validEmail = emailPattern.matcher(email).matches();
        if (!validEmail) {
            throw new IllegalArgumentException("Email is not valid");
        }
    }

    public static void validatePassword(String password) {
        if (password.length() < 9) {
            throw new IllegalArgumentException("Password is not valid");
        }
    }
}
