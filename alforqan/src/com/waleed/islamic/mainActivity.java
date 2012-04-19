package com.waleed.islamic;

/**
 * 'imports
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author waleed0
 * main activity
 */
public class mainActivity extends Activity implements OnClickListener {
	
	/**
	 * local fields
	 */
	ImageButton quranTextPlusZoomButton;
	ImageButton quranTextMinusZoomButton;
	TextView quranDisplayTextView;
	float quranTextSize;
	Spinner suraSelectSpinner;
	Spinner startAyaSelectSpinner;
	Spinner endAyaSelectSpinner;
	Spinner stopPeriodSelectSpinner;
	//spinner array adapters
	ArrayAdapter<String> startAyaAdapter, suraNameAdapter, endAyaAdapter, stopPeriodAdapter;
	//arrays to be used with the adapters
	String[] suraNamesStrings = {""}, ayaNumStrings = {""}, stopPeriodsStrings = {"طول الاية", "ضعف طول الاية", 
			"ثلاثة اضعاف طول الاية", "اربعة اضعاف طول الاية"};

	// Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize gui components
		initializeGui();
	}

	private void initializeGui() {
		// Get gui references
		quranTextPlusZoomButton = (ImageButton) findViewById(R.id.quranTextZoomPlusButton);
		quranTextMinusZoomButton = (ImageButton) findViewById(R.id.quranTextZoomMinusButton);
		quranDisplayTextView = (TextView) findViewById(R.id.quranDisplayTextView);
		suraSelectSpinner = (Spinner) findViewById(R.id.suraSelectSpiner);
		startAyaSelectSpinner = (Spinner) findViewById(R.id.startAyaSelectSpinner);
		endAyaSelectSpinner = (Spinner) findViewById(R.id.endAyaSelectSpinner);
		stopPeriodSelectSpinner = (Spinner) findViewById(R.id.stopPeriodSelectSpinner);
		
		//initialize spinners adapters
		suraNameAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout);
		startAyaAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout);
		endAyaAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout);
		stopPeriodAdapter = new ArrayAdapter<String>(this, R.layout.textviewlayout);
		
		//initialize every adapter with a string
		initializeAdapter(suraNameAdapter, suraNamesStrings);
		initializeAdapter(startAyaAdapter, ayaNumStrings);
		initializeAdapter(endAyaAdapter, ayaNumStrings);
		initializeAdapter(stopPeriodAdapter, stopPeriodsStrings);
		
		//add adapters to spinners
		suraSelectSpinner.setAdapter(suraNameAdapter);
		startAyaSelectSpinner.setAdapter(startAyaAdapter);
		endAyaSelectSpinner.setAdapter(endAyaAdapter);
		stopPeriodSelectSpinner.setAdapter(stopPeriodAdapter);
		
		// get the values of some variables
		quranTextSize = quranDisplayTextView.getTextSize();

		// set the listeners
		quranTextPlusZoomButton.setOnClickListener(this);
		quranTextMinusZoomButton.setOnClickListener(this);		
	}
	
	void initializeAdapter(ArrayAdapter<String> usedAdapter, String[] usedString){
		usedAdapter.clear();
		for (String s : usedString) {
			usedAdapter.add(s);
		}
	}

	/**
	 * the class controls onclick listener
	 * Inputs: v is a reference to the view that caused the event
	 */
	@Override
	public void onClick(View v) {
		// zooming code
		if (v == quranTextPlusZoomButton) {
			quranTextSize++;
			quranDisplayTextView.setTextSize(quranTextSize);
		} else if (v == quranTextMinusZoomButton) {
			quranTextSize--;
			quranDisplayTextView.setTextSize(quranTextSize);
		}
	}
	
	
}
