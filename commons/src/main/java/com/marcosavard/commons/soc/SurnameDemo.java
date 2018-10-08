package com.marcosavard.commons.soc;

import java.util.List;
import java.util.stream.Collectors;

public class SurnameDemo {

	public static void main(String[] args) {
		List<Surname> allSurnames = SurnameRepository.getSurnames();
		List<Surname> femaleSurnames = allSurnames.stream().filter(s -> s.getGender() == Surname.Gender.FEMALE).collect(Collectors.toList()); 
		femaleSurnames = femaleSurnames.subList(0, 100); 
		
		for (Surname surname : femaleSurnames) {
			System.out.println(surname);
		}

	}

}
