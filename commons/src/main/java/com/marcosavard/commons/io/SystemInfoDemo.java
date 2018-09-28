package com.marcosavard.commons.io;

public class SystemInfoDemo {
	
	public static void main(String[] args) {
	  SystemInfo.OperatingSystem os = SystemInfo.findOperatingSystem();
	  System.out.println(" Operating System : " + os);
		
	}
}
