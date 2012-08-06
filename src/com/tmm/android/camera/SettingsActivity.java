package com.tmm.android.camera;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import com.tmm.android.camera.application.CameraApplication;
import com.tmm.android.camera.util.Constants;

/**
 * @author robert.hinds
 *
 */
public class SettingsActivity extends Activity implements OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        /**
         * set listener on update button
         */
        Button updateBtn = (Button) findViewById(R.id.nextBtn);
		updateBtn.setOnClickListener(this);
		
		/**
		 * Set selected button if saved
		 */
		updateButtonWithPreferences();
		
    }


	/**
	 * Method to update default check box
	 */
	private void updateButtonWithPreferences() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		RadioButton c3 = (RadioButton)findViewById(R.id.hardSetting);
		
		SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
		int diff = settings.getInt(Constants.IMAGESIZE, Constants.MEDIUM);
		
		switch (diff)
		{
		case Constants.HIGH : 
			c1.toggle();
			break;
			
		case Constants.MEDIUM : 
			c2.toggle();
			break;
			
		case Constants.LOW :
			c3.toggle();
			break;	
		}
	}





	@Override
	public void onClick(View arg0) {
		/**
		 * check which settings set and return to menu
		 */
		if (!checkSelected())
		{
			return;
		}
		else
		{
			SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
			Editor e = settings.edit();
			e.putInt(Constants.IMAGESIZE, getSelectedSetting());
			e.commit();
			CameraApplication app = ((CameraApplication)getApplication());
			app.updateImageQuality();
			finish();
		}
		
	}


	/**
	 * Method to check that a checkbox is selected
	 * 
	 * @return boolean
	 */
	private boolean checkSelected() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		RadioButton c3 = (RadioButton)findViewById(R.id.hardSetting);
		return (c1.isChecked() || c2.isChecked() || c3.isChecked());
	}


	/**
	 * Get the selected setting 
	 */
	private int getSelectedSetting() {
		RadioButton c1 = (RadioButton)findViewById(R.id.easySetting);
		RadioButton c2 = (RadioButton)findViewById(R.id.mediumSetting);
		if (c1.isChecked())
		{
			return Constants.HIGH;
		}
		if (c2.isChecked())
		{
			return Constants.MEDIUM;
		}
		
		return Constants.LOW;
	}
	
}
