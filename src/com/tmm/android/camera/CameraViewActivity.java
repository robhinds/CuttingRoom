package com.tmm.android.camera;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tmm.android.camera.util.Constants;

public class CameraViewActivity extends Activity implements SurfaceHolder.Callback, OnClickListener{

	static final int FOTO_MODE = 0;
	Camera mCamera;
	boolean mPreviewRunning = false;
	
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private Context mContext = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.camera_surface);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		CharSequence text = "Tap the screen to take a photo";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(mContext, text, duration);
		toast.show();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}
		
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		if(display.getOrientation() == Surface.ROTATION_0){
			mCamera.setDisplayOrientation(90);
        } else if(display.getOrientation() == Surface.ROTATION_270){
        	mCamera.setDisplayOrientation(180);
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	
	public void onClick(View view) {
		mCamera.takePicture(null, mPictureCallback, mPictureCallback);
	}
	
	
	
	/**
	 * listener to handle picture taking
	 */
	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {
				Intent i = new Intent(mContext, PreviewActivity.class);
				i.putExtra("CAPTURED_IMAGE", imageData);
				Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
				i.putExtra("DISPLAY_ORIENTATION", display.getOrientation());
				i.putExtra("DISPLAY_WIDTH", display.getWidth());
				i.putExtra("DISPLAY_HEIGHT", display.getHeight());
				startActivityForResult(i, Constants.PREVIEWACTIVITY);
				
				mCamera.startPreview();
				setResult(FOTO_MODE, i);
				//finish();
			}
		}
	};

}
