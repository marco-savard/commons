package com.marcosavard.commons.chem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChemicalElement {
	H(1.008),
	He(4.0026),
	Li(6.94),
	Be(9.01218),
	B(10.81),
	C(12.011),
	N(14.0067),
	O(15.994), 
	F(18.9994),
	Ne(20.17),
	Na(22.9898),
	Mg(24.305),
	Al(26.9815),
	Si(28.086), 
	P(30.9738),
	S(32.06),
	Cl(34.453), 
	Ar(39.948), 
	K(39.102),
	Ca(40.08),
	Sc(44.9559),
	Ti(47.9),
	V(50.941),
	Cr(51.996),
	Mn(54.938),
	Fe(55.847),
	Co(58.9332),
	Ni(58.71),
	Cu(63.546),
	Zn(65.37),
	Ga(69.72),
	Ge(72.59),
	As(74.9216),
	Se(78.96),
	Br(79.904),
	Kr(83.80),
	Rb(85.467),
	Sr(87.62),
	Y(88.9059),
	Zr(91.22),
	Nb(92.9064),
	Mo(95.94),
	Tc(98.9062),
	Ru(101.07),
	Rh(102.9055),
	Pd(106.4),
	Ag(107.868),
	Cd(112.4),
	In(114.82),
	Sn(118.69),
	Sb(121.75),
	Te(127.6),
	I(126.9045),
	Xe(131.3)
	;

	public enum Category {ALKALI, ALKALINE_EARTH, METAL, METALLOID, NONMETAL, NOBLE_GAS}; 
	
	public static final int NOBLE_GAS_GROUP = 18;
	private static final Integer[] NOBLE_GASES = new Integer[] {2, 10, 18, 36, 54, 86, 118};
	private double atomicWeight; 
	
	private ChemicalElement(double atomicWeight) {
		this.atomicWeight = atomicWeight; 
	}
	
	public double getAtomicWeight() {
		return atomicWeight;
	}

	public int getAtomicNumber() {
		return this.ordinal() + 1; 
	}

	public int getAtomicPeriod() {
		int atomicNumber = getAtomicNumber(); 
		List<Integer> orbitalList = Arrays.asList(NOBLE_GASES); 
		int orbital = orbitalList.stream().filter(i -> (i >= atomicNumber)).findFirst().orElse(0); 
		int period = orbitalList.indexOf(orbital) + 1; 
		return period;
	}

	public int getAtomicGroup() {
		int atomicNumber = getAtomicNumber(); 
		int period = getAtomicPeriod(); 
		int orbital = (period == 1) ? 0 : NOBLE_GASES[period-2]; 
		int group = 0; 
		
		if (period == 1) {
			group = (atomicNumber == 1) ? 1 : NOBLE_GAS_GROUP; 
		} else if (period <= 3) {
			group = (atomicNumber - orbital <= 2) ? atomicNumber - orbital : NOBLE_GAS_GROUP - NOBLE_GASES[period-1] + atomicNumber;
		} else {
			group = (atomicNumber - orbital <= 3) ? atomicNumber - orbital : NOBLE_GAS_GROUP - NOBLE_GASES[period-1] + atomicNumber;
		}
		
		return group;
	}
	
	public static List<ChemicalElement> getPeriod(int periodNumber) {
		List<ChemicalElement> allElements = Arrays.asList(ChemicalElement.values()); 
		List<ChemicalElement> period = allElements.stream().filter(e -> e.getAtomicPeriod() == periodNumber).collect(Collectors.toList()); 
		return period;
	}

	public static ChemicalElement findElement(List<ChemicalElement> period, int atomicGroup) {
		ChemicalElement foundElement = period.stream().filter(e -> e.getAtomicGroup() == atomicGroup).findFirst().orElse(null); 
		return foundElement;
	}

	public Category getCategory() {
		int atomicNumber = getAtomicNumber(); 
		int period = getAtomicPeriod(); 
		int group = getAtomicGroup(); 
		Category category; 
		Category[] transition = new Category[] {Category.METAL, Category.METALLOID, Category.NONMETAL}; 
		
		if (atomicNumber == 1) {
			category = Category.NONMETAL; 
		} else if (group == 1) {
			category = Category.ALKALI; 
		} else if (group == 2) {
			category = Category.ALKALINE_EARTH; 
		} else if (group == NOBLE_GAS_GROUP) {
			category = Category.NOBLE_GAS; 	
		} else if (period == 2) {
			category = transition[getTransitionIndex(group, 13, 13)]; 
		} else if (period == 3) {
			category = transition[getTransitionIndex(group, 14, 14)]; 
		} else if (period == 4) {
			category = transition[getTransitionIndex(group, 14, 15)]; 
		} else if (period == 5) {
			category = transition[getTransitionIndex(group, 15, 16)]; 
		} else if (period == 6) {
			category = transition[getTransitionIndex(group, 17, 17)]; 
		} else {
			category = Category.METAL ; 
		}
		
		return category;
	}

	private int getTransitionIndex(int group, int start, int end) {
		int startIdx = (int)Math.signum(group - start); 
		int endIdx = (int)Math.signum(group - end); 
		return (startIdx * Math.abs(endIdx)) + 1;
	}

	
}
