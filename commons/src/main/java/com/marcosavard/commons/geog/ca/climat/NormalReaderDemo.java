package com.marcosavard.commons.geog.ca.climat;

import com.marcosavard.commons.debug.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class NormalReaderDemo {


    public static void main(String[] args) throws IOException {

        NormalReader reader = new NormalReader();
        reader.loadAll();





        LocalDate date = LocalDate.of(2025, 2, 1);
        List<NormalReader.NormalEvent> maxs = reader.findExtremeMaximums(date, 5);

        List<NormalReader.NormalEvent> mins = reader.findExtremeMinimums(date, 5);



        List<Month> months = List.of(Month.JUNE, Month.JULY, Month.AUGUST);
        String patt = "Daily min = {0}, max = {1} for {2} in {3}";

        for (Month month :months) {
            double max = reader.getExtremeMaximum(NormalReader.Station.QC, month);
            LocalDate maxDate = reader.getExtremeMaximumDate(NormalReader.Station.QC, month);

            Console.println(patt, reader.getDailyMinimum(NormalReader.Station.QC, month), reader.getDailyMaximum(NormalReader.Station.QC, month), NormalReader.Station.QC, month);
            Console.println(patt, reader.getDailyMinimum(NormalReader.Station.MTL, month), reader.getDailyMaximum(NormalReader.Station.MTL, month), NormalReader.Station.MTL, month);




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
            Console.println();
        }


    }



}
