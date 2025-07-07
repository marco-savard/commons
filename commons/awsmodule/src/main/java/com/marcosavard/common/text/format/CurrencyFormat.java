package com.marcosavard.common.text.format;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormat extends NumberFormat {
    enum Style {
      SYMBOL,
      CURRENCY_CODE,
      DISPLAY_NAME
    }

    private final Locale display;
    //private final Style style;
    private final Style style;
    private final NumberFormat numberFormat;
    private Currency currency = Currency.getInstance(Locale.US);
    private String symbol;

    public CurrencyFormat(Locale display, Style style) {
        this.display = display;
        this.style = style;
        this.numberFormat = DecimalFormat.getCurrencyInstance(display);
        this.symbol = currency.getSymbol(display);
    }

    public static NumberFormat getCurrencyInstance(Locale display, Style style) {
        NumberFormat numberFormat = new CurrencyFormat(display, style); //DecimalFormat.getCurrencyInstance(display);
        numberFormat.setCurrency(Currency.getInstance(Locale.US));
        return numberFormat;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
        this.symbol = currency.getSymbol(display);
        this.numberFormat.setCurrency(currency);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        String formatted = numberFormat.format(number);

        if (style == Style.CURRENCY_CODE) {
            String replacement = currency.getCurrencyCode();
            formatted = formatted.replace(symbol, replacement);
        } else if (style == Style.DISPLAY_NAME) {
            String replacement = currency.getDisplayName(display);
            formatted = formatted.replace(symbol, replacement);
        }

        return toAppendTo.append(formatted);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return format(number, toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return 0;
    }

    @Override
    public Number parse(String formatted) {
        return 0;
    }
}
