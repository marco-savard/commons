package com.marcosavard.commons.geog.ca.qc;

/**
 * A class that defines official Quebec's administrative regions.
 * 
 * @author Marco
 */
public enum RegionAdministrative {
	BAS_ST_LAURENT("Bas-Saint-Laurent"),
	SAGUENAY_LAC_ST_JEAN("Saguenay�Lac-Saint-Jean"),
	CAPITALE_NATIONALE("Capitale-Nationale"),
	MAURICIE("Mauricie"),
	ESTRIE("Estrie"),
	MONTREAL("Montr�al"),
	OUTAOUAIS("Outaouais"),
	ABITIBI("Abitibi-T�miscamingue"),
	COTE_NORD("C�te-Nord"),
	NORD_DU_QUEBEC("Nord-du-Qu�bec"),
	GASPESIE("Gasp�sie��les-de-la-Madeleine"),
	CHAUDIERE_APPALACHES("Chaudi�re-Appalaches"),
	LAVAL("Laval"),
	LANAUDIERE("Lanaudi�re"),
	LAURENTIDES("Laurentides"),
	MONTEREGIE("Mont�r�gie"),
	CENTRE_DU_QUEBEC("Centre-du-Qu�bec"); 

	private String name; 
	
    RegionAdministrative(String name) {
		this.name = name; 
	}
    
	@Override
	public String toString() {
		String s = String.format("%02d", getCode()) + " " + name;
		return s;
	}

	public int getCode() {
		return this.ordinal() + 1;
	}
}
