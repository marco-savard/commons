package com.marcosavard.commons.io;

import java.io.File;
import java.net.URL;

import javax.swing.JFileChooser;

/**
 * A facade class that returns commonly used folders. See FileSystemDemo. 
 * 
 * @author Marco
 *
 */
public class FileSystem {
	
	/**
	 * Return the user folder, generally C:\Users\MyName on Windows
	 * 
	 * @return the user folder
	 */
	public static File getUserFolder() {
		File homeFolder = new File(System.getProperty("user.home"));
		return homeFolder;
	}
	
	/**
	 * Return the folder for user's documents, generally C:\Users\MyName\Documents on Windows
	 * 
	 * @return the user folder
	 */
	public static File getUserDocumentFolder() {
		String folderName = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		File documentFolder = new File(folderName);
		return documentFolder;
	}

	/**
	 * Return the folder for user's desktop, generally C:\Users\MyName\Desktop on Windows
	 * 
	 * @return the user folder
	 */
	public static File getUserDesktopFolder() {
		String folderName = new JFileChooser().getFileSystemView().getHomeDirectory().toString();
		File documentFolder = new File(folderName);
		return documentFolder;
	}

	/**
	 * Return the folder for user's download folder, generally C:\Users\MyName\Downloads on Windows
	 * 
	 * @return the user folder
	 */
	public static File getUserDownloadFolder() {
		File homeFolder = new File(System.getProperty("user.home"));
		File downloadFolder = new File(homeFolder, "Downloads"); 
		return downloadFolder;
	}

	/**
	 * Return the folder for user's music folder. 
	 * 
	 * @return the user folder
	 */
	public static File getUserMusicFolder() {
		File userDocumentFolder = getUserDocumentFolder(); 
		File musicFolder;
		
		if (SystemInfo.isWindows()) {
			//get Music folder, sibling of Documents
			File parent = userDocumentFolder.getParentFile();
			musicFolder = new File(parent, "Music"); 
		} else {
			musicFolder = userDocumentFolder; 
		}

		return musicFolder;
	}

	/**
	 * Return the folder for user's pictures folder. 
	 * 
	 * @return the user folder
	 */
	public static File getUserPictureFolder() {
		File userDocumentFolder = getUserDocumentFolder(); 
		File pictureFolder;
		
		if (SystemInfo.isWindows()) {
			//get Pictures folder, sibling of Documents
			File parent = userDocumentFolder.getParentFile();
			pictureFolder = new File(parent, "Pictures"); 
		} else {
			pictureFolder = userDocumentFolder; 
		}

		return pictureFolder;
	}
	
	/**
	 * Return a folder to store temporary files.
	 * 
	 * @return the temporary folder
	 */
	public static File getTemporaryFolder() {
		File tmpFolder = new File(System.getProperty("java.io.tmpdir"));
		return tmpFolder;
	}
	
	/**
	 * Return the folder where Java is installed. 
	 * 
	 * @return the Java home folder
	 */
	public static File getJreFolder() {
		File jreFolder = new File(System.getProperty("java.home"));
		return jreFolder;
	}

	/**
	 * Return the current working folder.  
	 * 
	 * @return the current working folder folder
	 */
	public static File getCurrentWorkingFolder() {
		File homeFolder = new File(System.getProperty("user.dir"));
		return homeFolder;
	}
	
	/**
	 * Return the location of this class.
	 * 
	 * @param claz (default value FileSystem.class)
	 * @return the location of this class.
	 */
	public static File getClassFolder(Class claz) {
		String className = claz.getSimpleName() + ".class"; 		
		URL url = FileSystem.class.getResource(className); 
		File file = new File(url.getFile());
		File folder = file.getParentFile(); 
		return folder;
	}

	public static File getClassFolder() {
		return getClassFolder(FileSystem.class); 
	}
}