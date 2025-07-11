package com.marcosavard.common.text;

import com.marcosavard.common.text.format.DataNumberFormat;

import java.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

// Display : Only $9.99 for 10 GB, 10 Go pour seulement 9,99 $
public class MessageFormatDemo {
    private static final Locale[] DISPLAYS = new Locale[] {Locale.CANADA, Locale.CANADA_FRENCH};
    private static final String MESSAGES = "com.marcosavard.common.text.messages"; //refers to messages.properties

    //keys in messages.properties
    private static final String ONLY_PRICE_FOR_DATA_AMOUNT_MSG_KEY = "only0DollarsFor1DataAmount";
    private static final String ONLY_PRICE_FOR_DATA_AMOUNT_STR_KEY = "onlyStrDollarsForStrDataAmount";
    private static final String ERROR_CHOICE_KEY = "errorChoice";
    private static final String CABBAGE_CHOICE_KEY = "cabbageChoice";
    private static final String CHERRY_CHOICE_KEY = "cherryChoice";
    private static final String LOAF_OF_BREAD_CHOICE_KEY = "loafOfBreadChoice";
    private static final String GRAPE_LEAF_CHOICE_KEY = "grapeLeafChoice";

    public static void main(String[] args) {
        printMessageOnlyPriceForDataAmount();
        printMessageCartWithPlural();
        printMessageErrorWithPlural();
    }

    private static void printMessageOnlyPriceForDataAmount() {
        double moneyAmount = 9.99;
        long dataAmount = 20_000_000_000L;

        for (int i=0; i<DISPLAYS.length; i++) {
            printMessageUsingStringFormat(DISPLAYS[i], moneyAmount, dataAmount);
        }
        System.out.println();

        for (int i=0; i<DISPLAYS.length; i++) {
            printMessageUsingMessageFormat(DISPLAYS[i], moneyAmount, dataAmount);
        }
        System.out.println();
    }

    private static void printMessageUsingStringFormat(Locale display, double moneyAmount, long dataAmount) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(MESSAGES, display);
        String pattern = messageBundle.getString(ONLY_PRICE_FOR_DATA_AMOUNT_STR_KEY);

        NumberFormat priceFormat = DecimalFormat.getCurrencyInstance(display);
        NumberFormat dataNumberFormat = DataNumberFormat.getInstance(display);

        String price = priceFormat.format(moneyAmount);
        String data = dataNumberFormat.format(dataAmount);
        String lang = display.getDisplayLanguage(display);

        if ("fr".equals(display.getLanguage())) {
            System.out.println(lang + " : " + String.format(pattern, data, price));
        } else {
            System.out.println(lang + " : " + String.format(pattern, price, data));
        }
    }

    private static void printMessageUsingMessageFormat(Locale display, double moneyAmount, long dataAmount) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(MESSAGES, display);
        String pattern = messageBundle.getString(ONLY_PRICE_FOR_DATA_AMOUNT_MSG_KEY);

        NumberFormat priceFormat = DecimalFormat.getCurrencyInstance(display);
        NumberFormat dataNumberFormat = DataNumberFormat.getInstance(display);

        String price = priceFormat.format(moneyAmount);
        String data = dataNumberFormat.format(dataAmount);
        String lang = display.getDisplayLanguage(display);
        System.out.println(lang + " : " + MessageFormat.format(pattern, price, data));
    }

    private static void printMessageErrorWithPlural() {
        for (Locale display : DISPLAYS) {
            ResourceBundle messageBundle = ResourceBundle.getBundle(MESSAGES, display);
            ChoiceFormat errorCount = new ChoiceFormat(messageBundle.getString(ERROR_CHOICE_KEY));
            List<String> messages = new ArrayList<>();

            for (int i=0; i<5; i++) {
                messages.add(MessageFormat.format(errorCount.format(i), i));
            }

            String lang = display.getDisplayLanguage(display);
            String joined = String.join(", ", messages);
            System.out.println(MessageFormat.format("{0} : [{1}]", lang, joined));
        }

        System.out.println();
    }

    private static void printMessageCartWithPlural() {
        String[] products = new String[] {CABBAGE_CHOICE_KEY, CHERRY_CHOICE_KEY, LOAF_OF_BREAD_CHOICE_KEY, GRAPE_LEAF_CHOICE_KEY};
        int[] quantities = new int[] {2, 10, 3, 6};

        for (Locale display : DISPLAYS) {
            printMessageCartWithPlural(products, quantities, display);
        }
    }

    private static void printMessageCartWithPlural(String[] products, int[] quantities, Locale display) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(MESSAGES, display);
        List<String> cartProducts = new ArrayList<>();

        for (int i=0; i<products.length; i++) {
            int qty = quantities[i];
            ChoiceFormat productCount = new ChoiceFormat(messageBundle.getString(products[i]));
            cartProducts.add(MessageFormat.format(productCount.format(qty), qty));
        }

        String yourCartContains = messageBundle.getString("yourCartContains");
        String joined = String.join(", ", cartProducts);
        System.out.println(MessageFormat.format("{0} : [{1}]", yourCartContains, joined));
    }
}
