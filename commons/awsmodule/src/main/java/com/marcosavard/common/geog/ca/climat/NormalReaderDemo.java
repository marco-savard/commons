package com.marcosavard.common.geog.ca.climat;

import com.marcosavard.common.debug.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class NormalReaderDemo {


    public static void main(String[] args) throws IOException {

        NormalReader reader = new NormalReader();
        reader.loadAll();

       // findMinMaxDaily(reader);
        findHotSpots(reader);








            /*
            Console.println(patt, parcL.getDailyMinimum(month), parcL.getDailyMaximum(month), "Parc L.", month);
            Console.println(patt, laPoca.getDailyMinimum(month), laPoca.getDailyMaximum(month), "LaPoca.", month);
            Console.println(patt, capT.getDailyMinimum(month), capT.getDailyMaximum(month), "Cap Tor.", month);
            Console.println(patt, lauzon.getDailyMinimum(month), lauzon.getDailyMaximum(month), "Lauzon", month);

            Console.println(patt, steFoy.getDailyMinimum(month), steFoy.getDailyMaximum(month), "SteFoy", month);
            Console.println(patt, scott.getDailyMinimum(month), steFoy.getDailyMaximum(month), "Scott", month);
            Console.println(patt, thetfo.getDailyMinimum(month), thetfo.getDailyMaximum(month), "Thetfo.", month);
            Console.println(patt, stGeor.getDailyMinimum(month), stGeor.getDailyMaximum(month), "StGeor", month);
            Console.println(patt, victo.getDailyMinimum(month), victo.getDailyMaximum(month), "Victo", month);
            Console.println();

            Console.println(patt, jovite.getDailyMinimum(month), jovite.getDailyMaximum(month), "St Jovite", month);
            Console.println(patt, lachute.getDailyMinimum(month), lachute.getDailyMaximum(month), "Lachute.", month);
            Console.println(patt, assomp.getDailyMinimum(month), assomp.getDailyMaximum(month), "Assomp.", month);
            Console.println(patt, mtl.getDailyMinimum(month), mtl.getDailyMaximum(month), "Mtl", month);
            Console.println(patt, acadie.getDailyMinimum(month), acadie.getDailyMaximum(month), "Acadie", month);
            Console.println(patt, anicet.getDailyMinimum(month), anicet.getDailyMaximum(month), "St Anicet.", month);
            Console.println(patt, granby.getDailyMinimum(month), granby.getDailyMaximum(month), "Granby", month);
            Console.println(patt, frelig.getDailyMinimum(month), frelig.getDailyMaximum(month), "Frelig.", month);
*/




    }

    private static void findHotSpots(NormalReader reader) throws IOException {
        LocalDate date = LocalDate.of(2000, Month.MARCH, 16);
        List<NormalReader.NormalEvent> maxEvents = reader.findDailyMinMaxSpots(date, Integer.MAX_VALUE, true);
        List<NormalReader.NormalEvent> minEvents = reader.findDailyMinMaxSpots(date, Integer.MAX_VALUE, false);

        Console.println("Point le plus froid pour cette journee {0}", date);
        Console.println("  la nuit : {0}", minEvents.get(0));
        Console.println("  le jour : {0}", maxEvents.get(0));
        Console.println();

        Console.println("Point le plus chaud pour cette journee {0}", date);
        Console.println("  la nuit : {0}", minEvents.get(minEvents.size()-1));
        Console.println("  le jour : {0}", maxEvents.get(maxEvents.size()-1));
        Console.println();




    }

    private static void findMinMaxDaily(NormalReader reader) throws IOException {
        List<Month> months = List.of(Month.JUNE, Month.JULY, Month.AUGUST);
        String patt = "..daily min = {0}, max = {1} for {2} in {3}";

        for (Month month :months) {
            int len = month.length(true);

            for (int day = 1; day <= len; day++) {
                LocalDate date = LocalDate.of(2000, month, day);
                Console.println(date);
                Console.println(patt, reader.getDailyMinimum(NormalReader.Station.QC, date), reader.getDailyMaximum(NormalReader.Station.QC, date), NormalReader.Station.QC, month);
                //  Console.println(patt, reader.getDailyMinimum(NormalReader.Station.MTL, date), reader.getDailyMaximum(NormalReader.Station.MTL, date), NormalReader.Station.MTL, month);
                Console.println();
            }
        }
    }


}
