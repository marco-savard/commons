package com.marcosavard.commons.geog.ca.qc;

/**
 * A class that defines official Quebec's administrative regions.
 * 
 * @author Marco
 */
public enum RegionAdministrative {
	BAS_ST_LAURENT("Bas-Saint-Laurent"),
	SAGUENAY_LAC_ST_JEAN("Saguenay--Lac-Saint-Jean"),
	CAPITALE_NATIONALE("Capitale-Nationale"),
	MAURICIE("Mauricie"),
	ESTRIE("Estrie"),
	MONTREAL("Montréal"),
	OUTAOUAIS("Outaouais"),
	ABITIBI("Abitibi-Témiscamingue"),
	COTE_NORD("Cote-Nord"),
	NORD_DU_QUEBEC("Nord-du-Québec"),
	GASPESIE("Gaspésie--Iles-de-la-Madeleine"),
	CHAUDIERE_APPALACHES("Chaudière-Appalaches"),
	LAVAL("Laval"),
	LANAUDIERE("Lanaudière"),
	LAURENTIDES("Laurentides"),
	MONTEREGIE("Montérégie"),
	CENTRE_DU_QUEBEC("Centre-du-Québec"); 

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
