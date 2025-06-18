package com.marcosavard.awsmodule.climate;

import com.marcosavard.common.geog.ca.climat.NormalReader;
import com.marcosavard.common.geog.ca.climat.RepertoireStationReader;
import com.marcosavard.common.geog.ca.climat.StationFinder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClimateServiceImpl implements ClimateService {
    private StationFinder stationFinder = new StationFinder();

    @Override
    public List<String> getStations() throws URISyntaxException, IOException {
        return stationFinder.findStations();
    }

    @Override
    public String getInfoForStation(String station) throws IOException {
        NormalReader.NormalInfo info = stationFinder.findInfoForStation(station);

        List<String> fields = new ArrayList<>();
        fields.add("\"station\" : \"" + station + "\"");
        fields.add("\"dailyMaximumByMonth\" : \"" + Arrays.toString(info.dailyMaximums()) + "\"");
        fields.add("\"dailyMinimumByMonth\" : \"" + Arrays.toString(info.dailyMinimums()) + "\"");

        return "{" + String.join(", ", fields) + "}";
    }

    @Override
    public String getInfoForStation(String station, String monthNumber, String dayOfMonthNumber) throws IOException {
        RepertoireStationReader repertoireStationReader = new RepertoireStationReader();
        String[] stationInfo = repertoireStationReader.readStation(station);

        Month month = Month.of(Integer.parseInt(monthNumber));
        int dayOfMonth = Integer.parseInt(dayOfMonthNumber);
        LocalDate date = LocalDate.of(Year.now().getValue(), month, dayOfMonth);
        NormalReader.NormalInfo info = stationFinder.findInfoForStation(station);
        double maximum = getValue(info.dailyMaximums(), date);
        double minimum = getValue(info.dailyMinimums(), date);
        double nbDaysWithRain = getValue(info.nbDaysWithRain(), date);
        double nbDaysWithSnow = getValue(info.nbDaysWithSnow(), date);
        double nbDaysWithFreeze= getValue(info.nbDaysWithFreeze(), date);

        double rainProbability = (nbDaysWithRain * 100) / month.length(false);
        double snowProbability = (nbDaysWithSnow * 100) / month.length(false);
        double freezeProbability = (nbDaysWithFreeze * 100) / month.length(false);

        rainProbability = Math.round(rainProbability * 10) / 10.0;
        snowProbability = Math.round(snowProbability * 10) / 10.0;
        freezeProbability = Math.round(freezeProbability * 10) / 10.0;

        List<String> fields = new ArrayList<>();
        fields.add("\"station\" : \"" + station + "\"");
        fields.add("\"latitude\" : \"" + stationInfo[0] + "\"");
        fields.add("\"longitude\" : \"" + stationInfo[1] + "\"");
        fields.add("\"altitude\" : \"" + stationInfo[2] + "\"");
        fields.add("\"dailyMaximum\" : \"" + maximum + "\"");
        fields.add("\"dailyMinimum\" : \"" + minimum + "\"");
        fields.add("\"rainProbability\" : \"" + rainProbability + "\"");
        fields.add("\"snowProbability\" : \"" + snowProbability + "\"");
        fields.add("\"freezeProbability\" : \"" + freezeProbability + "\"");

        return "{" + String.join(", ", fields) + "}";
    }

    private double getValue(Double[] values, LocalDate date) {
        int year = date.getYear();
        int dayOfMonth = date.getDayOfMonth();
        int monthLength = date.getMonth().length(date.isLeapYear());
        boolean beforeMid = dayOfMonth <= monthLength/2;
        Month month1 = beforeMid ? date.getMonth().minus(1) : date.getMonth();
        Month month2 = beforeMid ? date.getMonth() : date.getMonth().plus(1);
        LocalDate date1 = LocalDate.of(month1.equals(Month.DECEMBER) ? year -1 : year, month1, 15);
        long daysSince = Duration.between(date1.atStartOfDay(), date.atStartOfDay()).toDays();
        double fractionOfMonth = daysSince / (double)monthLength;

        double value1 = (values == null) ? 0.0 : values[month1.getValue() - 1];
        double value2 = (values == null) ? 0.0 : values[month2.getValue() - 1];
        double value = value1 * (1-fractionOfMonth) + (value2 * fractionOfMonth);
        value = Math.round(value * 10) / 10.0;
        return value;
    }
}
