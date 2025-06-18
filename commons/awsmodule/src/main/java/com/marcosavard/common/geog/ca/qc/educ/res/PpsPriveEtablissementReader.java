package com.marcosavard.common.geog.ca.qc.educ.res;

import com.marcosavard.common.io.reader.CsvReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PpsPriveEtablissementReader extends CsvReader  {
    public PpsPriveEtablissementReader() throws IOException {
        super(PpsPriveEtablissementReader.class, "pps_prive_etablissement.csv", StandardCharsets.UTF_8);
        this.withHeader(1, ',');
        this.withSeparator(',');
        this.readHeaders();
    }
}
