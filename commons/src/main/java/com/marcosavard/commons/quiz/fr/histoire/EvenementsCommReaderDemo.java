package com.marcosavard.commons.quiz.fr.histoire;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class EvenementsCommReaderDemo {

    public static void main(String[] args) throws IOException {
        Locale display = Locale.FRENCH;
        LocalDate today = LocalDate.now();
        int doy = today.getDayOfYear(); //LocalDate.of(2025, 12, 20); //LocalDate.now();

        EvenementsCommReader reader = new EvenementsCommReader();
        List<HistoricalEvent> events = reader.readAll();

        //obtient les evenements les plus pres du jour
        List<HistoricalEvent> comingEvents = events.stream()
                .sorted(Comparator.comparing(e -> Math.floorMod(e.getDate().getDayOfYear() - doy, 365)))
                .toList();

        //comingEvents = comingEvents.subList(0, Math.min(15, comingEvents.size()));

        for (HistoricalEvent event : comingEvents) {
            Console.println(event);
        }

        Console.println("Success");
    }

    private static String[] VERBS = new String[] {"avait", "a", "aura"};
    private static String[] DAYS = new String[] {"avant-hier", "hier", "aujourd`hui", "demain", "apres-demain"};

    private static String toText(LocalDate today, String[] event, Locale display) {
        int year = Integer.parseInt(event[3]);
        int month = Integer.parseInt(event[2]);
        int day = Integer.parseInt(event[1]);
        int diff = day - today.getDayOfMonth();
        int idx = (int)(Math.signum(diff) + 1);
        String verb = VERBS[idx];
        String dayStr = DAYS[diff + 2];

        String age = Integer.toString(today.getYear() - Integer.parseInt(event[3]));
        String nom = toText(event[0]);
        boolean hasVerb = (nom.equals(event[0]));
        String that = hasVerb ? "que" : "qu`a eu lieu";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", display);

        LocalDate eventDate = LocalDate.of(year, month, day);
        String eventDateStr = eventDate.format(formatter);

        String pat = "Il y {0} {1} ans {2} {3}, le {4}, {5}";
        String text = MessageFormat.format(pat, verb, age, dayStr, that, eventDateStr, nom);
        return text;
    }

    private static String toText(String raw) {
        String text = StringUtil.uncapitalize(raw);

        if (text.startsWith("abandon")) {
            text = "l`" + text;
        } else if (text.startsWith("alliance")) {
            text = "l`" + text;
        } else if (text.startsWith("arrêt")) {
            text = "l`" + text;
        } else if (text.startsWith("arrivée")) {
            text = "l`" + text;
        } else if (text.startsWith("augmentation")) {
            text = "l`" + text;
        } else if (text.startsWith("baptême")) {
            text = "le " + text;
        } else if (text.startsWith("bataille")) {
            text = "la " + text;
        } else if (text.startsWith("cession")) {
            text = "la " + text;
        } else if (text.startsWith("concession")) {
            text = "la " + text;
        } else if (text.startsWith("conclusion")) {
            text = "la " + text;
        } else if (text.startsWith("construction")) {
            text = "la " + text;
        } else if (text.startsWith("couronnement")) {
            text = "le " + text;
        } else if (text.startsWith("débarquement")) {
            text = "le " + text;
        } else if (text.startsWith("début")) {
            text = "le " + text;
        } else if (text.startsWith("destruction")) {
            text = "la " + text;
        } else if (text.startsWith("échange")) {
            text = "l`" + text;
        } else if (text.startsWith("élection")) {
            text = "l`" + text;
        } else if (text.startsWith("enlèvement")) {
            text = "l`" + text;
        } else if (text.startsWith("entrée")) {
            text = "l`" + text;
        } else if (text.startsWith("érection")) {
            text = "l`" + text;
        } else if (text.startsWith("exploration")) {
            text = "l`" + text;
        } else if (text.startsWith("fin")) {
            text = "la " + text;
        } else if (text.startsWith("fondation")) {
            text = "la " + text;
        } else if (text.startsWith("grand")) {
            text = "le " + text;
        } else if (text.startsWith("instauration")) {
            text = "l`" + text;
        } else if (text.startsWith("introduction")) {
            text = "l`" + text;
        } else if (text.startsWith("légalisation")) {
            text = "la " + text;
        } else if (text.startsWith("martyr")) {
            text = "le " + text;
        } else if (text.startsWith("massacre")) {
            text = "le " + text;
        } else if (text.startsWith("mission")) {
            text = "la " + text;
        } else if (text.startsWith("mort")) {
            text = "la " + text;
        } else if (text.startsWith("naissance")) {
            text = "la " + text;
        } else if (text.startsWith("naufrage")) {
            text = "le " + text;
        } else if (text.startsWith("nomination")) {
            text = "la " + text;
        } else if (text.startsWith("obtention")) {
            text = "l`" + text;
        } else if (text.startsWith("ouverture")) {
            text = "l`" + text;
        } else if (text.startsWith("première")) {
            text = "la " + text;
        } else if (text.startsWith("présententation")) {
            text = "la " + text;
        } else if (text.startsWith("prise")) {
            text = "la " + text;
        } else if (text.startsWith("publication")) {
            text = "la " + text;
        } else if (text.startsWith("reconnaissance")) {
            text = "la " + text;
        } else if (text.startsWith("réforme")) {
            text = "la " + text;
        } else if (text.startsWith("sacre")) {
            text = "le " + text;
        } else if (text.startsWith("signature")) {
            text = "la " + text;
        } else if (text.startsWith("traité")) {
            text = "le " + text;
        } else if (text.startsWith("tremblement")) {
            text = "le " + text;
        } else if (text.startsWith("vente")) {
            text = "la " + text;
        } else if (text.startsWith("visite")) {
            text = "la " + text;
        } else {
            text = raw;
        }

        return text;
    }


}
