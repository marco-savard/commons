package com.marcosavard.commons.quiz.fr.religion;

import com.marcosavard.commons.debug.Console;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ReligionDemo {

    public static void main(String[] args) throws IOException {
        LocalDate today = LocalDate.now();
        Locale display = Locale.FRENCH;

        printSaintsDuJour(today);
        printPopes(today, display);
    }

    private static void printSaintsDuJour(LocalDate date) throws IOException {
        SaintDuJourReader reader1 = new SaintDuJourReader();
        SaintsLongReader reader2 = new SaintsLongReader();
        List<SaintDuJour> allSaintsDuJour = reader1.readAll();
        allSaintsDuJour.addAll(reader2.readAll());

        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        List<SaintDuJour> saintsDuJour = allSaintsDuJour.stream()
                .filter(s -> s.month().equals(month) && s.dayOfMonth() == day)
                .toList();

        SaintDuJour saintDuJour = saintsDuJour.get(0);
        Console.println("Saint du jour : {0}", saintDuJour.name());
        Console.println("  {0}", saintDuJour.description());
        Console.println();
        Console.println("Autres saints:");

        for (int i=1; i<saintsDuJour.size(); i++) {
            Console.println("- {0}", saintsDuJour.get(i).name());
        }
        Console.println();
    }


    private static void printPopes(LocalDate date, Locale display) throws IOException {
        PopeReader popeReader = new PopeReader();
        List<Pope> popes = popeReader.readAll();

        //obtient les evenements les plus pres du jour
        int day = date.getDayOfYear();
        List<Pope> comingEvents = popes.stream()
                .sorted(Comparator.comparing(p -> Math.floorMod(p.start().getDayOfYear() - day, 365)))
                .toList();

        comingEvents = comingEvents.subList(0, Math.min(15, comingEvents.size()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", display);
        String title = MessageFormat.format("Pontificat débutant un {0} ou peu après", date.format(formatter));
        Console.println(title);

        for (Pope pope : comingEvents) {
            printPope(pope, display);
        }
    }

    private static void printPope(Pope pope, Locale display) {
        StringBuilder builder = new StringBuilder();
        builder.append("Le pape " + pope.name());

        if (pope.originalName() != null) {
            builder.append(", précédemment connu sous le nom de " + pope.originalName());
        }

        if (pope.birthPlace() != null) {
            builder.append(", né à " + pope.birthPlace());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyy", display);
        builder.append(", débuta son pontificat le " + pope.start().format(formatter) + ". ");

        if (pope.end() != null) {
            long days = Duration.between(pope.start().atStartOfDay(),  pope.end().atStartOfDay()).toDays();
            int years = (int)(days / 365.25);

            if (years > 0) {
                builder.append("Son pontificat durera " + years + " ans.");
            } else {
                builder.append("Son pontificat durera " + days + " jours.");
            }
        }

        Console.println(builder.toString());
    }
}
