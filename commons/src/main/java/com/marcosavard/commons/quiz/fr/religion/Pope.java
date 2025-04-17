package com.marcosavard.commons.quiz.fr.religion;

import java.time.LocalDate;

public record Pope(String name, LocalDate start, LocalDate end, String originalName, String birthPlace) {
}
