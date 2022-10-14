package com.marcosavard.commons.io;

import java.io.Writer;
import java.text.MessageFormat;

public class FormatWriter extends IndentWriter {

    public FormatWriter(Writer w, int indentation) {
        super(w);
    }

    public FormatWriter(Writer w) {
        this(w, DEFAULT_INDENTATION);
    }

    public void println(String pattern, Object... items) {
        super.println(MessageFormat.format(pattern, items));
    }

}
