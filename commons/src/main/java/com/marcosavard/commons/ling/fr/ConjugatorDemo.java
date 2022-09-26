package com.marcosavard.commons.ling.fr;

public class ConjugatorDemo {

	public static void main(String[] args) {	
		//demoGroup1(); 
		//demoGroup2(); 
		demoGroup3Oir();
		//demoGroup3Ir();
		//demoGroup3Re(); 
	}

	private static void demoGroup1() {
		Conjugator.conjugate("acheter");
		Conjugator.conjugate("aimer");
		Conjugator.conjugate("aller");
		Conjugator.conjugate("amener");
		Conjugator.conjugate("appeler");
		Conjugator.conjugate("c�der");
		Conjugator.conjugate("donner");
		Conjugator.conjugate("esp�rer");
		Conjugator.conjugate("essuyer");
		Conjugator.conjugate("fatiguer");
		Conjugator.conjugate("geler");
		Conjugator.conjugate("jeter");
		Conjugator.conjugate("lancer");
		Conjugator.conjugate("lever");
		Conjugator.conjugate("manger");
		Conjugator.conjugate("menacer");
		Conjugator.conjugate("nettoyer");
		Conjugator.conjugate("payer");
		Conjugator.conjugate("peser");
	}

	private static void demoGroup2() {
		Conjugator.conjugate("finir");
	}
	
	private static void demoGroup3Ir() {
		Conjugator.conjugate("aller");
		Conjugator.conjugate("conqu�rir");
		Conjugator.conjugate("courir");
		Conjugator.conjugate("cueillir");
		Conjugator.conjugate("dormir");
		Conjugator.conjugate("mourir");
		Conjugator.conjugate("ouvrir");
		Conjugator.conjugate("sentir");
		Conjugator.conjugate("sortir");
		Conjugator.conjugate("tenir");
		Conjugator.conjugate("venir");
	}
	
	private static void demoGroup3Oir() {
		Conjugator.conjugate("asseoir");
		Conjugator.conjugate("chaloir");
		Conjugator.conjugate("choir");
		Conjugator.conjugate("devoir");
		Conjugator.conjugate("falloir");
		Conjugator.conjugate("mouvoir");
		Conjugator.conjugate("pleuvoir");
		Conjugator.conjugate("pourvoir");
		Conjugator.conjugate("pouvoir");
		Conjugator.conjugate("recevoir");
		Conjugator.conjugate("savoir");
		Conjugator.conjugate("valoir");
		Conjugator.conjugate("voir");
		Conjugator.conjugate("vouloir");
	}

	private static void demoGroup3Re() {
		Conjugator.conjugate("construire");
		Conjugator.conjugate("craindre");
		Conjugator.conjugate("croire");
		Conjugator.conjugate("descendre");
		Conjugator.conjugate("joindre");
		Conjugator.conjugate("mettre");
		Conjugator.conjugate("peindre");
		Conjugator.conjugate("prendre");
		Conjugator.conjugate("r�soudre");
		Conjugator.conjugate("vendre");
	}

	//exceptions: dire, faire. 
}
