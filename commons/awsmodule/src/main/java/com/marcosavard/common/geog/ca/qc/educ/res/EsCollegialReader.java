package com.marcosavard.common.geog.ca.qc.educ.res;

import com.marcosavard.common.io.reader.CsvReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EsCollegialReader extends CsvReader  {
    public EsCollegialReader() throws IOException {
        super(EsCollegialReader.class, "es_collegial.csv", StandardCharsets.UTF_8);
        this.withHeader(1, ',');
        this.withSeparator(',');
        this.readHeaders();
    }
}
