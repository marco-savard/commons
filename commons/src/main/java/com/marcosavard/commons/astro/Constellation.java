package com.marcosavard.commons.astro;

import java.util.stream.Stream;

public enum Constellation {
	ANDROMEDA("And", "Andromeda", "Andromedae", Category.NORTHERN),
	ANTLIA("Ant", "Antlia", "Antliae", Category.SOUTHERN),
	APUS("Aps", "Apus", "Apodis", Category.SOUTHERN),
	AQUARIUS("Aqr", "Aquarius", "Aquarii", Category.ZODIAC),
	AQUILA("Aql", "Aquila", "Aquilae", Category.NORTHERN), 
	ARA("Ara", "Ara", "Arae", Category.SOUTHERN),
	ARIES("Ari", "Aries", "Arietis", Category.ZODIAC), 
	AURIGA("Aur", "Auriga", "Aurigae", Category.NORTHERN),
	BOOTES("Boo", "Bootes", "Bootis", Category.NORTHERN), 
	CAELUM("Cae", "Caelum", "Caeli", Category.SOUTHERN),
	CAMELOPARDALIS("Cam", "Camelopardalis", "Camelopardalis", Category.SOUTHERN),
	CANCER("Cnc", "Cancer", "Cancri", Category.ZODIAC),
	CANES_VENATICI("Cvn", "Canes Venatici", "Canum Venaticorum", Category.NORTHERN),
	CANIS_MAJOR("CMa", "Canis Major", "Canis Majoris", Category.SOUTHERN),
	CANIS_MINOR("CMi", "Canis Minor", "Canis Minoris", Category.NORTHERN),
	CAPRICORNUS("Cap", "Capricornus", "Capricorni", Category.ZODIAC),
	CARINA("Car", "Carina", "Carinae", Category.SOUTHERN),
	CASSIOPEIA("Cas", "Cassiopeia", "Cassiopeiae", Category.NORTHERN),
	CENTAURUS("Cen", "Centaurus", "Centauri", Category.SOUTHERN),
	CEPHEUS("Cep", "Cephus", "Cephei", Category.NORTHERN), 
	CETUS("Cet", "Cetus", "Ceti", Category.SOUTHERN), 
	CHAMAELEON("Cha", "Chamaeleon", "Chamaeleontis", Category.SOUTHERN), 
	CIRCINUS("Cir", "Circinus", "Circini", Category.SOUTHERN), 
	COLUMBA("Col", "Columba", "Columbae", Category.SOUTHERN), 
	COMA_BERENICES("Com", "Coma Berenices", "Comae Berenices", Category.NORTHERN), 
	CORONA_AUSTRALIS("CrA", "Corona Australis", "Coronae Australis", Category.SOUTHERN), 
	CORONA_BOREALIS("CrB", "Corona Borealis", "Coronae Borealis", Category.NORTHERN), 
	CORVUS("Crv", "Corvus", "Corvi", Category.SOUTHERN),
	CRATER("Crt", "Crater", "Crateris", Category.SOUTHERN), 
	CRUX("Cru", "Crux", "Crucis", Category.SOUTHERN), 
	CYGNUS("Cyg", "Cygnus", "Cygni", Category.NORTHERN), 
	DELPHINUS("Del", "Delphinus", "Delphini", Category.NORTHERN),
	DORADO("Dor", "Dorado", "Doradus", Category.SOUTHERN), 
	DRACO("Dra", "Draco", "Draconis", Category.NORTHERN), 
	EQUULEUS("Equ", "Equuleus", "Equulei", Category.NORTHERN),
	ERIDANUS("Eri", "Eridanus", "Edinani", Category.SOUTHERN),
	FORNAX("For", "Fornax", "Fornacis", Category.SOUTHERN), 
	GEMINI("Gem", "Gemini", "Geminorum", Category.ZODIAC),
	GRUS("Gru", "Grus", "Gruis", Category.SOUTHERN),
	HERCULES("Her", "Hercules", "Herculis", Category.NORTHERN), 
	HOROLOGIUM("Hor", "Horologium", "Horologii", Category.SOUTHERN), 
	HYDRA("Hya", "Hydra", "Hydrae", Category.SOUTHERN), 
	HYDRUS("Hyi", "Hydrus", "Hydri", Category.SOUTHERN), 
	INDUS("Ind", "Indus", "Indi", Category.SOUTHERN),
	LACERTA("Lac", "Lacerta", "Lacertae", Category.NORTHERN),
	LEO("Leo", "Leo", "Leonis", Category.ZODIAC),
	LEO_MINOR("Leo Minor", "LMin", "Leonis Minoris", Category.NORTHERN),
	LEPUS("Lep", "Lepus", "Leporis", Category.SOUTHERN), 
	LIBRA("Lib", "Libra", "Librae", Category.ZODIAC), 
	LUPUS("Lup", "Lupus", "Lupi", Category.SOUTHERN), 
	LYNX("Lyn", "Lynx", "Lyncis", Category.NORTHERN),
	LYRA("Lyr", "Lyra", "Lyrae", Category.NORTHERN),
	MENSA("Men", "Mensa", "Mensae", Category.SOUTHERN),
	MICROSCOPIUM("Mis", "Microscopium", "Microscopii", Category.SOUTHERN),
	MONOCERUS("Mon", "Monoceros", "Monocerotis", Category.SOUTHERN),
	MUSCA("Mus", "Musca", "Muscae", Category.SOUTHERN),
	NORMA("Nor", "Norma", "Normae", Category.SOUTHERN), 
	OCTANS("Oct", "Octans", "Octantis", Category.SOUTHERN),
	OPHIUCHUS("Oph", "Ophiuchus", "Ophiuchi", Category.ZODIAC),
	ORION("Ori", "Orion", "Orionis", Category.SOUTHERN), 
	PAVO("Pav", "Pavo", "Pavonis", Category.SOUTHERN),
	PEGASUS("Peg", "Pegasus", "Pegasi", Category.NORTHERN), 
	PERSEUS("Per", "Perseus", "Persei", Category.NORTHERN), 
	PHOENIX("Phe", "Phoenix", "Phoenicis", Category.SOUTHERN),
	PICTOR("Pic", "Pictor", "Pictoris", Category.SOUTHERN),
	PISCES("Psc", "Pisces", "Piscium", Category.ZODIAC),
	PISCES_AUSTRINUS("PsA", "Pisces Austrinus", "Piscis Austini", Category.SOUTHERN),
	PUPPIS("Pup", "puppis", "Puppis", Category.SOUTHERN),
	PYXIS("Pyx", "Pyxis", "Pyxidis", Category.SOUTHERN), 
	RETICULUM("Ret", "Reticulum", "Reticuli", Category.SOUTHERN), 
	SAGITTA("Sge", "Sagitta", "Sagittae", Category.NORTHERN), 
	SAGITTARIUS("Sgr", "Sagittarius", "Sagittarii", Category.ZODIAC),
	SCORPIO("Sco", "Scorpio", "Scorpii", Category.ZODIAC),
	SCULTOR("Scl", "Scultor", "Scultoris", Category.SOUTHERN), 
	SCUTUM("Sct", "Scutum", "Scuti", Category.SOUTHERN),
	SERPENS("Ser", "Serpens", "Serpentis", Category.NORTHERN),
	SEXTANS("Sex", "Sextans", "Sextantis", Category.SOUTHERN),
	TAURUS("Tau", "Taurus", "Tauri", Category.ZODIAC),
	TELESCOPIUM("Tel", "Telescopium", "Telescopii", Category.SOUTHERN),
	TRIANGULUM("Tri", "Triangulum", "Trianguli", Category.NORTHERN),
	TRIANGULUM_AUSTRALE("TrA", "Triangulum Australe", "Trianguli Australis", Category.SOUTHERN),
	TUCANA("Tuc", "Tucana", "Tucanae", Category.SOUTHERN), 
	URSUS_MAJOR("UMa", "Ursus Major", "Ursae Majoris", Category.NORTHERN),
	URSUS_MINOR("UMi", "Ursus Minor", "Ursae Minoris", Category.NORTHERN),
	VELA("Vel", "Vela", "Velorum", Category.SOUTHERN),
	VIRGO("Vir", "Virgo", "Virginis", Category.ZODIAC),
	VOLANS("Vol", "Volans", "Volantis", Category.SOUTHERN),
	VULPECULA("Vul", "Vulpecula", "Vulpeculae", Category.NORTHERN),
	;

	private String code;
	private String name;
	private String genitive;
	private Category category;

	Constellation(String code, String name, String genitive, Category category) {
		this.code = code;
		this.name = name;
		this.genitive = genitive;
		this.category = category;
	}

	public static Constellation of(String code) {
		Stream<Constellation> values = Stream.of(Constellation.values()); 		
		Constellation constellation = values.filter(c -> c.getCode().equals(code)).findFirst().orElse(null); 
		
		if (constellation == null) { 
			String message = "Constellation not defined : " + code; 
			throw new RuntimeException(message);
		}
		
		return constellation;
	}

	String getCode() {
		return code;
	}
	
	String getName() {
		return name;
	}
	
	public boolean isNorthern() {
		return category.equals(Category.NORTHERN);
	}
	
	public boolean isZodiac() {
		return category.equals(Category.ZODIAC);
	}
	
	public boolean isSouthern() {
		return category.equals(Category.SOUTHERN);
	}
	

	public String getDisplayName(String rank) {
		String displayName = GreekLetter.of(rank).getGreekName() + " " + genitive;
		return displayName;
	}
	
	//Note Northern means north of zodiac constellations. e.g. Orion spans on the
	//celestrial equator but is south of the ecliptic (south of Gemini and Taurus), 
	//therefore categorised as a southern constellation.
	public static enum Category {
		NORTHERN,
		ZODIAC,
		SOUTHERN
	}



}
