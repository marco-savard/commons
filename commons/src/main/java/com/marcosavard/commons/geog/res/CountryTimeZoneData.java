package com.marcosavard.commons.geog.res;

import java.nio.charset.StandardCharsets;

import com.marcosavard.commons.geog.res.CountryTimeZoneData.TimeZoneEntry;
import com.marcosavard.commons.io.csv.CsvRows;

public class CountryTimeZoneData extends CsvRows<TimeZoneEntry> {
	
	CountryTimeZoneData() {
		super("CountryTimeZoneData.csv", StandardCharsets.ISO_8859_1);
	    withNbHeaders(0);
	    withDelimiter(',');
	    withCommentCharacter('#');
	}
	
    public static class TimeZoneEntry extends CsvRows.CvsRow {
		public String numero;
		public String country;
		public String timezone;
	}
}
