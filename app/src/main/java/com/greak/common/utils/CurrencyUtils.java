package com.greak.common.utils;

import java.util.Locale;

public class CurrencyUtils {

    private static final Locale USD_LOCALE = Locale.US;
    private static final String MONEY_FORMAT_TWO_DIGITS_AFTER_DECIMAL = "%.2f";

    public static String formatCurrency(double value) {
        return String.format(USD_LOCALE, MONEY_FORMAT_TWO_DIGITS_AFTER_DECIMAL, value);
    }
}
