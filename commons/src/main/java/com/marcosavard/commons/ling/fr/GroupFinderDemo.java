package com.marcosavard.commons.ling.fr;

import java.text.MessageFormat;

public class GroupFinderDemo {

	public static void main(String[] args) {
		findGroup("finir"); 
	}

	private static void findGroup(String infinitive) {
		int group = GroupFinder.findGroup(infinitive); 
		String msg = MessageFormat.format("{0} : {1} groupe", infinitive, group); 
		System.out.println(msg); 
	}

}
