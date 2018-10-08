package com.marcosavard.commons.geog.ca;

import java.io.Serializable;

/**
 * A class to store Canadian postal code, with formatting and validating methods. 
 * 
 * @author Marco
 *
 */
@SuppressWarnings("serial")
public class PostalCode implements Serializable {
	public static final String VALID_LETTERS = "ABCEGHJKLMNPRSTVXY";
	private String _postalCode;
	
	public enum Region {
		NEWFOUNDLAND,
		NOVA_SCOTIA,
		PEI,
		NEW_BRUNSWICK,
		GASPESIE,
		COTE_NORD,
		SAGUENAY,
		CAPITALE_NATIONALE,
		CHAUDIERE_APPALACHES,
		MAURICIE,
		CENTRE_DU_QUEBEC,
		ESTRIE,
		MONTEREGIE,
		MONTREAL,
		LANAUDIERE,
		LAURENTIDE,
		OUTAOUAIS,
		ABITIBI,
		OUEST_DU_QUEBEC,
		NORD_DU_QUEBEC,
		ONTARIO_EASTERN,
		TORONTO,
		ONTARIO_CENTRAL,
		ONTARIO_WESTERN,
		ONTARIO_NORTHERN,
		MANITOBA,
		SASKATCHEWAN,
		ALBERTA_SOUTHERN,
		ALBERTA_CENTRAL,
		ALBERTA_NORTHERN,
		BRITISH_COLUMBIA_MAIN_LAND,
		BRITISH_COLUMBIA_VANCOUVER_ISLAND,
		NUNAVUT,
		NWT,
		YUKON,
	};
	
	//required by GWT
	@SuppressWarnings("unused")
	private PostalCode() {}

	public PostalCode(String postalCode) {
		postalCode = (postalCode == null) ? null : postalCode.replaceAll(" ", "").toUpperCase();
		_postalCode = postalCode;
	}
	
	@Override 
	public String toString() {
		return _postalCode;
	}
	
	public String toDisplayString() {
		int len = (_postalCode == null) ? 0 : _postalCode.length(); 
		String display = _postalCode; 
		
		if (len > 3) {
			display = _postalCode.substring(0, 3) + " " + _postalCode.substring(3); 
		}
		
		return display;
	}
	
	public int compareTo(PostalCode postalCode) {
		int comparison = _postalCode.compareTo(postalCode._postalCode);
		return comparison;
	}

	public boolean isRural() {
		char ch = _postalCode.charAt(1); 
		boolean rural = (ch == '0');
		return rural;
	}

	public boolean isValid() {
		return _postalCode.length() == 6;
	}
	
	public CanadianProvince getProvince() {
		char ch = _postalCode.charAt(0); 
		CanadianProvince prov;
		
		switch (ch) {
		case 'A':
			prov = CanadianProvince.NL;
			break;
		case 'B':
			prov = CanadianProvince.NS;
			break;
		case 'C':
			prov = CanadianProvince.PE;
			break;
		case 'E':
			prov = CanadianProvince.NB;
			break;
		case 'G':
		case 'H':
		case 'J':
			prov = CanadianProvince.QC;
			break;
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'P':
			prov = CanadianProvince.ON;
			break;
		case 'R':
			prov = CanadianProvince.MB;
			break;
		case 'S':
			prov = CanadianProvince.SK;
			break;
		case 'T':
			prov = CanadianProvince.AB; 
			break;
		case 'V':
			prov = CanadianProvince.BC; 
			break;
		case 'X':
			prov = getTerritory();
			break;
		case 'Y':
			prov = CanadianProvince.YK;
			break;
		default :
			prov = null;
		}
		return prov;
	}

	private CanadianProvince getTerritory() {
		CanadianProvince territory; 
		
		if (_postalCode.startsWith("X0A")) {
			territory = CanadianProvince.NU;
		} else {
			territory = CanadianProvince.NT;
		}
		return territory;
	}

	public Region getRegion() {
		Region region = null;
		
		char ch = _postalCode.charAt(0); 
		
		switch (ch) {
		case 'A':
			region = Region.NEWFOUNDLAND;
			break;
		case 'B':
			region = Region.NOVA_SCOTIA;
			break;
		case 'C':
			region = Region.PEI;
			break;
		case 'E':
			region = Region.NEW_BRUNSWICK;
			break;
		case 'G':
			region = getRegionEasternQuebec();
			break;
		case 'H':
			region = Region.MONTREAL;
			break;
		case 'J':
			region = getRegionWesternQuebec();
			break;
		case 'K':
			region = Region.ONTARIO_EASTERN;
			break;
		case 'L':
			region = Region.ONTARIO_CENTRAL;
			break;
		case 'M':
			region = Region.TORONTO;
			break;
		case 'N':
			region = Region.ONTARIO_WESTERN;
			break;
		case 'P':
			region = Region.ONTARIO_NORTHERN; 
			break;
		case 'R':
			region = Region.MANITOBA; 
			break;
		case 'S':
			region = Region.SASKATCHEWAN; 
			break;
		case 'T':
			region = getRegionAlberta();
			break;
		case 'V':
			region = getRegionBC();
			break;
		case 'X':
			region = getRegionNT();
			break;
		case 'Y':
			region = Region.YUKON;
			break;
		}
				
		return region;
	}
	
	private Region getRegionEasternQuebec() {
		Region region; 
		
		if (isRural()) {
			region = getRegionEasternQuebecRural();
		} else {
			region = getRegionEasternQuebecUrban();
		}
		
		return region;
	}
	
	private Region getRegionEasternQuebecRural() {
		Region region; 
		
		if (_postalCode.startsWith("G0C")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0C")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0E")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0G")) {
			region = Region.COTE_NORD; 
		} else if (_postalCode.startsWith("G0H")) {
			region = Region.COTE_NORD;
		} else if (_postalCode.startsWith("G0J")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0K")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0L")) {
			region = Region.GASPESIE; 
		} else if (_postalCode.startsWith("G0M")) {
			region = Region.CHAUDIERE_APPALACHES; 
		} else if (_postalCode.startsWith("G0N")) {
			region = Region.CHAUDIERE_APPALACHES; 
		} else if (_postalCode.startsWith("G0P")) {
			region = Region.CENTRE_DU_QUEBEC; 
		} else if (_postalCode.startsWith("G0R")) {
			region = Region.CHAUDIERE_APPALACHES; 
		} else if (_postalCode.startsWith("G0S")) {
			region = Region.CHAUDIERE_APPALACHES; 
		} else if (_postalCode.startsWith("G0T")) {
			region = Region.SAGUENAY; 
		} else if (_postalCode.startsWith("G0V")) {
			region = Region.SAGUENAY; 
		} else if (_postalCode.startsWith("G0W")) {
			region = Region.SAGUENAY; 
		} else if (_postalCode.startsWith("G0X")) {
			region = Region.MAURICIE; 
		} else if (_postalCode.startsWith("G0Y")) {
			region = Region.CHAUDIERE_APPALACHES; 
		} else if (_postalCode.startsWith("G0Z")) {
			region = Region.CENTRE_DU_QUEBEC; 
		} else {
			region = Region.CAPITALE_NATIONALE; 
		}
		
		return region;
	}
	
	private Region getRegionEasternQuebecUrban() {
		Region region; 
		
		if (_postalCode.startsWith("G8P")) {
			region = Region.NORD_DU_QUEBEC; 
		} else if (_postalCode.startsWith("G7B")) {
			region = Region.SAGUENAY; 
		} else if ((compareTo(new PostalCode("G7G1A1")) >= 0) && (compareTo(new PostalCode("G8P1A1")) < 0)) {
			region = Region.SAGUENAY; 
		} else if (compareTo(new PostalCode("G8T1A1")) >= 0) {
			region = Region.MAURICIE; 
		} else if ((compareTo(new PostalCode("G4R1A1")) >= 0) && (compareTo(new PostalCode("G5T1A1")) < 0)) {
			region = getRegionGaspeNothShore();
		} else {
			region = Region.CAPITALE_NATIONALE; 
		}
		
		return region;
	}
	
	private Region getRegionGaspeNothShore() {
		Region region; 
		
		if (_postalCode.startsWith("G5B")) {
			region = Region.COTE_NORD;
		} else if (_postalCode.startsWith("G5C")) {
			region = Region.COTE_NORD;
		} else if (compareTo(new PostalCode("G4T1A1")) >= 0) {
			region = Region.COTE_NORD;
		} else {
			region = Region.GASPESIE; 
		}
		
		return region;
	}

	private Region getRegionWesternQuebec() {
		Region region; 
		
		if (isRural()) {
			region = getRegionWesternQuebecRural();
		} else {
			region = getRegionWesternQuebecUrban();
		}
		
		return region;
	}

	private Region getRegionWesternQuebecRural() {
		Region region; 
		
		if (_postalCode.startsWith("J0A") || _postalCode.startsWith("J0C")) {
			region = Region.CENTRE_DU_QUEBEC; 
		} else if (_postalCode.startsWith("J0G") || _postalCode.startsWith("J0H")) {
			region = Region.CENTRE_DU_QUEBEC; 
		} else if (_postalCode.startsWith("J0B") || _postalCode.startsWith("J0E")) {
			region = Region.ESTRIE; 
		} else if (_postalCode.startsWith("J0J") || _postalCode.startsWith("J0L")) {
			region = Region.MONTEREGIE; 
		} else if (_postalCode.startsWith("J0K")) {
			region = Region.LANAUDIERE; 
		} else if (_postalCode.startsWith("J0M")) {
			region = Region.NORD_DU_QUEBEC; 
		} else if (_postalCode.startsWith("J0N")) {
			region = Region.LAURENTIDE; 
		} else if (_postalCode.startsWith("J0P")) {
			region = Region.MONTEREGIE; 
		} else if (_postalCode.startsWith("J0R")) {
			region = Region.LAURENTIDE;
		} else if (_postalCode.startsWith("J0S")) {
			region = Region.MONTEREGIE; 
		} else if (_postalCode.startsWith("J0T")) {
			region = Region.LAURENTIDE;
		} else {
			region = Region.OUEST_DU_QUEBEC;
		}
		
		return region;
	}

	private Region getRegionWesternQuebecUrban() {
		Region region; 
		
		if (_postalCode.startsWith("J3T")) {
			region = Region.CENTRE_DU_QUEBEC; //Nicolet
		} else if (_postalCode.startsWith("J5R")) {
			region = Region.MONTEREGIE; //La Prairie
		} else if (_postalCode.startsWith("J5V")) {
			region = Region.MAURICIE; //Louiseville
		} else if (_postalCode.startsWith("J9L")) {
			region = Region.LAURENTIDE; //Mont-Laurier
		} else if (compareTo(new PostalCode("J1Z1A1")) < 0) {
			region = Region.ESTRIE;
		} else if (compareTo(new PostalCode("J2G1A1")) < 0) {
			region = Region.CENTRE_DU_QUEBEC;
		} else if (compareTo(new PostalCode("J5J1A1")) < 0) {
			region = Region.MONTEREGIE;
		} else if (compareTo(new PostalCode("J5T1A1")) < 0) {
			region =  Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J6J1A1")) < 0) {
			region =  Region.LANAUDIERE;
		} else if (compareTo(new PostalCode("J6V1A1")) < 0) {
			region =  Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J6V1A1")) < 0) {
			region =  Region.MONTEREGIE;
		} else if (compareTo(new PostalCode("J6Z1A1")) < 0) {
			region =  Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J7K1A1")) < 0) {
			region =  Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J7N1A1")) < 0) {
			region = Region.LANAUDIERE;
		} else if (compareTo(new PostalCode("J7T1A1")) < 0) {
			region = Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J7Y1A1")) < 0) {
			region = Region.MONTEREGIE;
		} else if (compareTo(new PostalCode("J8L1A1")) < 0) {
			region = Region.LAURENTIDE;
		} else if (compareTo(new PostalCode("J9L1A1")) < 0) {
			region =  Region.OUTAOUAIS; 
		} else {
			region = Region.ABITIBI; 
		}
		
		return region;
	}

	private Region getRegionAlberta() {
		Region region; 
		
		int cmp = compareTo(new PostalCode("T4C1A1"));
		if (cmp < 0) {
			region = Region.ALBERTA_SOUTHERN; 
		} else {
			boolean north = _postalCode.startsWith("T8S"); 
			north |= _postalCode.startsWith("T8V"); 
			north |= _postalCode.startsWith("T8W"); 
			north |= _postalCode.startsWith("T8X"); 
			north |= _postalCode.startsWith("T9H"); 
			north |= _postalCode.startsWith("T9J"); 
			north |= _postalCode.startsWith("T9K"); 
			north |= _postalCode.startsWith("T9S"); 
			
			region = north ? Region.ALBERTA_NORTHERN : Region.ALBERTA_CENTRAL;
		}
		
		return region;
	}

	private Region getRegionBC() {
		Region region; 
		
		int cmp = compareTo(new PostalCode("V8K1A1"));
		if (cmp < 0) {
			region = Region.BRITISH_COLUMBIA_MAIN_LAND; 
		} else {
			region = Region.BRITISH_COLUMBIA_VANCOUVER_ISLAND; 
		}
		
		return region;
	}
	
	private Region getRegionNT() {
		Region territory; 
		
		if (_postalCode.startsWith("X0A")) {
			territory = Region.NUNAVUT;
		} else {
			territory = Region.NWT; 
		}
		return territory;
	}





	

	
	

}
