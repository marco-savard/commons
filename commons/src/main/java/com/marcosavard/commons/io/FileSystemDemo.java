package com.marcosavard.commons.io;

import java.io.File;

public class FileSystemDemo {

	public static void main(String[] args) {
		//get folders		
		File userFolder = FileSystem.getUserFolder(); 
		File userDesktopFolder = FileSystem.getUserDesktopFolder(); 
		File userDownloadFolder = FileSystem.getUserDownloadFolder(); 
		File userDocumentFolder = FileSystem.getUserDocumentFolder(); 
		File userMusicFolder = FileSystem.getUserMusicFolder(); 
		File userPictureFolder = FileSystem.getUserPictureFolder(); 
		File temporaryFolder = FileSystem.getTemporaryFolder(); 
		File jreFolder = FileSystem.getJreFolder(); 
		File classFolder = FileSystem.getClassFolder(); 
		File currentWorkingFolder = FileSystem.getCurrentWorkingFolder(); 
		
		System.out.println("  User folder : " + userFolder);
		System.out.println("  User Document folder : " + userDocumentFolder);
		System.out.println("  User Desktop folder : " + userDesktopFolder);
		System.out.println("  User Download folder : " + userDownloadFolder);
		System.out.println("  User Music folder : " + userMusicFolder);
		System.out.println("  User Picture folder : " + userPictureFolder);
		System.out.println("  Temporary folder : " + temporaryFolder);
		System.out.println("  JRE folder : " + jreFolder);
		System.out.println("  Current working folder : " + currentWorkingFolder);
		System.out.println("  Class folder : " + classFolder);
	}

}
