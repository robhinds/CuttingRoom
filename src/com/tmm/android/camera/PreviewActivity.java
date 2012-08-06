/**
 * 
 */
package com.tmm.android.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tmm.android.camera.application.CameraApplication;
import com.tmm.android.camera.marvin.filters.BigBrotherFilter;
import com.tmm.android.camera.marvin.filters.BlackWhiteFilter;
import com.tmm.android.camera.marvin.filters.EdgeFilter;
import com.tmm.android.camera.marvin.filters.InvertFilter;
import com.tmm.android.camera.marvin.filters.MonitorFilter;
import com.tmm.android.camera.marvin.filters.NeonFilter;
import com.tmm.android.camera.marvin.filters.PixelateFilter;
import com.tmm.android.camera.marvin.image.AndroidImage;
import com.tmm.android.camera.quickaction.ActionItem;
import com.tmm.android.camera.quickaction.QuickAction;
import com.tmm.android.camera.util.CameraHelper;

/**
 * @author robert.hinds
 *
 */
public class PreviewActivity extends Activity implements OnClickListener{

	//image being manipulated
	private AndroidImage aImage;
	
	//original captured/loaded image
	private byte[] originalData;
	private int originalOrientation;
	private int imageQuality;
	private int originalWidth;
	private int originalHeight;
	
	//image view
	private ImageView imageView;
	
	//FX Menu Options
	final ActionItem invertAction = new ActionItem();
	final ActionItem tvAction = new ActionItem();
	final ActionItem pixelateAction = new ActionItem();
	final ActionItem bwAction = new ActionItem();
	final ActionItem bbAction = new ActionItem();
	final ActionItem outlineAction = new ActionItem();
	final ActionItem neonAction = new ActionItem();

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        
        CameraApplication app = ((CameraApplication)getApplication());
        imageQuality = app.getImageQuality();
        
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null) {
            //get data from intent
        	byte[] imageData = extras.getByteArray("CAPTURED_IMAGE");
            int orientation = extras.getInt("DISPLAY_ORIENTATION");
            originalWidth = extras.getInt("DISPLAY_WIDTH");
            originalHeight = extras.getInt("DISPLAY_HEIGHT");
            
            //set original image
            originalData = imageData;
            originalOrientation = orientation;
            
            //create image to be modified
            Bitmap picture = CameraHelper.previewImage(imageData, orientation, originalWidth, originalHeight, imageQuality);
            imageView = (ImageView) findViewById(R.id.captured_image);
            imageView.setImageBitmap(picture);
            aImage = new AndroidImage(picture);
            
        } else{
        	finish();
        }
        
        //create popup FX menu
		createFxMenu();
		
		//define the behaviour for core UI buttons
//		Button resetBtn = (Button) findViewById(R.id.resetBtn);
//        resetBtn.setOnClickListener(this);
		Button fxBtn = (Button) findViewById(R.id.fxBtn);
		fxBtn.setOnClickListener(this);
		Button saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {		
		switch (v.getId()){
		case R.id.fxBtn :
			QuickAction qa = new QuickAction(v);
			qa.addActionItem(outlineAction);
			qa.addActionItem(invertAction);
			qa.addActionItem(tvAction);
			qa.addActionItem(bwAction);
			qa.addActionItem(pixelateAction);
			qa.addActionItem(bbAction);
			qa.addActionItem(neonAction);
			qa.setAnimStyle(QuickAction.ANIM_AUTO);
			qa.show();
			break;
			
//		case R.id.resetBtn :
//			aImage = new AndroidImage(CameraHelper.previewImage(originalData, originalOrientation, 
//            		originalWidth, originalHeight, imageQuality));
//            imageView.setImageBitmap((new AndroidImage(CameraHelper.previewImage(originalData, originalOrientation, 
//            		originalWidth, originalHeight, imageQuality))).getImage());
//			break;
			
		case R.id.saveBtn :
			if (!CameraHelper.StoreByteImage(aImage.getImage(), imageQuality, this)){
				Toast t = Toast.makeText(this, "Unable to save image - please ensure you have enough room on your SD card", Toast.LENGTH_SHORT);
				t.show();
			}else{
				Toast t = Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT);
				t.show();
			}
			break;
			
		}
	}

		
	/**
	 * Method creates the required FX menu items and configures the on click handlers
	 */
	private void createFxMenu() {
		outlineAction.setTitle("Outline FX");
		outlineAction.setIcon(getResources().getDrawable(R.drawable.photo_outline));
		outlineAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EdgeFilter filter = new EdgeFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		invertAction.setTitle("Invert FX");
		invertAction.setIcon(getResources().getDrawable(R.drawable.photo_invert));
		invertAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InvertFilter filter = new InvertFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		tvAction.setTitle("TV FX");
		tvAction.setIcon(getResources().getDrawable(R.drawable.photo_tv));
		tvAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MonitorFilter filter = new MonitorFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		bwAction.setTitle("B&W FX");
		bwAction.setIcon(getResources().getDrawable(R.drawable.photo_bw));
		bwAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BlackWhiteFilter filter = new BlackWhiteFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		pixelateAction.setTitle("Pixelate FX");
		pixelateAction.setIcon(getResources().getDrawable(R.drawable.photo_pixel));
		pixelateAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PixelateFilter filter = new PixelateFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		bbAction.setTitle("Newspaper Print FX");
		bbAction.setIcon(getResources().getDrawable(R.drawable.photo_newspaper));
		bbAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BigBrotherFilter filter = new BigBrotherFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
		
		neonAction.setTitle("Neon FX");
		neonAction.setIcon(getResources().getDrawable(R.drawable.photo_invert));
		neonAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NeonFilter filter = new NeonFilter();
				aImage = filter.process(aImage);
	            imageView.setImageBitmap(aImage.getImage());
			}
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
	        originalData = null;
	        aImage.setColourArray(null);
	        aImage.setImage(null);
	        aImage = null;
	    	finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}