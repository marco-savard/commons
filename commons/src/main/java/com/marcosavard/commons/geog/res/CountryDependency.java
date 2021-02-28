package com.marcosavard.commons.geog.res;

import java.util.HashMap;
import java.util.Map;

public class CountryDependency {
	private static Map<String, String> dependencies = null; 

	public static String of(String country) {
		Map<String, String> dependencies = getDependencies(); 
		return dependencies.get(country); 
	}

	private static Map<String, String> getDependencies() {
		if (dependencies == null) { 
			dependencies = new HashMap<>();
			//Antigua & Barbuda : indep. 
			dependencies.put("AG",  "GB"); 
			dependencies.put("AI",  "GB"); 
			dependencies.put("AS",  "US"); 
			dependencies.put("AW",  "NL"); 
			dependencies.put("AX",  "FI"); 
			dependencies.put("BL",  "FR"); 
			dependencies.put("BM",  "GB"); 
			dependencies.put("BQ",  "NL"); 
			dependencies.put("BV",  "NO"); 
			dependencies.put("CC",  "AU"); 
			dependencies.put("CK",  "NZ"); 
			dependencies.put("CW",  "NL"); 
			dependencies.put("CX",  "AU"); 
			dependencies.put("FK",  "GB"); 
			dependencies.put("FO",  "DK"); 
			dependencies.put("GF",  "FR"); 
			dependencies.put("GG",  "GB"); 
			dependencies.put("GI",  "GB"); 
			dependencies.put("GL",  "DK"); 
			dependencies.put("GP",  "FR"); 
			dependencies.put("GS",  "GB"); 
			dependencies.put("GU",  "US"); 
			dependencies.put("HK",  "CN"); 
			dependencies.put("HM",  "AU"); 
			dependencies.put("IM",  "GB"); 
			dependencies.put("IO",  "GB"); 
			dependencies.put("JE",  "GB"); 
			dependencies.put("KY",  "GB"); 
			dependencies.put("MF",  "FR"); 
			dependencies.put("MO",  "CN"); 
			dependencies.put("MP",  "US"); 
			dependencies.put("MQ",  "FR"); 
			dependencies.put("MS",  "FR"); 
			dependencies.put("NC",  "FR"); 
			dependencies.put("NF",  "AU"); 
			dependencies.put("PF",  "FR"); 
			dependencies.put("PM",  "FR"); 
			dependencies.put("PN",  "GB");
			dependencies.put("PR",  "US");
			dependencies.put("RE",  "FR"); 
			dependencies.put("SH",  "GB");
			dependencies.put("SJ",  "NO");
			dependencies.put("SX",  "NL");
			dependencies.put("TC",  "GB");
			dependencies.put("TF",  "FR");
			dependencies.put("TK",  "NZ");
			dependencies.put("UM",  "US");
			dependencies.put("VG",  "GB");
			dependencies.put("VI",  "US");
			dependencies.put("WF",  "FR");
			dependencies.put("YT",  "FR");
			
			
			
			
		}
		
		return dependencies;
	}

}
