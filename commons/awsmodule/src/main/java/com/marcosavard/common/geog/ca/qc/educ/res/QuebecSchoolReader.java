package com.marcosavard.common.geog.ca.qc.educ.res;

import com.marcosavard.common.io.reader.CsvReader;

import java.nio.charset.Charset;

public class QuebecSchoolReader extends CsvReader {
    private static final Charset WINDOWS_1252 = Charset.forName("windows-1252");

    public QuebecSchoolReader() {
        super(QuebecSchoolReader.class, "QuebecSchools.csv", WINDOWS_1252);
        withHeader(1, ';');
        withSeparator(';');
    }
}
