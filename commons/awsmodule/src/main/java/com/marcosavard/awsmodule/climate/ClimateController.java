package com.marcosavard.awsmodule.climate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ClimateController {
    private final ClimateService climateService;

    @Autowired
    public ClimateController(ClimateService climateService) {
        this.climateService = climateService;
        System.out.println("ClimateService");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/climate")
    public String climate() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Type /climate/stations to list all the stations.");
        printWriter.println("Type /climate/station/{station} for stats about a given {station}.");
        printWriter.println("Type /climate/station/{station}/{month}/{dayOfMonth} for stats about a given {station} at a given date.");
        return stringWriter.toString();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/climate/stations")
    public String climateStations() {
        String result;

        try {
            List<String> stations = climateService.getStations();
            result = "{" + String.join(", ", stations) + "}";
        } catch (URISyntaxException e) {
            result = e.toString();
        } catch (IOException e) {
            result = e.toString();
        }

        return result;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/climate/station/{station}")
    public String climateStation(@PathVariable("station") String station) {
        String result;

        try {
            result = climateService.getInfoForStation(station);
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/climate/station/{station}/{month}/{dayOfMonth}")
    public String climateStationDate(@PathVariable("station") String station,
                                     @PathVariable("month") String month,
                                     @PathVariable("dayOfMonth") String dayOfMonth) {
        String result;

        try {
            result = climateService.getInfoForStation(station, month, dayOfMonth);
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

}
