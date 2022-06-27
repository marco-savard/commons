package com.marcosavard.commons.lang;

public class StringUtilDemo {

	public static void main(String[] args) {
		String longString = "United States of America";
		String quoted = StringUtil.quote(longString);
		String stringWithAccents = "Québec";

		System.out.println(StringUtil.abbreviate(longString, 20));
		System.out.println(StringUtil.center(longString, 64));
		System.out.println(StringUtil.center(quoted, 64));
		System.out.println(StringUtil.center(StringUtil.unquote(quoted), 64));
		System.out.println();

		System.out.println(StringUtil.startsWith(stringWithAccents, "Que"));
		System.out.println(StringUtil.startsWithIgnoreAccent(stringWithAccents, "Que"));
		System.out.println(StringUtil.startsWithIgnoreCase(stringWithAccents, "que"));
		System.out.println();

		System.out.println(StringUtil.endsWith(stringWithAccents, "ebec"));
		System.out.println(StringUtil.endsWithIgnoreAccent(stringWithAccents, "ebec"));
		System.out.println(StringUtil.endsWithIgnoreCase(stringWithAccents, "EBEC"));
		System.out.println();
	}

}
