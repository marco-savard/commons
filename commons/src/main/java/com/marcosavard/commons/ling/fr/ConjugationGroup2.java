package com.marcosavard.commons.ling.fr;

public class ConjugationGroup2 extends Conjugation {
	private static final String[] ENDINGS = new String[] { //
		"is", "is", "it", "issons", "issez", "issent"
	};
	
	public static Conjugation findConjugationFor(String infinitive) {
		Conjugation conjugator = new ConjugationGroup2();
		return conjugator; 
	}
	
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

	@Override
	protected String getEnding(int pers) { 
		return ENDINGS[pers];
	}


}
