package com.marcosavard.commons.io;

public class FileSystemDemo {

	public static void main(String[] args) {
		//get and display folders				
		System.out.println("  User folder : " + FileSystem.getUserFolder());
		System.out.println("  User Document folder : " + FileSystem.getUserDesktopFolder());
		System.out.println("  User Desktop folder : " + FileSystem.getUserDownloadFolder());
		System.out.println("  User Download folder : " + FileSystem.getUserDocumentFolder());
		System.out.println("  User Music folder : " + FileSystem.getUserMusicFolder());
		System.out.println("  User Picture folder : " + FileSystem.getUserPictureFolder());
		System.out.println("  Temporary folder : " + FileSystem.getTemporaryFolder());
		System.out.println("  JRE folder : " + FileSystem.getJreFolder());
		System.out.println("  Current working folder : " + FileSystem.getClassFolder());
		System.out.println("  Class folder : " + FileSystem.getCurrentWorkingFolder());
	}

}
