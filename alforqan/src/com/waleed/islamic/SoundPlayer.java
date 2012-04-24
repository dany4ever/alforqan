/**
 * 
 */
package com.waleed.islamic;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;

/**
 * @author waleed0
 * This class will be responsible of the sound playing control
 */
public class SoundPlayer implements OnPreparedListener{
	
	/**
	 * locals
	 */
	private MediaPlayer usedPlayer;
	private boolean opened;
	public long soundFilePeriod;
	private volatile boolean preparingComplete;
	private String oldPath; // the path of the last opened file to compare with
	
	/**
	 * default constructor, initialize the object
	 */
	public SoundPlayer(Context callingActivityContext){
		opened = false;
		soundFilePeriod = 0;
		preparingComplete = false;
		usedPlayer = new MediaPlayer();
		usedPlayer.create(callingActivityContext, Uri.EMPTY);
		// set the player completion listener to play the next sound
		usedPlayer.setOnPreparedListener(this);
	}
	
	/**
	 * open a file and prepare it to be played using the play method
	 * @param filePath: the path of the file to be opened
	 * @return a boolean value indicates if the operation succeeded or not
	 */
	private boolean openSoundFile(String filePath){
		boolean success = true;
		try {
			usedPlayer.reset();
			usedPlayer.setDataSource(filePath);
			usedPlayer.prepare();
			preparingComplete = false;
			opened = true;
			oldPath = filePath;
		} catch(Exception e){
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
		if(usedPlayer.isPlaying()){
			usedPlayer.stop();  // stop the currently playing file
		}
		try{
			usedPlayer.prepare();  // re-preparing the player
			preparingComplete = false;
			opened = false;
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
		if(!opened){  // open file if not opened
			openSoundFile(filePath);
		}else if(!filePath.equals(oldPath)){
			oldPath = filePath;
			openSoundFile(filePath);
		}
		try {
			while ((!preparingComplete) || (usedPlayer.isPlaying())) {
			}
			soundFilePeriod = (long) usedPlayer.getDuration();
			usedPlayer.start();
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

	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		synchronized (this) {
			preparingComplete = true;
		}
	}	
}
