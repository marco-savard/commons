package com.marcosavard.common.geog.ca.climat;

import com.marcosavard.common.io.reader.CsvReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RepertoireStationReader extends CsvReader  {
    private List<String[]> allRows;

    public RepertoireStationReader() throws IOException {
        super(RepertoireStationReader.class, "Normales_climatiques_canadiennes_1991_2020_repertoire_des_stations.csv", StandardCharsets.UTF_8);
        this.withHeader(1, ',');
        this.withSeparator(',');
        this.readHeaders();
    }

    public String[] readStation(String station) throws IOException {
        if (allRows == null) {
            allRows = super.readAll();
        }

        String stationName = station.replace('_', ' ');

        String[] row = allRows.stream()
                .filter(a -> stationName.contains(a[0]))
                .findFirst().orElse(null);

        String lat = row[7];
        String lon = row[8];
        String alt = row[9];
        return new String[] {lat, lon, alt};
    }
}
