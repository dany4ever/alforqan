package com.waleed.islamic;

/**
 * 'imports
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author waleed0 main activity
 */
public class mainActivity extends Activity implements OnClickListener,
		OnDrawerOpenListener, OnDrawerCloseListener {

	/**
	 * local fields
	 */
	// buttons
	Button collapseButton;
	// image buttons
	ImageButton quranTextPlusZoomButton;  // zoom in button
	ImageButton quranTextMinusZoomButton;  // zoom out button
	ImageButton soundPlayImageButton;  // play button
	ImageButton soundPauseImageButton;  // pause button
	// text views
	TextView quranDisplayTextView;
	// Edit texts
	EditText ayaRepeatNumEditText;
	EditText groupRepeatNumEditText;
	// spinners
	Spinner suraSelectSpinner;
	Spinner startAyaSelectSpinner;
	Spinner endAyaSelectSpinner;
	Spinner stopPeriodSelectSpinner;
	// sliding drawers
	SlidingDrawer settingsSlidingDrawer;
	// layouts
	LinearLayout mainPartLayout;
	// spinner array adapters
	ArrayAdapter<String> startAyaAdapter, suraNameAdapter, endAyaAdapter,
			stopPeriodAdapter;
	// arrays to be used with the adapters
	String[] suraNamesStrings = { "" }, ayaNumStrings = { "" },
			stopPeriodsStrings = { "طول الاية", "2 x طول الاية",
					"3 x طول الاية", "4 x طول الاية" };
	// variables
	float quranTextSize;
	// user entered values
	int suraNameIndex = 0, startAya = 0, endAya = 0, stopPeriod = 0,
			ayaRepeat = 0, groupRepeat = 0;

	// Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the full screen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		// set the contents of the view
		setContentView(R.layout.main);

		// get the values passed from the splach activity
		suraNamesStrings = getIntent().getExtras().getStringArray("surasNames"); // get suras names
		ayaNumStrings = getIntent().getExtras().getStringArray("suraAyas"); // get ayas of a sura (numbers
																			// not text)

		// Initialize gui components
		initializeGui();
	}

	private void initializeGui() {
		// Get gui references
		// buttons & image buttons
		collapseButton = (Button) findViewById(R.id.collapseToggleButton);
		quranTextPlusZoomButton = (ImageButton) findViewById(R.id.quranTextZoomPlusButton);
		quranTextMinusZoomButton = (ImageButton) findViewById(R.id.quranTextZoomMinusButton);
		soundPlayImageButton = (ImageButton) findViewById(R.id.playButton);
		soundPauseImageButton = (ImageButton) findViewById(R.id.pauseButton);
		// Text views
		quranDisplayTextView = (TextView) findViewById(R.id.quranDisplayTextView);
		// Spinners
		suraSelectSpinner = (Spinner) findViewById(R.id.suraSelectSpiner);
		startAyaSelectSpinner = (Spinner) findViewById(R.id.startAyaSelectSpinner);
		endAyaSelectSpinner = (Spinner) findViewById(R.id.endAyaSelectSpinner);
		stopPeriodSelectSpinner = (Spinner) findViewById(R.id.stopPeriodSelectSpinner);
		// sliding drawers
		settingsSlidingDrawer = (SlidingDrawer) findViewById(R.id.settingsSlidingDrawer);
		// layouts
		mainPartLayout = (LinearLayout) findViewById(R.id.mainBlock);
		// Edit views
		ayaRepeatNumEditText = (EditText) findViewById(R.id.ayarepeatenteryEditText);
		groupRepeatNumEditText = (EditText) findViewById(R.id.grouprepeatenteryEditText);

		// initialize spinners adapters
		suraNameAdapter = new ArrayAdapter<String>(this,
				R.layout.textviewlayout);
		startAyaAdapter = new ArrayAdapter<String>(this,
				R.layout.textviewlayout);
		endAyaAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout);
		stopPeriodAdapter = new ArrayAdapter<String>(this,
				R.layout.textviewlayout);

		// initialize every adapter with a string
		initializeAdapter(suraNameAdapter, suraNamesStrings);
		initializeAdapter(startAyaAdapter, ayaNumStrings);
		initializeAdapter(endAyaAdapter, ayaNumStrings);
		initializeAdapter(stopPeriodAdapter, stopPeriodsStrings);
		
		// initialize ControlClass
		ControlClass.initializeControlClass(this);

		// add adapters to spinners
		suraSelectSpinner.setAdapter(suraNameAdapter);
		startAyaSelectSpinner.setAdapter(startAyaAdapter);
		endAyaSelectSpinner.setAdapter(endAyaAdapter);
		stopPeriodSelectSpinner.setAdapter(stopPeriodAdapter);

		// get the values of some variables
		quranTextSize = quranDisplayTextView.getTextSize();

		// set the listeners
		// buttons listeners
		quranTextPlusZoomButton.setOnClickListener(this);
		quranTextMinusZoomButton.setOnClickListener(this);
		soundPlayImageButton.setOnClickListener(this);
		soundPauseImageButton.setOnClickListener(this);
		// sliding drawer listeners
		settingsSlidingDrawer.setOnDrawerOpenListener(this);
		settingsSlidingDrawer.setOnDrawerCloseListener(this);
	}

	void initializeAdapter(ArrayAdapter<String> usedAdapter, String[] usedString) {
		usedAdapter.clear();
		for (String s : usedString) {
			usedAdapter.add(s);
		}
	}

	/**
	 * the class controls on click listener Inputs: v is a reference to the view
	 * that caused the event
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Zoom in
		case R.id.quranTextZoomPlusButton:
			quranTextSize++;
			quranDisplayTextView.setTextSize(quranTextSize);
			break;
		// zoom out
		case R.id.quranTextZoomMinusButton:
			quranTextSize--;
			quranDisplayTextView.setTextSize(quranTextSize);
			break;
		// sound play button event
		case R.id.playButton:
			ControlClass.setSshikh("minshawi/");  // set shikh
			ControlClass.testMethod("2", "2", 1);
			break;
		// sound pause button event
		case R.id.pauseButton:
			ControlClass.setSshikh("minshawi/"); // set shikh
			ControlClass.testMethod("2", "2", 2);
			break;
		}
	}

	@Override
	public void onDrawerClosed() {
		mainPartLayout.setVisibility(0); // else show the main part
		collapseButton.setText(R.string.collapse_button_collapsed); // write suitable text to the button
		// getting the values entered by the user
		suraNameIndex = suraSelectSpinner.getSelectedItemPosition();
		startAya = startAyaSelectSpinner.getSelectedItemPosition();
		endAya = endAyaSelectSpinner.getSelectedItemPosition();
		stopPeriod = stopPeriodSelectSpinner.getSelectedItemPosition();
		if (ayaRepeatNumEditText.getText().toString().length() != 0) { // check if left blank
			ayaRepeat = Integer.parseInt(ayaRepeatNumEditText.getText().toString());
		}else{
			ayaRepeat = 0; // default is no repeat
		}
		if (groupRepeatNumEditText.getText().toString().length() != 0) { // check if left blank
			groupRepeat = Integer.parseInt(groupRepeatNumEditText.getText().toString());
		}else{
			groupRepeat = 0; // default is no repeat
		}
	}

	@Override
	public void onDrawerOpened() {
		// main method job
		mainPartLayout.setVisibility(4); // hide the main part
		collapseButton.setText(R.string.collapse_button_folded); // write suitable text to the button
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * this will respond to the hardware volume buttons
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean success = true;
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			// audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
			// AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			success = true;
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			// audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
			// AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			success = true;
			break;
		default:
			success = false;
		}
		return success;
	}
	
}
