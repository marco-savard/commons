package com.marcosavard.awsmodule.climate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ClimateService {
    List<String> getStations() throws URISyntaxException, IOException;

    String getInfoForStation(String station) throws IOException;

    String getInfoForStation(String station, String month, String dayOfMonth) throws IOException;
}
