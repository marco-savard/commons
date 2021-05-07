package com.marcosavard.commons.geog.ca.qc.res;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Municipalite {
	  public enum Designation {
		    CANTON, CANTONS_UNIS, MUNICIPALITE, PAROISSE, VILLAGE, VILLE, UNKNOWN
		  };
		  
	  private String mcode = "?";
	  private String munnom = "?";
	  private String mdes = "?";
	  private String regadm = "?";
	  private String divrec = "?";
	  private String mtel = "?";;
	  private String mcodpos = "?";;
	  
	  public static Municipalite of(String name) {
		  Municipalite municipality = new Municipalite(name);
		    return municipality;
		  }
	  
	  public Municipalite() {}
	  
	  private Municipalite(String name) {
		    this.munnom = normalize(name);
		  }

	  private static String normalize(String original) {
		    String normalized = Normalizer.normalize(original, Normalizer.Form.NFD);
		    normalized = normalized.replaceAll("Isle", "Île");
		    normalized = normalized.replaceAll("Mnt\\.", "Mount");
		    normalized = normalized.replaceAll("Mt", "Mont");
		    normalized = normalized.replaceAll("St\\.", "Saint");
		    normalized = normalized.replaceAll("St-", "Saint-");
		    normalized = normalized.replaceAll("Ste-", "Sainte-");
		    normalized = normalized.replaceAll("_", "");
		    normalized = normalized.trim();
		    normalized = toTitleCase(normalized);
		    return normalized;
		  }
	  
		public static String toTitleCase(String word) {
		    return Stream.of(word.split(" "))
		            .map(w -> w.toUpperCase().charAt(0)+ w.toLowerCase().substring(1))
		            .reduce((s, s2) -> s + " " + s2).orElse("");
		}
	  
	@Override
	public String toString() {
		String str = munnom ;
		return str;
	}
	
	public String getName() { 
		return munnom; 
	}
	
	  public String getSearchName() {
		    String searchName = searchText(munnom);
		    return searchName;
		  }
	  
	  public Designation getDesignation() {
		    String searchName = searchText(mdes);
		    List<Designation> designations = Arrays.asList(Designation.values());
		    Designation designation =
		        designations.stream().filter(d -> searchText(d.name()).equalsIgnoreCase(searchName))
		            .findFirst().orElse(Designation.UNKNOWN);
		    return designation;
		  }
	  
	  public String getPostalCode() {
		    return mcodpos;
		  }
	  
	  public int getRegionAdmin() {
		    int i1 = regadm.indexOf('(');
		    int i2 = regadm.indexOf(')');
		    String number = (i1 == -1) ? "0" : regadm.substring(i1 + 1, i2);
		    int regionCode = Integer.parseInt(number);
		    return regionCode;
		  }
	  
	  public String getDivisionRegionale() {
		    int idx = divrec.lastIndexOf("(");
		    String regionCode = (idx == -1) ? "0" : divrec.substring(idx + 1, idx + 3);
		    return regionCode;
		  }
	  
	  public String getNoTelephone() {
		    return mtel;
		  }
	  
	  private static String searchText(String original) {
		    String searchText = Normalizer.normalize(original, Normalizer.Form.NFD);
		    searchText = searchText.replaceAll("[^\\p{ASCII}]", "");
		    searchText = searchText.replaceAll("Mont-", "Mt-");
		    searchText = searchText.replaceAll("Mont ", "Mt ");
		    searchText = searchText.replaceAll("Mount-", "Mt-");
		    searchText = searchText.replaceAll("Mount ", "Mt ");
		    searchText = searchText.replaceAll("Saint-", "St-");
		    searchText = searchText.replaceAll("Saint ", "St ");
		    searchText = searchText.replaceAll("Sainte-", "Ste-");
		    searchText = searchText.replaceAll("Sainte ", "Ste ");
		    searchText = searchText.replaceAll("L'", "");
		    searchText = searchText.replaceAll("-", "");
		    searchText = searchText.replaceAll("_", "");
		    searchText = searchText.replaceAll(" ", "");
		    return searchText;
		  }
	  
	  @Override
	  public boolean equals(Object other) {
	    boolean equal = false;

	    if (other instanceof Municipalite) {
	    	Municipalite that = (Municipalite) other;
	      String thisText = searchText(this.munnom);
	      String thatText = searchText(that.munnom);
	      equal = (thisText.equalsIgnoreCase(thatText));
	    }

	    return equal;
	  }
}
