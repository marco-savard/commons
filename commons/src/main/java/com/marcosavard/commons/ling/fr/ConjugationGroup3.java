package com.marcosavard.commons.ling.fr;

import java.util.ArrayList;
import java.util.List;

//ref : https://leconjugueur.lefigaro.fr/frlesgroupes.php
public abstract class ConjugationGroup3 extends Conjugation {
	private static final String[] ENDINGS = new String[] { //
			"s", "s", "t", "ons", "ez", "ent" };
	
	@Override
	protected String getEnding(int pers) {
		return ENDINGS[pers];
	}
	
	public static Conjugation findConjugationFor(String infinitive) {
		ConjugationFinder conjugationFinder = ConjugationFinder.getInstance(); 
		Conjugation conjugation = conjugationFinder.find(infinitive);
		return conjugation;
	}
		
		
    //
	// inner classed
	//
	private static class ConjugationFinder {
		private static ConjugationFinder conjugationFinder; 
		private List<SuffixConjugation> conjugations; 
		
		public static ConjugationFinder getInstance() {
			if (conjugationFinder == null) { 
				conjugationFinder = new ConjugationFinder(); 
			}
			
			return conjugationFinder;
		}

		private ConjugationFinder() { 
			conjugations = new ArrayList<>(); 
			conjugations.add(SuffixConjugation.of("aller", new ConjugationAller())); 
			
			conjugations.add(SuffixConjugation.of("ourvoir", new ConjugationVoir())); 
			conjugations.add(SuffixConjugation.of("pouvoir", new ConjugationPouvoir())); 
			conjugations.add(SuffixConjugation.of("alloir", new ConjugationAlloir())); 
			conjugations.add(SuffixConjugation.of("cevoir", new ConjugationCevoir())); 
			conjugations.add(SuffixConjugation.of("euvoir", new ConjugationEuvoir())); 
			conjugations.add(SuffixConjugation.of("ouvoir", new ConjugationOuvoir())); 
			conjugations.add(SuffixConjugation.of("ouloir", new ConjugationOuloir())); 
			conjugations.add(SuffixConjugation.of("avoir", new ConjugationAvoir())); 
			conjugations.add(SuffixConjugation.of("aloir", new ConjugationAloir())); 
			conjugations.add(SuffixConjugation.of("evoir", new ConjugationEvoir())); 
			conjugations.add(SuffixConjugation.of("voir", new ConjugationVoir()));
			conjugations.add(SuffixConjugation.of("eoir", new ConjugationEoir()));
			conjugations.add(SuffixConjugation.of("oir", new ConjugationOir())); 
			
			conjugations.add(SuffixConjugation.of("mourir", new ConjugationMourir())); 
			conjugations.add(SuffixConjugation.of("enir", new ConjugationEnir())); 
			conjugations.add(SuffixConjugation.of("�rir", new ConjugationErir())); 
			conjugations.add(SuffixConjugation.of("vrir", new ConjugationVrir())); 
			conjugations.add(SuffixConjugation.of("lir", new ConjugationLir())); 
			conjugations.add(SuffixConjugation.of("mir", new ConjugationMir())); 
			conjugations.add(SuffixConjugation.of("tir", new ConjugationTir())); 
			conjugations.add(SuffixConjugation.of("ir", new ConjugationIr())); 
			
			conjugations.add(SuffixConjugation.of("indre", new ConjugationIndre())); 
			conjugations.add(SuffixConjugation.of("ndre", new ConjugationNdre())); 
			conjugations.add(SuffixConjugation.of("udre", new ConjugationUdre())); 
			conjugations.add(SuffixConjugation.of("ttre", new ConjugationTtre())); 
			conjugations.add(SuffixConjugation.of("uire", new ConjugationUire())); 
			conjugations.add(SuffixConjugation.of("dre", new ConjugationDre())); 
			conjugations.add(SuffixConjugation.of("ire", new ConjugationIre())); 
			conjugations.add(SuffixConjugation.of("re", new ConjugationRe())); 
		}
		
		
		public Conjugation find(String infinitive) {
			Conjugation foundConjugation = null; 
			
			for (SuffixConjugation suffixConjugation : conjugations) {
				String ending = suffixConjugation.getEnding(); 
				if (infinitive.endsWith(ending)) { 
					foundConjugation = suffixConjugation.getConjugation(); 
					break;
				}
			}
			
			return foundConjugation;
		}
	}
	
	private static class SuffixConjugation {
		private String ending;
		private Conjugation conjugation;

		public SuffixConjugation(String ending, Conjugation conjugation) {
			this.ending = ending;
			this.conjugation = conjugation;
		}

		public Conjugation getConjugation() {
			return conjugation;
		}

		public String getEnding() {
			return ending;
		}

		public static SuffixConjugation of(String suffix, Conjugation conjugation) {
			return new SuffixConjugation(suffix, conjugation);
		}
	}
	
	private static class ConjugationVoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"vois", "vois", "voit", "voyons", "voyez", "voient" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("voir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
		
	}
	
	private static class ConjugationEoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ois", "ois", "oit", "oyons", "oyez", "oient" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("eoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
		
	}
	
	private static class ConjugationEuvoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"eux", "eux", "eut", "euvons", "euvez", "euvent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("euvoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationPouvoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"eux", "eux", "eut", "ouvons", "ouvez", "euvent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ouvoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationOuloir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"eux", "eux", "eut", "oulons", "oulez", "eulent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ouloir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationOuvoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"eus", "eus", "eut", "ouvons", "ouvez", "euvent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ouvoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationAlloir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"aux", "aux", "aut", "alons", "alez", "alent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("alloir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationAloir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"aux", "aux", "aut", "alons", "alez", "alent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("aloir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationAvoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ais", "ais", "ait", "avons", "avez", "avent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("avoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationEvoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ois", "ois", "oit", "evons", "evez", "oivent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("evoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationCevoir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"�ois", "�ois", "�oit", "cevons", "cevez", "�oivent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("cevoir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationIr extends ConjugationGroup3 { 
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
		
	}
	
	private static class ConjugationOir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ois", "ois", "oit", "oyons", "oyez", "oient" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("oir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationTtre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ts", "ts", "t", "ttons", "ttez", "ttent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ttre");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationIndre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ns", "ns", "nt", "gnons", "gnez", "gnent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ndre");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationNdre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"nds", "nds", "nd", "nons", "nez", "nnent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ndre");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationUdre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"us", "us", "ut", "lvons", "lvez", "lvent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("udre");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationDre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"ds", "ds", "d", "dons", "dez", "dent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("dre");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationLir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"le", "les", "le", "lons", "lez", "lent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("lir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationVrir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"re", "res", "re", "rons", "rez", "rent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("rir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationErir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"iers", "iers", "iert", "�rons", "�rez", "i�rent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("�rir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationEnir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"iens", "iens", "ient", "enons", "enez", "iennent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("enir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationMir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"s", "s", "t", "mons", "mez", "ment" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("mir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationTir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"s", "s", "t", "tons", "tez", "tent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("tir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationUire extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"uis", "uis", "uit", "uisons", "uisez", "uisent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("uire");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	
	private static class ConjugationIre extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"is", "is", "it", "yons", "yez", "ient" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("ire");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationRe extends ConjugationGroup3 { 
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("re");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}	
	}
	
	private static class ConjugationMourir extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"meurs", "meurs", "meurt", "mourons", "mourez", "meurent" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("mourir");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	
	private static class ConjugationAller extends ConjugationGroup3 { 
		private static final String[] ENDINGS = new String[] { //
				"vais", "vas", "va", "allons", "allez", "vont" };
		
		@Override
		protected String getEnding(int pers) {
			return ENDINGS[pers];
		}
		
		@Override
		public void conjugate(String infinitive) {
			int idx = infinitive.indexOf("aller");
			String radical = infinitive.substring(0, idx); 
			System.out.println(infinitive);
			
			for (int i=0; i<6; i++) {
				String conjugated = conjugate(radical, i);
				String pronoun = getPronoun(conjugated, i); 
				String joined = String.join(" ", pronoun, conjugated);
				System.out.println("  " + joined);
			}
			
			System.out.println();
		}
	}
	

}
