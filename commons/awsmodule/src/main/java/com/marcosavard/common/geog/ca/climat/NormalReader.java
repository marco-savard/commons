package com.marcosavard.common.geog.ca.climat;

import com.marcosavard.common.io.reader.ResourceReader;
import com.marcosavard.common.lang.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NormalReader {
    private static final char DOUBLE_QUOTE = '"';

    public enum Station {
        RIMOUSKI("QC_RIMOUSKI", "Rimouski"),
        BAGOTVILLE("QC_BAGOTVILLE", "Saguenay"),
        ROBERVAL("QC_ROBERVAL", "Roberval"),
        QC("QC_QUEBEC_JEAN_LESAGE_(AÉROPORT)", "Québec"),
        ST_GEORGES("QC_ST_GEORGES", "Saint-Georges"),
        TROIS_RIVIERES("QC_TROIS-RIVIERES", "Trois-Rivières"),
        DRUMMONDVILLE("QC_DRUMMONDVILLE", "Drummondville"),
        SHERBROOKE("QC_SHERBROOKE", "Sherbrooke"),
        MTL("QC_MONTREAL_TRUDEAU_(AÉROPORT)", "Montréal"),
        OTTAWA("ON_OTTAWA_(AÉROPORT)", "Gatineau"),
        VAL_DOR("QC_VAL-D'OR", "Val d'or"),
        ROUYN("QC_ROUYN", "Rouyn"),

        ILES_MAD("QC_ILES_DE_LA_MADELEINE", "Iles-de-la-Madeleine"),
        SEPT_ILES("QC_SEPT-ILES", "Sept-Îles"),
        BAIE_COMEAU("QC_BAIE-COMEAU", "Baie-Comeau"),
        GASPE("QC_GASPE", "Gaspé"),

        PARC_L("QC_FORET_MONTMORENCY", "la forêt Montmorency"),
        CAP_T("QC_CAP-TOURMENTE", "Cap-Tourmente"),
        LA_POCA("QC_LA_POCATIERE", "La Pocatière"),
        LAUZON("QC_LAUZON", "Lévis"),
        STE_FOY("QC_STE-FOY_(U._LAVAL)", "Québec"),
        SCOTT("QC_SCOTT", "Scott"),
        THETFO("QC_THETFORD_MINES", "Thetford Mines"),
        VICTO("QC_ARTHABASKA", "Victoriaville"),

        JOVITE("QC_ST-JOVITE", "Saint-Jovite"),
        LACHUTE("QC_LACHUTE", "Lachute"),
        ASSOMP("QC_L'ASSOMPTION", "L'assomption"),
        ACADIE("QC_L'ACADIE", "L'acadie"),
        ANICET("QC_ST-ANICET", "Saint-Anicet"),
        GRANBY("QC_GRANBY", "Granby"),
        FRELIGHSBURG("QC_FRELIGHSBURG", "Frelighsburg");
        private String id, displayName;

        Station(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        public String getResource() {
            return MessageFormat.format("fr_1991-2020_Normales_{0}.csv", id);
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    private List<Station> stations = new ArrayList<>();
    private Map<Station, Double[]> dailyMaximum = new HashMap<>();
    private Map<Station, Double[]> dailyMinimum = new HashMap<>();
    private Map<Station, Double[]> extremeMinimum = new HashMap<>();
    private Map<Station, LocalDate[]> extremeMinimumDate = new HashMap<>();
    private Map<Station, Double[]> extremeMaximum = new HashMap<>();
    private Map<Station, LocalDate[]> extremeMaximumDate = new HashMap<>();

    public double getDailyMaximum(Station station, LocalDate date) throws IOException {
        return getDailyLimit(station, date, true);
    }

    public double getDailyMinimum(Station station, LocalDate date) throws IOException {
        return getDailyLimit(station, date, false);
    }

    private double getDailyLimit(Station station, LocalDate date, boolean isMax) throws IOException {
        loadNormals(station);
        int year = date.getYear();
        int dayOfMonth = date.getDayOfMonth();
        int monthLength = date.getMonth().length(date.isLeapYear());
        boolean beforeMid = dayOfMonth <= monthLength/2;
        Month month1 = beforeMid ? date.getMonth().minus(1) : date.getMonth();
        Month month2 = beforeMid ? date.getMonth() : date.getMonth().plus(1);
        LocalDate date1 = LocalDate.of(month1.equals(Month.DECEMBER) ? year -1 : year, month1, 15);
        long daysSince = Duration.between(date1.atStartOfDay(), date.atStartOfDay()).toDays();
        double fractionOfMonth = daysSince / (double)monthLength;

        Map<Station, Double[]> dailyLimit = isMax? dailyMaximum : dailyMinimum;
        double limit1 = dailyLimit.get(station)[month1.getValue() - 1];
        double limit2 = dailyLimit.get(station)[month2.getValue() - 1];
        double limit = limit1 * (1-fractionOfMonth) + (limit2 * fractionOfMonth);
        limit = Math.round(limit * 10) / 10.0;
        return limit;
    }

    public double getDailyMinimumOld(Station station, LocalDate date) throws IOException {
        loadNormals(station);
        Month month = date.getMonth();
        return dailyMinimum.get(station)[month.getValue() - 1];
    }

    public double getExtremeMaximum(Station station, Month month) throws IOException {
        loadNormals(station);
        return extremeMaximum.get(station)[month.getValue() - 1];
    }

    public LocalDate getExtremeMaximumDate(Station station, Month month) throws IOException {
        loadNormals(station);
        LocalDate[] dates = extremeMaximumDate.get(station);
        return dates[month.getValue() - 1];
    }

    public LocalDate getExtremeMinimumDate(Station station, Month month) throws IOException {
        loadNormals(station);
        LocalDate[] dates = extremeMinimumDate.get(station);
        return dates[month.getValue() - 1];
    }

    public double getExtremeMinimum(Station station, Month month) throws IOException {
        loadNormals(station);
        return extremeMinimum.get(station)[month.getValue() - 1];
    }

    public void loadNormals(Station station) throws IOException {
        if (! stations.contains(station)) {
            stations.add(station);
            Reader reader = new ResourceReader(NormalReaderDemo.class, station.getResource(), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            readAll(station, bf);
        }
    }

    public NormalInfo loadNormals(String stationName) throws IOException {
        String resourceName = StationFinder.PREFIX + stationName + ".csv";
        Reader reader = new ResourceReader(NormalReaderDemo.class, resourceName, StandardCharsets.UTF_8);
        BufferedReader bf = new BufferedReader(reader);
        Double[] dailyMaximums = null, dailyMinimums = null;
        Double[] nbDaysWithRain = null, nbDaysWithSnow= null, nbDaysWithFreeze = null;
        String line;

        do {
            line = bf.readLine();

            if (line != null) {
                String[] data = split(',', line);

                if (data[4].equals("\"Maximum quotidien (°C)\"")) {
                    dailyMaximums = toDouble(data);
                } else  if (data[4].equals("\"Minimum quotidien (°C)\"")) {
                    dailyMinimums = toDouble(data);
                } else  if (data[4].equals("\"Jours avec précipitation >= 5 mm\"")) {
                    nbDaysWithRain = toDouble(data);
                } else  if (data[4].equals("\"Jours avec neige >= 5 cm\"")) {
                    nbDaysWithSnow = toDouble(data);
                } else  if (data[4].equals("\"Jours avec température minimale <= 0 °C\"")) {
                    nbDaysWithFreeze = toDouble(data);
                }
            }
        } while (line != null);

        NormalInfo normalInfo = new NormalInfo(dailyMaximums, dailyMinimums, nbDaysWithRain, nbDaysWithSnow, nbDaysWithFreeze);
        return normalInfo;
    }

    public void loadAll() throws IOException {
        Station[] stations = Station.values();

        for (Station station : stations) {
            loadNormals(station);
        }
    }

    private void readAll(Station station, BufferedReader bf) throws IOException {
        String line;

        do {
            line = bf.readLine();

            if (line != null) {
                String[] data = split(',', line);

                if (data[4].equals("\"Maximum quotidien (°C)\"")) {
                    dailyMaximum.put(station, toDouble(data));
                } else  if (data[4].equals("\"Minimum quotidien (°C)\"")) {
                    dailyMinimum.put(station, toDouble(data));
                } else  if (data[4].equals("\"Maximum extrême (°C)\"")) {
                    extremeMaximum.put(station, toDouble(data));
                } else if (data[4].equals("\"Maximum extrême (°C) (aaaa/mm/jj)\"")) {
                    extremeMaximumDate.put(station, toLocalDate(data));
                } else if (data[4].equals("\"Minimum extrême (°C)\"")) {
                    extremeMinimum.put(station, toDouble(data));
                } else if (data[4].equals("\"Minimum extrême (°C) (aaaa/mm/jj)\"")) {
                    extremeMinimumDate.put(station, toLocalDate(data));
                }
            }
        } while (line != null);
    }

    private Double[] toDouble(String[] data) {
        Double[] values = new Double[12];
        for (int i=0; i<12; i++) {
            String value = (data == null) ? null : StringUtil.unquote(data[5+i]).toString();
            value = (value == null) ? null : value.replace(',', '.').replace(" ", "");
            values[i] = (value == null) || value.isBlank() ? 0.0 : Double.parseDouble(value);
        }

        return values;
    }

    private LocalDate[] toLocalDate(String[] data) {
        LocalDate[] dates = new LocalDate[12];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"yyyy/MM/dd\"");
        for (int i=0; i<12; i++) {
            dates[i] = LocalDate.parse(data[5+i], formatter);
        }

        return dates;
    }

    private String[] split(char separator, String line) {
        List<String> list = new ArrayList<>();
        StringBuilder currentSegment = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == DOUBLE_QUOTE) {
                insideQuotes = !insideQuotes; //toggle
                currentSegment.append(c);
            } else if (c == separator && !insideQuotes) {
                list.add(currentSegment.toString());
                currentSegment.setLength(0);
            } else {
                currentSegment.append(c);
            }
        }

        return list.toArray(new String[0]);
    }

    public List<NormalEvent> findExtremeMinimums(LocalDate current, int maxCount) {
        return findEvents(current, maxCount, extremeMinimum, extremeMinimumDate, false);
    }

    public List<NormalEvent> findExtremeMaximums(LocalDate current, int maxCount) {
        return findEvents(current, maxCount, extremeMaximum, extremeMaximumDate, true);
    }

    public List<NormalEvent> findDailyMinMaxSpots(LocalDate date, int maxCount, boolean isMax) throws IOException {
        List<NormalEvent> events = new ArrayList<>();
        int month = date.getMonth().getValue();
        int day = date.getDayOfYear();

        for (Station station : stations) {
            double value = getDailyLimit(station, date, isMax);
            NormalEvent event = new NormalEvent(station, value, date, day, isMax);
            events.add(event);
        }

        Comparator<NormalEvent> comparator = Comparator
                .comparing(NormalEvent::getValue);

        events = events.stream().sorted(comparator).toList();
        events = (events.size() > maxCount) ? events.subList(0, maxCount) : events;
        return events;
    }


    public List<NormalEvent> findEvents(LocalDate current, int maxCount, Map<Station, Double[]> extremes, Map<Station, LocalDate[]> extremeDates, boolean isMax) {
        int month = current.getMonth().getValue();
        int doy = current.getDayOfYear();
        List<NormalEvent> events = new ArrayList<>();

        for (Station station : stations) {
            double value = extremes.get(station)[month-1];
            LocalDate date = extremeDates.get(station)[month-1];
            NormalEvent event = new NormalEvent(station, value, date, doy, isMax);
            events.add(event);
        }

        Comparator<NormalEvent> comparator = Comparator
                .comparing(NormalEvent::getDiffDay)
                .thenComparing(NormalEvent::getDisc);

        events = events.stream().sorted(comparator).toList();
        events = (events.size() > maxCount) ? events.subList(0, maxCount) : events;
        return events;
    }

    public record NormalInfo(Double[] dailyMaximums,
                             Double[] dailyMinimums,
                             Double[] nbDaysWithRain,
                             Double[] nbDaysWithSnow,
                             Double[] nbDaysWithFreeze) {};

    public static class NormalEvent {
        private Station station;
        private double value;
        private LocalDate date;
        private int diffDay = 0;
        private boolean isMax;

        public NormalEvent(Station station, double value, LocalDate date, int dayOfYear, boolean isMax) {
            this.station = station;
            this.value = value;
            this.date = date;
            this.diffDay = Math.abs(dayOfYear - date.getDayOfYear());
            this.isMax = isMax;
        }

        public int getDiffDay() {
            return diffDay;
        }

        public double getValue() {
            return value;
        }

        public double getDisc() {
            return (value + 100L) * (isMax ? -1 : 1);
        }

        public LocalDate getDate() {
            return date;
        }

        public String getStationName() {
            return station.getDisplayName();
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0} : {1} ", station, value);
        }
    }


}
