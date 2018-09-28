package com.marcosavard.commons.io;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A facade class that returns info of the system, such as the kind of 
 * operating system. 
 * 
 * @author Marco
 *
 */
public class SystemInfo {
	public enum OperatingSystem {WINDOWS, MAC, UNIX, UNSUPPORTED}
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * Find the underlying operating system. 
	 * 
	 * @return Windows, Mac or Unix. 
	 * 
	 */
	public static OperatingSystem findOperatingSystem() {
		OperatingSystem os; 
		
		if (isWindows()) {
			os = OperatingSystem.WINDOWS; 
		} else if (isMac()) {
			os = OperatingSystem.MAC; 
		} else if (isUnix()) {
			os = OperatingSystem.UNIX; 
		} else {
			os = OperatingSystem.UNSUPPORTED;
		}
		
		return os;
	}
	
	public static String getLocalHostAddress() throws UnknownHostException {
		InetAddress host = InetAddress.getLocalHost(); 
		String address = host.getHostAddress(); 
		return address;
	}
	
	public static String getLocalHostName() throws UnknownHostException {
		InetAddress host = InetAddress.getLocalHost(); 
		String address = host.getHostName(); 
		return address;
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

}
