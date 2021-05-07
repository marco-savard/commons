package com.marcosavard.commons.geog.ca.qc.educ.res;

import java.util.List;

public class QuebecSchoolsDemo {

  public static void main(String[] args) {
    // get data
    QuebecSchools quebecSchools = QuebecSchools.ofType(QuebecSchool.class);
    List<QuebecSchool> schools = quebecSchools.read(8); 
    quebecSchools.close();
    
    // print columns
    List<String> columns = quebecSchools.getColumnNames(); 
    for (String col : columns) {
        System.out.print(col + "|");
    }
    System.out.println();
    
    // print values
    for (QuebecSchool school : schools) { 
    	System.out.println(school);
    }
    
    System.out.println();
  }

}
