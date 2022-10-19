package com.marcosavard.commons.io;

import java.io.Writer;
import java.text.MessageFormat;

public class FormatWriter extends IndentWriter {

    public FormatWriter(Writer w, int indentation) {
        super(w, indentation);
    }

    public FormatWriter(Writer w) {
        this(w, DEFAULT_INDENTATION);
    }

    public void print(String pattern, Object... items) {
        super.print(MessageFormat.format(pattern, items));
    }

    public void println(String pattern, Object... items) {
        super.println(MessageFormat.format(pattern, items));
    }

}
