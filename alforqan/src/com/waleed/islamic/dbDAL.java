package com.waleed.islamic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author waleed0 this is the database DAL
 */
public class dbDAL extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.waleed.islamic/databases/";

	private static String DB_NAME = "quran.db3";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public dbDAL(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database. if DB exists do no thing.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.
	
	//my own methods
	
	/**
	 * Get all the suras names
	 * @return String array contains all the suras names
	 */
	public int getSuraNumOfAyas(int suraNum){
		int suraNumOfAyas;
		Cursor myCursor = null;   //cursor to hold the data returned from the DB
		myCursor = myDataBase.rawQuery("SELECT ayas from suras where _id = " + Integer.toString(suraNum),new String[]{}); // get the num of ayas from the DB
		myCursor.moveToNext();// go to the result in the cursor
		suraNumOfAyas= myCursor.getInt(0);  // put the number in the variable
		return suraNumOfAyas;  // return the names to the caller
	}
	
	public String[] getSurasNames(){
		String[] surasNames = new String[114]; //string array containing the names
		Cursor myCursor = null;   //cursor to hold the data returned from the DB
		int index = 0;  // variable to be used as index
		myCursor = myDataBase.rawQuery("SELECT name from suras",new String[]{}); // get the names from the DB
		while(myCursor.moveToNext()){ // loop till getting all the names from the cursor
			surasNames[index] = myCursor.getString(0);  // store the names in the array
			index++;  // increase the index
		}
		return surasNames;  // return the names to the caller
	}
	
	/**
	 * get a row from the quran_text table with the given _id
	 * @param id : the _id of the row
	 * @return a cursor containing the returned data
	 */
	public Cursor getDBRowById(int id){
		Cursor myCursor = null;
		myCursor = myDataBase.rawQuery("SELECT * from quran_text where _id = " + Integer.toString(id),new String[]{});
		return myCursor;
	}
	/**
	 * return the text of the aya
	 * @param suraNum : the sura number as a string
	 * @param ayaNum : the aya number as a string
	 * @return A string containing the aya text
	 */
	public String getAyaText(String suraNum, String ayaNum){
		Cursor myCursor = null;
		String ayaText = "";
		try{
			myCursor = myDataBase.rawQuery("SELECT text from quran_text where sura = " + suraNum + " and aya = " + ayaNum,new String[]{});
			myCursor.moveToNext();
			ayaText = myCursor.getString(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ayaText;
	}
}
