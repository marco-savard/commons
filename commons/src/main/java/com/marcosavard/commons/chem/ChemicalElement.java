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

	private static final Integer[] INERT_GASES = new Integer[] {2, 10, 18, 36, 54, 86, 118};
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
		List<Integer> orbitalList = Arrays.asList(INERT_GASES); 
		int orbital = orbitalList.stream().filter(i -> (i >= atomicNumber)).findFirst().orElse(0); 
		int period = orbitalList.indexOf(orbital) + 1; 
		return period;
	}

	public int getAtomicFamily() {
		int atomicNumber = getAtomicNumber(); 
		int period = getAtomicPeriod(); 
		int orbital = (period == 1) ? 0 : INERT_GASES[period-2]; 
		int family = atomicNumber - orbital;
		return family;
	}
	
	public boolean isInertGas() {
		List<Integer> orbitalList = Arrays.asList(INERT_GASES); 
		return orbitalList.contains(getAtomicNumber()); 
	}

	public static List<ChemicalElement> getPeriod(int periodNumber) {
		List<ChemicalElement> allElements = Arrays.asList(ChemicalElement.values()); 
		List<ChemicalElement> period = allElements.stream().filter(e -> e.getAtomicPeriod() == periodNumber).collect(Collectors.toList()); 
		return period;
	}

	
}
