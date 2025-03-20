package com.marcosavard.commons.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private BufferedReader bufferedReader;
    private int nbHeaders = 1;
    private char headerSeparator = ';', separator = ';';
    private String commentCharacter = "#";
    private boolean hasNext = true;

    public static CsvReader of(Reader reader) {
        return new CsvReader(reader);
    }

    public static CsvReader of(Class<?> resourceClass, String resource, Charset charset) {
        return new CsvReader(resourceClass, resource, charset);
    }

    public CsvReader(Reader reader) {
        bufferedReader = new BufferedReader(reader);
    }

    public CsvReader(Class<?> resourceClass, String resource, Charset charset) {
        bufferedReader = new BufferedReader(new ResourceReader(resourceClass, resource, charset));
    }

    public CsvReader withHeader(int nbHeaders, char headerSeparator) {
        this.nbHeaders = nbHeaders;
        this.headerSeparator = headerSeparator;
        return this;
    }

    public CsvReader withSeparator(char separator) {
        this.separator = separator;
        return this;
    }

    public CsvReader withCommentCharacter(char commentCharacter) {
        this.commentCharacter = Character.toString(commentCharacter);
        return this;
    }

    /**
     * Read headers, in the case there are several lines of headers
     *
     * @return the list of columns
     * @throws IOException when I/O exception occurs
     */
    public List<String[]> readHeaders() throws IOException {
        List<String[]> headers = new ArrayList<>();

        for (int h = 0; h < nbHeaders; h++) {
            String[] columns = readNext(this.headerSeparator);
            headers.add(columns);
        }

        return headers;
    }

    /**
     * Tells if CSV has next rows to read.
     *
     * @return true if CSV has more rows to read.
     *
     */
    public boolean hasNext() {
        return hasNext;
    }

    public String[] readNext() throws IOException {
        return readNext(this.separator);
    }

    public String[] readNext(char separator) throws IOException {
        String[] line;

        do {
            line = readNotEmptyLine(separator);
        } while (line.length == 0 && hasNext);

        return line;
    }

    // private method
    private String[] readNotEmptyLine(char separator) throws IOException {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        String line = bufferedReader.readLine();

        if (line != null) {
            boolean comment = line.startsWith(commentCharacter);

            if (!comment) {
                char[] chars = line.toCharArray();

                for (char ch : chars) {
                    if (ch == '\"') {
                        inQuotes = !inQuotes;
                    } else if ((ch == separator) && !inQuotes) {
                        values.add(sb.toString().trim());
                        sb.setLength(0);
                    } else {
                        sb.append(ch);
                    }
                }

                if (!sb.toString().isEmpty()) {
                    values.add(sb.toString().trim());
                }
            }
        } else {
            hasNext = false;
        }

        String[] row = new String[values.size()];
        row = values.toArray(row);
        return row;
    }

    public void close() throws IOException {
        bufferedReader.close();
    }

    public List<String[]> readAll() throws IOException {
        List<String[]> lines = new ArrayList<>();

        do {
            String[] line = readNext(this.separator);
            if (line.length > 0) {
                lines.add(line);
            }
        } while (hasNext());

        return lines;
    }
}
