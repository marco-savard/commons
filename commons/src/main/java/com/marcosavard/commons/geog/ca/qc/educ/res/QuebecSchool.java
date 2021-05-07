package com.marcosavard.commons.geog.ca.qc.educ.res;

public class QuebecSchool {
	private String codeDOrganisme; 
	private String nomDOrganismeOfficiel;
	private String typeDOrganisme; 
	private String codeOrganismeResponsable;
	private String codePostalDeLAdresseGeographique;
	private Integer regionAdministrative;
	private Double latitude; 
	private Double longitude; 
	
	@Override
	public String toString() {
		String str = codeDOrganisme + ", " + nomDOrganismeOfficiel;
		return str;
	}
	
	public String getCodeDOrganisme() { 
		return codeDOrganisme; 
	}
	
	public String getNomDOrganismeOfficiel() { 
		return nomDOrganismeOfficiel; 
	}
	
	public String getTypeDOrganisme() { 
		return typeDOrganisme; 
	}
	
	public String getCodeOrganismeResponsable() { 
		return codeOrganismeResponsable; 
	}
	
	public String getCodePostalDeLAdresseGeographique() { 
		return codePostalDeLAdresseGeographique; 
	}
	
	public int getRegionAdministrative() { 
		return regionAdministrative; 
	}
	
	public double getLatitude() { 
		return latitude; 
	}
	
	public double getLongitude() { 
		return longitude; 
	}
}
