package com.marcosavard.common.util.soundex;

import java.util.ArrayList;
import java.util.List;

public class Soundex {

	public static List<String> of(List<String> list) {
		List<String> soundexList = new ArrayList<>();

		for (String item : list) {
			soundexList.add(Soundex.of(item));
		}

		return soundexList;
	}

	public static String of(String text) {
		return (text == null) ? "" : ofNotNull(text);
	}

	private static String ofNotNull(String text) {
		List<String> soundexList = new ArrayList<>();

		String[] words = text.split(" ");
		for (String word : words) {
			if (!word.isBlank()) {
				soundexList.add(ofWord(word));
			}
		}

		String joined = String.join(" ", soundexList);
		return joined;
	}

	// https://www.rosettacode.org/wiki/Soundex#Java
	private static String ofWord(String word) {
		String code, previous, soundex;
		code = word.toUpperCase().charAt(0) + "";
		previous = getCode(word.toUpperCase().charAt(0));

		for (int i = 1; i < word.length(); i++) {
			String current = getCode(word.toUpperCase().charAt(i));

			// Rule : Remove duplicates
			if (current.length() > 0 && !current.equals(previous)) {
				code = code + current;
			}
			previous = current;
		}

		// Rule : Pad with 0's or truncate
		soundex = (code + "0000").substring(0, 4);
		return soundex;
	}

	private static String getCode(char c) {
		switch (c) {
		case 'B':
		case 'F':
		case 'P':
		case 'V':
			return "1";
		case 'C':
		case 'G':
		case 'J':
		case 'K':
		case 'Q':
		case 'S':
		case 'X':
		case 'Z':
			return "2";
		case 'D':
		case 'T':
			return "3";
		case 'L':
			return "4";
		case 'M':
		case 'N':
			return "5";
		case 'R':
			return "6";
		default:
			return "";
		}
	}

}
