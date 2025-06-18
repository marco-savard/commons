package com.marcosavard.common.geog.ca.climat;

import com.marcosavard.common.debug.Console;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StationFinderDemo {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String station = "QC_QUEBEC_JEAN_LESAGE_(AÉROPORT)";
        RepertoireStationReader repertoireStationReader = new RepertoireStationReader();
        repertoireStationReader.readStation(station);

        StationFinder stationFinder = new StationFinder();
        List<String> stations = stationFinder.findStations();
        System.out.println(stations);

        NormalReader.NormalInfo info = stationFinder.findInfoForStation(station);

      //  double nbDaysWithRain = getValue(info.nbDaysWithRain(), date);
      //  double nbDaysWithSnow = getValue(info.nbDaysWithSnow(), date);
       // double nbDaysWithFreeze= getValue(info.nbDaysWithFreeze(), date);

        Console.println(station);
        Console.println(info.dailyMaximums());
        Console.println(info.dailyMinimums());

       // url.toURI().
    }
}
