/**
 * 
 */
package com.waleed.islamic;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * @author waleed0
 * This class will be responsible of the sound playing control
 */
public class SoundPlayer {
	
	/**
	 * locals
	 */
	private MediaPlayer usedPlayer;
	private boolean paused;
	private int soundPosition;
	
	/**
	 * default constructor, initialize the object
	 */
	public SoundPlayer(Context callingActivityContext){
		paused = false;
		soundPosition = 0;
		usedPlayer = new MediaPlayer();
		usedPlayer.create(callingActivityContext, Uri.EMPTY);
	}
	
	/**
	 * open a file and prepare it to be played using the play method
	 * @param filePath: the path of the file to be opened
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	private boolean openSoundFile(String filePath){
		boolean success = true;
		paused = false;
		if(usedPlayer.isPlaying()){
			usedPlayer.stop();  // stop the currently playing file
			usedPlayer.release();  // release the current file
		}
		try {
			usedPlayer.setDataSource(filePath);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * Close a file that was previously opened
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	public boolean closSoundFile(){
		boolean success = true;
		paused = false;
		if(usedPlayer.isPlaying()){
			usedPlayer.stop();  // stop the currently playing file
		}
		try{
			usedPlayer.release();  // release the current file
		}catch(Exception e){
			success = false;
			e.printStackTrace();
		}
		return success; 
	}
	
	/**
	 * Play a file that was previously opened
	 * 
	 * @param filePath
	 *            : the path of the file to be played
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	public boolean playSoundFile(String filePath) {
		boolean success = true;
		try {
			if (openSoundFile(filePath)) {
				usedPlayer.prepare();
				usedPlayer.start();
				if (paused) { // file was paused
					usedPlayer.seekTo(soundPosition); // continue from previous
														// position
					paused = false;
				}
			}else{
				success = false;
			}
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * pause a file that is now playing, if played again it will 
	 * continue from the place it was stopped at
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	public boolean pauseSoundFile(){
		boolean success = true;
		if(usedPlayer.isPlaying()){
			try{
				paused = true;  // set pause flag
				soundPosition = usedPlayer.getCurrentPosition(); // stores the current position
				usedPlayer.pause();  // release the current file
			}catch(Exception e){
				paused = false;  // reset pause flag
				success = false;
				e.printStackTrace();
			}
		}
		return success;
	}
	
	/**
	 * stop a file that is now playing, if played again it will 
	 * continue from the start of the file
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	public boolean stopSoundFile(){
		boolean success = true;
		try {
			usedPlayer.stop();
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * adjusts the volume of the player to a certain value
	 * @param volume : float holds the volume value
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	public boolean setVolume(float volume){
		boolean success = true;
		try{
		usedPlayer.setVolume(volume, volume);
		}catch(Exception e){
			success = false;
			e.printStackTrace();
		}
		return success;
	}
}
