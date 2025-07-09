package com.marcosavard.common.text;

import com.marcosavard.common.text.format.DataNumberFormat;

import java.text.*;
import java.util.Locale;
import java.util.ResourceBundle;

// Display : Only $9.99 for 10 GB, 10 Go pour seulement 9,99 $
public class MessageFormatDemo {
    private static final Locale[] DISPLAYS = new Locale[] {Locale.CANADA, Locale.CANADA_FRENCH};
    private static final String MESSAGES = "com.marcosavard.common.text.messages"; //refers to messages.properties
    private static final String ONLY_PRICE_FOR_DATA_AMOUNT_KEY = "onlyPriceForDataAmount"; //key in messages.properties

    public static void main(String[] args) {
        double moneyAmount = 9.99;
        long dataAmount = 20_000_000_000L;

        for (int i=0; i<DISPLAYS.length; i++) {
            Locale display = DISPLAYS[i];
            printMessage(display, moneyAmount, dataAmount);
        }
    }

    private static void printMessage(Locale display, double moneyAmount, long dataAmount) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(MESSAGES, display);
        String pattern = messageBundle.getString(ONLY_PRICE_FOR_DATA_AMOUNT_KEY);

        NumberFormat priceFormat = DecimalFormat.getCurrencyInstance(display);
        NumberFormat dataNumberFormat = DataNumberFormat.getInstance(display);

        String price = priceFormat.format(moneyAmount);
        String data = dataNumberFormat.format(dataAmount);
        String lang = display.getDisplayLanguage(display);
        System.out.println(MessageFormat.format(pattern, lang, price, data));
    }
}
