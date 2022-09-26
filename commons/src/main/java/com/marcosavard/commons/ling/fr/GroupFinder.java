package com.marcosavard.commons.ling.fr;

public class GroupFinder {

	public static int findGroup(String infinitive) {
		int group = -1; 
		
		if (infinitive.equals("aller")) { 
			group = 3; 
		} else if (infinitive.endsWith("er")) { 
			group = 1; 
		} else if (infinitive.endsWith("oir")) { 
			group = 3;
		} else if (infinitive.endsWith("ir")) { 
			group = findGroupForIr(infinitive); 
		} else if (infinitive.endsWith("re")) { 
			group = 3;
		} else { 
			group = -1;
		}
		
		return group;
	}

	//ref : https://leconjugueur.lefigaro.fr/frlesgroupes.php
	private static int findGroupForIr(String infinitive) {
		boolean group3 = false;
		
		group3 = group3 || infinitive.endsWith("bouillir"); 
		group3 = group3 || infinitive.endsWith("courir"); 
		group3 = group3 || infinitive.endsWith("cueillir"); 
		group3 = group3 || infinitive.endsWith("dormir"); 
		group3 = group3 || infinitive.endsWith("faillir");
		group3 = group3 || infinitive.endsWith("f�rir");
		group3 = group3 || infinitive.endsWith("fuir");
		group3 = group3 || infinitive.endsWith("g�sir"); 
		group3 = group3 || infinitive.endsWith("issir"); 
		group3 = group3 || infinitive.endsWith("mentir"); 
		group3 = group3 || infinitive.endsWith("mourir"); 
		group3 = group3 || infinitive.endsWith("offrir"); 
		group3 = group3 || infinitive.endsWith("ou�r"); 
		group3 = group3 || infinitive.endsWith("ouvrir"); 
		group3 = group3 || infinitive.endsWith("partir"); 
		group3 = group3 || infinitive.endsWith("qu�rir"); 
		group3 = group3 || infinitive.endsWith("saillir"); 
		group3 = group3 || infinitive.endsWith("sentir"); 
		group3 = group3 || infinitive.endsWith("servir");
		group3 = group3 || infinitive.endsWith("sortir"); 
		group3 = group3 || infinitive.endsWith("tenir"); 
		group3 = group3 || infinitive.endsWith("venir"); 
		group3 = group3 || infinitive.endsWith("v�tir"); 
		
		int group = group3 ? 3 : 2; 
		return group;
	}

}
