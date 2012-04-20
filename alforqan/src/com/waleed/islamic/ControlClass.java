/**
 * 
 */
package com.waleed.islamic;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

/**
 * @author waleed0
 * this class will control the sound and text diplay operations,
 * it will contain static members, so we don't need objects from it
 */
public class ControlClass {
	
	// static locals
	private static Context usedContext;
	private static SoundPlayer usedSoundPlayer;
	private static dbDAL usedDBDAL;
	private static String baseDirectory;
	private static String soundFolderInSD = ""; // holds the application folder in
												 // the SD card, this folder contains
												// all application sounds
	
	/**
	 * initialize the class
	 * @param thisContext : the calling class Context
	 * @param shikhFolderPath : the folder containing the selected sikh sound files ended with \\
	 */
	public static void initializeControlClass(Context thisContext, String shikhFolderPath){
		baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath(); // get the sd card directory
		soundFolderInSD = baseDirectory + "/" + shikhFolderPath;
		usedContext = thisContext;
		usedSoundPlayer = new SoundPlayer(thisContext);
	}
	
	/**
	 * Gets the text of the aya using aya number and sura number
	 * @param suraNum : String holds the sura number
	 * @param ayaNum : String holds the aya number
	 * @return A String holds the aya text
	 */
	public static String getAyaText(String suraNum, String ayaNum){
		String ayaText = "";
		usedDBDAL = new dbDAL(null);
		usedDBDAL.openDataBase(); // open the DB
		ayaText = usedDBDAL.getAyaText(suraNum, ayaNum);
		usedDBDAL.close(); // close the DB
		
		// TODO testing-to be removed
		constructAyaSoundFilePath("2", "2");
		return ayaText;
	}
	
	/**
	 * set the sd directory
	 * @param directoryPath : path of the Directory
	 */
	public static void setSdDirectory(String directoryPath){
		soundFolderInSD = directoryPath;
	}
	
	/**
	 * constructs the file path of the aya sound file givin aya number and sura number
	 * @param suraNum : string holds the sura number
	 * @param ayaNum : string holds the aya number
	 * @return A String holds the constructed path
	 */
	private static String constructAyaSoundFilePath(String suraNum, String ayaNum){
		String filePath = soundFolderInSD;
		try {
			suraNum = String.format("%03d", Integer.parseInt(suraNum));
			ayaNum = String.format("%03d", Integer.parseInt(ayaNum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		filePath +=  suraNum + ayaNum + ".mp3";
		
		//MediaPlayer myPlayer = MediaPlayer.create(usedContext, Uri.parse(filePath));
		usedSoundPlayer.playSoundFile(filePath);
		return filePath;
	}
}
