package com.tmm.android.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.vending.licensing.LicenseChecker;
import com.android.vending.licensing.LicenseCheckerCallback;
import com.android.vending.licensing.ServerManagedPolicy;
import com.android.vending.licensing.AESObfuscator;

import com.tmm.android.camera.application.CameraApplication;
import com.tmm.android.camera.util.Constants;

public class SplashActivity extends Activity implements OnClickListener{
	
	private LicenseCheckerCallback mLicenseCheckerCallback;
	private LicenseChecker mChecker;
	
	// Generate 20 random bytes, and put them here.
    private static final byte[] SALT = new byte[] {
     -83, 34, 19, -108, -03, -73, 46, -68, 23, -99, -23,
     -49, 112, -10, -76, -1, -21, 65, -84, 29};
    
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAguxrQeDRLm5r4kuDXQqQUAVjMf5gUa38T1i7uHoG79GWy4Su4TOUuJIlyr/bbziCSZic33S4ueC+4r9QWqwewkIdyPcjv4CLee7rGXGiLsy6zcfv17ysSn0YpTcbjIhy0gQqCSFPEf29Lnf8Q8GG3vn0C7x9ZeOFIdEDctrUBVNnJ0rKoQsMqPPlwmLG4YR9myE0O4M1HmkJMZtHWXA0ajrV4Gg2Gdz3chrv3lYaiOL4gLSyuh3ywduXLHwvyeBHVyfkQT1OJQdgTwgpCyQ7+/SmvJbLfw1fsWbVO/nA6KGaD0tiL98izQhWvmUA7A4ZIixOmfAcWzCg7PmJ8zzL8QIDAQAB";

    private Builder exit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		// Construct the LicenseCheckerCallback. The library calls this when done.
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();

        String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		
		// Construct the LicenseChecker with a Policy.
		mChecker = new LicenseChecker(
				this, new ServerManagedPolicy(this,
						new AESObfuscator(SALT, getPackageName(), deviceId)),
						BASE64_PUBLIC_KEY  // Your public licensing key.
		);
		

		//check licence permissions
        mChecker.checkAccess(mLicenseCheckerCallback);


		Button playBtn = (Button) findViewById(R.id.startBtn);
		System.out.println(playBtn);
		playBtn.setOnClickListener(this);
		Button rulesBtn = (Button) findViewById(R.id.settingsBtn);
		rulesBtn.setOnClickListener(this);
		Button exitBtn = (Button) findViewById(R.id.exitBtn);
		exitBtn.setOnClickListener(this);
		
		CameraApplication app = ((CameraApplication)getApplication());
		app.updateImageQuality();
		
		exit = new AlertDialog.Builder(this)
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .setTitle("")
	    .setMessage("App is not licenced - Please purchase from Market")
	    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog, int which) {
	    		finish();    
	    	}
	    });
	}


	/**
	 * Listener for game menu
	 */
	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()){
		case R.id.startBtn :
			i = new Intent(this, CameraViewActivity.class);
			startActivityForResult(i, Constants.CAMERAACTIVITY);
			break;
			
		case R.id.exitBtn :
			finish();
			break;
			
		case R.id.settingsBtn :
			i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, Constants.SETTINGACTIVITY);
			break;
		}
	}
	
	
	 private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
	        public void allow() {
	            if (isFinishing()) {
	                // Don't update UI if Activity is finishing.
	                return;
	            }
	        }

	        public void dontAllow() {
	            if (isFinishing()) {
	                // Don't update UI if Activity is finishing.
	                return;
	            }
	            exit.show();
	        }

			@Override
			public void applicationError(ApplicationErrorCode errorCode) {
				Log.e("LICENSE", errorCode.toString());
				dontAllow();
			}
	    }
	 
	 
	 @Override
	 protected void onDestroy() {
		 super.onDestroy();
		 mChecker.onDestroy();
	 }
}