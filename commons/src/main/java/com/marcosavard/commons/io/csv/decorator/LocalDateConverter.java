package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LocalDateConverter extends CsvFormatter.Decorator<Long>  {
    public LocalDateConverter(String... columns) {
        super(columns);
    }

    @Override
    public Object decorateValue(Long value) {
        Instant instant = Instant.ofEpochMilli(value);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
        return dateTime.toLocalDate();
    }
}
