/**
 * 
 */
package com.waleed.islamic;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

/**
 * @author waleed0
 * this class will control the sound and text display operations,
 * it will contain static members, so we don't need objects from it
 */
public class ControlClass {
	
	// static locals
	private static SoundPlayer usedSoundPlayer;
	private static dbDAL usedDBDAL;
	private static String baseDirectory;
	public static boolean soundThreadPaused;
	private static String soundFolderInSD = ""; // holds the application folder in the SD card, this folder contains
												// all application sounds
	
	/**
	 * initialize the class
	 * @param thisContext : the calling class Context
	 * @param shikhFolderPath : the folder containing the selected sikh sound files ended with \\
	 */
	public static void initializeControlClass(Context thisContext){
		usedSoundPlayer = new SoundPlayer(thisContext);
	}
	
	/**
	 * Gets the text of the aya using aya number and sura number
	 * @param suraNum : String holds the sura number
	 * @param ayaNum : String holds the aya number
	 * @return A String holds the aya text
	 */
	private static String getAyaText(String suraNum, String ayaNum){
		String ayaText = "";
		usedDBDAL = new dbDAL(null);
		usedDBDAL.openDataBase(); // open the DB
		ayaText = usedDBDAL.getAyaText(suraNum, ayaNum);
		usedDBDAL.close(); // close the DB
		return ayaText;
	}
	
	/**
	 * set the sd directory
	 * @param directoryPath : path of the Directory
	 */
	public static void setSshikh(String sikhDirectoryName){
		baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath(); // get the sd card directory
		soundFolderInSD = baseDirectory + "/alforqan/";
		soundFolderInSD += sikhDirectoryName;
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
		return filePath;
	}
	
	/**
	 * play the quran sound with the selected settings
	 * @param suraIndex : the index of the selected sura
	 * @param startAya : the aya to start from
	 * @param endAya : the aya to end with
	 * @param stopPeriod : the stop period between two successive sounds in multiples of aya period
	 *  zero indicates no stop
	 * @param ayaRepeat : number of times to successively repeat one aya
	 * @param qroupRepeat : the number of times to repeat all the aya group
	 * @return a boolean indicates whether the operation completed successfully or not
	 */
	public static boolean playWithSelectedSettings(int suraIndex, int startAya,
			int endAya, int stopPeriod, int ayaRepeat, int qroupRepeat, Thread thisThread, Handler usedHandler) {
		boolean success = true;
		String filePath;
		String ayaText;
		Bundle usedBundle = new Bundle();
		Message msg;
		// main method function
		for (int groupIndex = 0; groupIndex <= qroupRepeat; groupIndex++) { // group repeating loop
			for (int currentAya = startAya; currentAya <= endAya; currentAya++) { // loop at all ayas
				// check if the file is basmala open it
			    filePath = constructAyaSoundFilePath(Integer.toString(suraIndex), Integer.toString(currentAya)); // other ayas sounds
				ayaText = getAyaText(Integer.toString(suraIndex), Integer.toString(currentAya));
				usedBundle.clear();
				usedBundle.putString("text", ayaText);
				msg = new Message();
				msg.setData(usedBundle);
				while(ControlClass.soundThreadPaused){} // pause the thread before typing the new aya text if pause button clicked
				usedHandler.sendMessage(msg);
				for (int ayaRepeatIndex = 0; ayaRepeatIndex <= ayaRepeat; ayaRepeatIndex++) { // aya repeating loop
					usedSoundPlayer.playSoundFile(filePath);
						try {
							Thread.sleep((usedSoundPlayer.soundFilePeriod + 10) * stopPeriod);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
		return success;
	}
}
