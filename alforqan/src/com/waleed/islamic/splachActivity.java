package com.waleed.islamic;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.media.MediaPlayer;
import android.os.Bundle;

public class splachActivity extends Activity {
	
	//class glopal fields
	String[] surasNames;
	String[] numOfSurasAyas;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splach);

		// make instance from the used objects
		surasNames = new String[114];
		final dbDAL tempDB = new dbDAL(this);
		
		// Thread to get the data from the database
				final Thread dbHandlingThread = new Thread() {
					@Override
					public void run() {
						// Handle the database copy to it's destination
						try {
							tempDB.createDataBase(); // if not exists, create the database and
														// copy the original one to it
							tempDB.openDataBase(); // opens the DB
							// Get the suras names
							surasNames = tempDB.getSurasNames();
							// Get the num of ayas in the current sura (initially the first sura)
							int count = tempDB.getSuraNumOfAyas(1); // al-fatiha
							// fill the array with ayas in the sura
							numOfSurasAyas = new String[count];
							for(int i = 0; i < count; i++){
								numOfSurasAyas[i] = Integer.toString(i + 1);
							}
							// close the DB
							tempDB.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				dbHandlingThread.start();
				
		// A Thread that play a sound, shows the slash activity for 6 seconds minimum, then start the main activity
		Thread waitThread = new Thread() {
			@Override
			public void run() {
				// Initiate and starting the media player object
				MediaPlayer myPlayer = MediaPlayer.create(splachActivity.this,
						R.raw.a0);
				myPlayer.start();
				try {
					sleep(6000);
					myPlayer.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						// Wait the DB handling thread to finish it,s work
						dbHandlingThread.join();
						// start the main activity and finish this activity thread
						Intent mainActivityIntent = new Intent(
								"com.waleed.islamic.alforqan.MAINACTIVITY");
						// put the data in the intent to be passed to the main activity
						mainActivityIntent.putExtra("surasNames", surasNames); // put suras names
						mainActivityIntent.putExtra("suraAyas", numOfSurasAyas);  // put the sura ayas numbers array
						startActivity(mainActivityIntent); // starting the activity
					} catch (Exception e) {
					}
				}
			}
		};
		waitThread.start(); // Starts the wait thread
	}

	// this method is called when this activity is going to be paused (calling
	// the main activity)
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}