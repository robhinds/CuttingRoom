package com.tmm.android.camera.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.tmm.android.camera.marvin.image.AndroidImage;
import com.tmm.android.camera.util.Constants;

public class CameraApplication extends Application {
	
	private int imageQuality;
	
	private AndroidImage originalImage;

	/**
	 * @return the imageQuality
	 */
	public int getImageQuality() {
		return imageQuality;
	}

	/**
	 * @param imageQuality the imageQuality to set
	 */
	public void setImageQuality(int imageQuality) {
		this.imageQuality = imageQuality;
	}

	/**
	 * @return the originalImage
	 */
	public AndroidImage getOriginalImage() {
		return originalImage;
	}

	/**
	 * @param originalImage the originalImage to set
	 */
	public void setOriginalImage(AndroidImage originalImage) {
		this.originalImage = originalImage;
	}

	
	public void updateImageQuality(){
		SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
		int diff = settings.getInt(Constants.IMAGESIZE, Constants.MEDIUM);
		setImageQuality(diff);
	}
}
