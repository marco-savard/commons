package com.marcosavard.commons.debug;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class OutputToggle {
    private static final PrintStream ORIGINAL = System.err;
    private static final PrintStream NULL_STREAM = new NullPrintStream();

    public static void disableSystemErr() {
        System.setErr(NULL_STREAM);
    }

    public static void enableSystemErr() {
        System.setErr(ORIGINAL);
    }

    private static class NullPrintStream extends PrintStream {

        public NullPrintStream() {
            super(new NullOutputStream());
        }

        private static class NullOutputStream extends ByteArrayOutputStream {
            @Override
            public void write(int b) {
                //do nothing
            }

            @Override
            public void write(byte[] b, int off, int len) {
                //do nothing
            }

            @Override
            public void writeTo(OutputStream out) {
                //do nothing
            }
        }
    }
}
