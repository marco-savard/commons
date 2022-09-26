package com.marcosavard.commons.ling.fr;

import java.util.Arrays;

public class ConjugationGroup1 extends Conjugation {
	private static final String[] ENDINGS = new String[] { //
			"e", "es", "e", "ons", "ez", "ent"
	};
	
	public static Conjugation findConjugationFor(String infinitive) {
		Conjugation conjugator; 
		
		if (infinitive.endsWith("oyer") || infinitive.endsWith("uyer")) { 
			conjugator = new ConjugationOyer();
		} else if (infinitive.endsWith("eler") || infinitive.endsWith("eter")) { 
			boolean exceptionEler = isExceptionEler(infinitive);
			conjugator = exceptionEler ? new ConjugationEler2() : new ConjugationEler();
		} else if (endsWithECer(infinitive)) {
			conjugator = new ConjugationEler2();
		} else if (infinitive.endsWith("cer")) { 
			conjugator = new ConjugationCer();
		} else if (infinitive.endsWith("ger")) { 
			conjugator = new ConjugationGer();
		} else { 
			conjugator = new ConjugationGroup1();
		}
	 
		return conjugator;
	}
	
	private static final String[] EXCEPTION_ELER = new String[] { 
			"acheter", "geler"	
		}; 
			
		private static boolean isExceptionEler(String infinitive) {
			boolean exception = Arrays.asList(EXCEPTION_ELER).contains(infinitive); 
			return exception;
		}
			
		private static boolean endsWithECer(String infinitive) {
			boolean endsWithECer = false;
			infinitive = infinitive.replaceAll("�", "e");
			endsWithECer = endsWithECer || infinitive.endsWith("ecer");
			endsWithECer = endsWithECer || infinitive.endsWith("echer");
			endsWithECer = endsWithECer || infinitive.endsWith("ecrer");
			endsWithECer = endsWithECer || infinitive.endsWith("eder");
			endsWithECer = endsWithECer || infinitive.endsWith("egrer");
			endsWithECer = endsWithECer || infinitive.endsWith("eguer");
			endsWithECer = endsWithECer || infinitive.endsWith("eler");
			endsWithECer = endsWithECer || infinitive.endsWith("emer");
			endsWithECer = endsWithECer || infinitive.endsWith("ener");
			endsWithECer = endsWithECer || infinitive.endsWith("equer");
			endsWithECer = endsWithECer || infinitive.endsWith("erer");
			endsWithECer = endsWithECer || infinitive.endsWith("eser");
			endsWithECer = endsWithECer || infinitive.endsWith("eter");
			endsWithECer = endsWithECer || infinitive.endsWith("etrer");
			endsWithECer = endsWithECer || infinitive.endsWith("ever");
			endsWithECer = endsWithECer || infinitive.endsWith("eyer");
			endsWithECer = endsWithECer || infinitive.endsWith("evrer");
			return endsWithECer;
		}
	
	public void conjugate(String infinitive) {
		int idx = infinitive.indexOf("er");
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
	
	protected String getEnding(int pers) { 
		return ENDINGS[pers];
	}

    //
	// inner classes
	// 
	public static class ConjugationOyer extends ConjugationGroup1 {
		protected String changeRadical(String radical, int pers) {
			String changed = radical;  
			
			if ((pers < 3) || (pers == 5)) { 
			  int len = radical.length();
			  changed = radical.substring(0, len - 1) + 'i'; 
			} 
			
			return changed;
		}
	}
	
	public static class ConjugationEler extends ConjugationGroup1 {
		protected String changeRadical(String radical, int pers) {
			String changed = radical;  
			char lastLetter = radical.charAt(radical.length()-1);
			
			if ((pers < 3) || (pers == 5)) { 
				changed = radical + lastLetter;
			}
			
			return changed;
		}
	}
	
	public static class ConjugationEler2 extends ConjugationGroup1 {
		protected String changeRadical(String radical, int pers) {
			String changed = radical;  
			int len = radical.length();
			
			if ((pers < 3) || (pers == 5)) { 
				char lastLetter = radical.charAt(len-1);
				changed = radical.substring(0, len - 2) + '�' + lastLetter; 
			}
			
			return changed;
		}
	}
	
	public static class ConjugationCer extends ConjugationGroup1 {
		protected String changeRadical(String radical, int pers) {
			String changed = radical;  
			
			if (pers == 3) { 
				changed = radical.substring(0, radical.length() - 1) + '�';
			}
			
			return changed;
		}
	}
	
	public static class ConjugationGer extends ConjugationGroup1 {
		protected String changeRadical(String radical, int pers) {
			String changed = radical;  
			
			if (pers == 3) { 
				changed = radical + "e";
			}
			
			return changed;
		}
	}

}
