package com.marcosavard.library.poi.word;

import com.marcosavard.commons.astro.moon.MoonPosition;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.time.Holiday;
import com.marcosavard.commons.time.calendar.Season;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.Point;
import java.io.*;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalendarCreatorDemo {

    public static void main(String[] args) {
        //setting
        Locale display = Locale.FRENCH;
        LocalDate today = LocalDate.now(); //LocalDate.of(2025, Month.DECEMBER, 1);

        //set input, output file
        String inputFilePath = "calendar.template.docx";
        String outputFilePath = "calendar.docx";
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        List<CalendarEvent> events = findCalendarEvents(today, display);

        try (OutputStream output = new FileOutputStream(outputFile)) {
            XWPFDocument document = new XWPFDocument();
            addTitle(document, today, display);
            Map<Integer, Point> gridPositionByDay = findGridPositions(today);
            XWPFTable table = addCalendar(document, today, gridPositionByDay, display);
            addCalendarEvents(table, today, gridPositionByDay, events);
            addUpcomingEvents(document, today, events, display);
            document.write(output);
            Console.println("String replacement completed. Output file: {0}", outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<CalendarEvent> findCalendarEvents(LocalDate date, Locale display) {
        Month month = date.getMonth();
        int year = date.getYear();
        int yearNextMont = (month == Month.DECEMBER) ? year + 1: year;
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);
        LocalDate firstOfNextMonth = LocalDate.of(yearNextMont, month.plus(1), 1);
        List<CalendarEvent> events = new ArrayList<>();

        //add moon phases
        addMoonPhases(events, firstOfMonth, display);
        addMoonPhases(events, firstOfNextMonth, display);

        //compute next equinox/solstice and add it
        Season.EventOccurence occurence = Season.findNextEvent(firstOfMonth);
        addSeasonEvent(events, occurence, display);

        //compute next holidays
        List<Holiday.Occurence> occurences = Holiday.findNextOccurences(firstOfMonth);
        addHolidays(events, occurences, display);

        events = events.stream().sorted().toList();
        return events;
    }

    private static void addMoonPhases(List<CalendarEvent> events, LocalDate sinceDate, Locale display) {
        //compute moon phases
        LocalDate newMoon = MoonPosition.findNextNewMoon(sinceDate).toLocalDate();
        LocalDate firstQuarter = MoonPosition.findNextFirstQuarter(sinceDate).toLocalDate();
        LocalDate fullMoon = MoonPosition.findNextFullMoon(sinceDate).toLocalDate();
        LocalDate lastQuarter = MoonPosition.findNextLastQuarter(sinceDate).toLocalDate();

        //add moon phases
        addMoonPhaseEvent(events, newMoon, MoonPosition.Phase.NEW, display);
        addMoonPhaseEvent(events, firstQuarter,MoonPosition.Phase.FIRST_QUARTER, display);
        addMoonPhaseEvent(events, fullMoon, MoonPosition.Phase.FULL, display);
        addMoonPhaseEvent(events, lastQuarter, MoonPosition.Phase.THIRD_QUARTER, display);
    }

    private static void addMoonPhaseEvent(List<CalendarEvent> events, LocalDate date, MoonPosition.Phase phase, Locale display) {
        String displayName = phase.getDisplayName(display);
        CalendarEvent event = new CalendarEvent(date, displayName, phase.getCodePoint());
        events.add(event);
    }

    private static void addSeasonEvent(List<CalendarEvent> events, Season.EventOccurence occurence, Locale display) {
        String displayName = occurence.getSeasonEvent().getDisplayName(display);
        CalendarEvent event = new CalendarEvent(occurence.getDateTime().toLocalDate(), displayName, occurence.getSeasonEvent().getCodePoint());
        events.add(event);
    }

    private static void addHolidays(List<CalendarEvent> events, List<Holiday.Occurence> occurences, Locale display) {
        for (Holiday.Occurence occurence : occurences) {
            Holiday holiday = occurence.getHoliday();
            String displayName = holiday.getDisplayName(display);
            CalendarEvent event = new CalendarEvent(occurence.getDate(), displayName, holiday.getCodePoint());
            events.add(event);
        }
    }


    private static Map<Integer, Point> findGridPositions(LocalDate date) {
        LocalDate firstOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int days = YearMonth.from(date).lengthOfMonth();
        Map<Integer, Point> gridPositionByDay = new HashMap<>();

        for (int d=0; d<days; d++) {
            int idx = dayOfWeek + d;
            int row = (idx / 7) + 1;
            int col = idx % 7;
            gridPositionByDay.put(d+1, new Point(row, col));
        }

        return gridPositionByDay;
    }


    private static void addTitle(XWPFDocument document, LocalDate date, Locale display) {
        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", display);
        String title = StringUtil.capitalize(date.format(formatter));

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        run.setFontSize(30);
        run.setText(title);
        run.addCarriageReturn();
    }

    private static XWPFTable addCalendar(XWPFDocument document, LocalDate date, Map<Integer, Point> gridPositionByDay, Locale display) {
        //how many weeks
        LocalDate firstOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int days = YearMonth.from(date).lengthOfMonth();
        int rows = 1 + (int)Math.ceil((dayOfWeek + days) / 7.0);

        //cells are rectangle
        int cellWidth = 1200, cellHeight = 1000;
        int tableWidth = 7 * (cellWidth + 20);

        XWPFTable table = document.createTable(rows, 7);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth(tableWidth);

        for (int i=0; i<rows; i++) {
            int height = (i == 0) ? 400 : cellHeight;
            XWPFTableRow row = table.getRow(i);
            row.setHeight(height);
        }

        fillHeader(table, display);
        fillGrid(table, date, gridPositionByDay, display);
        return table;
    }

    private static void fillHeader(XWPFTable table, Locale display) {
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(display);
        String[] weekdays = symbols.getShortWeekdays();
        XWPFTableRow header = table.getRow(0);

        for (int i=0; i<7; i++) {
            String text = weekdays[i+1].toUpperCase().replace(".", " ");

            XWPFTableCell cell = header.getCell(i);
            XWPFParagraph titleParagraph = cell.getParagraphs().get(0);
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            titleParagraph.setVerticalAlignment(TextAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(text);
            titleRun.setFontSize(20);
        }
    }

    private static void fillGrid(XWPFTable table, LocalDate date, Map<Integer, Point> gridPositionByDay, Locale display) {
        int days = YearMonth.from(date).lengthOfMonth();

        for (int d=1; d<=days; d++) {
            Point gridPos = gridPositionByDay.get(d);
            int row = gridPos.x;
            int col = gridPos.y;
            fillCell(table, row, col, d);
        }
    }

    private static Point fillCell(XWPFTable table, int rowId, int col, int day) {
        Point gridPos = new Point(rowId, col);
        String text = Integer.toString(day);

        XWPFTableRow row = table.getRow(rowId);
        XWPFTableCell cell = row.getCell(col);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        XWPFRun titleRun = paragraph.createRun();
        titleRun.setText(text);
        titleRun.setFontSize(20);
        return gridPos;
    }

    private static void addCalendarEvents(XWPFTable table, LocalDate currentDate, Map<Integer, Point> gridPositionByDay, List<CalendarEvent> events) {
       for (CalendarEvent event : events) {
           LocalDate date = event.getDate();

           if (date.getYear() == currentDate.getYear()) {
               if (date.getMonth() == currentDate.getMonth()) {
                   Point gridPos = gridPositionByDay.get(date.getDayOfMonth());
                   addCalendarEvent(table, gridPos, event);
               }
           }
       }
    }

    private static void addCalendarEvent(XWPFTable table, Point gridPos, CalendarEvent event) {
        XWPFTableRow row = table.getRow(gridPos.x);
        XWPFTableCell cell = row.getCell(gridPos.y);
        int nbParagraphs = cell.getParagraphs().size();
        XWPFParagraph eventParagraph;

        if (nbParagraphs < 2) {
            cell.addParagraph();
            eventParagraph = cell.addParagraph();
        } else {
            eventParagraph = cell.getParagraphs().get(2);
        }

        XWPFRun titleRun = eventParagraph.createRun();
        titleRun.setText(Character.toString(event.getCodePoint()));
        titleRun.setFontSize(16);
    }

    private static void addUpcomingEvents(XWPFDocument document, LocalDate date, List<CalendarEvent> events, Locale display) {
        int max = 5;
        List<CalendarEvent> nextEvents = events.stream().filter(e -> ! e.getDate().isBefore(date)).toList();
        nextEvents = nextEvents.size() > max ? nextEvents.subList(0, max) : nextEvents;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("eeee d MMMM", display);
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
        run.setFontSize(18);
        run.setText("A venir : ");
        run.addCarriageReturn();

        for (CalendarEvent event : nextEvents) {
            long daysBetween = ChronoUnit.DAYS.between(date, event.getDate());
            String days = Long.toString(ChronoUnit.DAYS.between(date, event.getDate()));
            String symb = Character.toString(event.getCodePoint());
            String name = StringUtil.capitalize(event.getDisplayName());
            String formatted = event.getDate().format(formatter);
            String inNbDays = (daysBetween == 0) ? "aujourd’hui" : "dans " + days + " jours";
            String text = MessageFormat.format("{0} {1}, le {2}, {3}.", symb, name, formatted, inNbDays);
            run.setFontSize(15);
            run.setText(text);
            run.addCarriageReturn();
        }
    }

    //inner classes
    private static class CalendarEvent implements Comparable<CalendarEvent> {
        private LocalDate date;
        private String displayName;
        private int codePoint;

        public CalendarEvent(LocalDate date, String displayName, int codePoint) {
            this.date = date;
            this.displayName = displayName;
            this.codePoint = codePoint;
        }

        public int getCodePoint() {
            return codePoint;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public int compareTo(CalendarEvent other) {
            return date.compareTo(other.getDate());
        }
    }
}
