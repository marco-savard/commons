package com.marcosavard.common.geog.ca.climat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StationFinder {
    static final String PREFIX = "fr_1991-2020_Normales_";

    public List<String> findStations() throws URISyntaxException, IOException {
        URL url = StationFinder.class.getResource(".");
        Path path = Paths.get(url.toURI());
        List<String> stations = Files.list(path)
                .map(Path::toString)
                .filter(s -> s.endsWith(".csv"))
                .map(s -> s.substring(1 + s.lastIndexOf('\\')))
                .filter(s -> s.startsWith(PREFIX))
                .map(s -> s.substring(PREFIX.length(), s.lastIndexOf('.')))
                .toList();

        return stations;
    }

    public NormalReader.NormalInfo findInfoForStation(String station) throws IOException {
        NormalReader reader = new NormalReader();
        NormalReader.NormalInfo info = reader.loadNormals(station);
        return info;
    }
}
