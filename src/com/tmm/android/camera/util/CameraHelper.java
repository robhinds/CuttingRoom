package com.tmm.android.camera.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.view.Surface;

public class CameraHelper {

	/**
	 * method to build a Bitmap to be previewed/Fx'd after capture
	 * 
	 * @param imageData
	 * @param orientation
	 * @param width
	 * @param height
	 * @param imageQuality
	 * @return
	 */
	public static Bitmap previewImage(byte[] imageData, int orientation, int width, int height, int imageQuality) {
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = imageQuality;
		Bitmap pic = BitmapFactory.decodeByteArray(imageData, 0, imageData.length,options);
		
		int rotate = 0;
		if(orientation == Surface.ROTATION_0){
			rotate = 90;
        } else if(orientation == Surface.ROTATION_270){
        	rotate = 180;
        }
		
		if (rotate!=0){
			int w = pic.getWidth();
	        int h = pic.getHeight();
	        Matrix mtx = new Matrix();
	        mtx.postRotate(rotate);
	        pic = Bitmap.createBitmap(pic, 0, 0, w, h, mtx, true);
		}
		
		pic = pic.copy(Bitmap.Config.ARGB_8888, true);
		return pic;
	}
	
	
	/**
	 * Method to store an image on the SD card
	 * 
	 * @param imageData
	 * @param quality
	 * @return
	 */
	public static boolean StoreByteImage(Bitmap image,int quality, Context ctx) {

        File sdImageMainDirectory = Environment.getExternalStorageDirectory();
        File cuttingDirectoy = new File(sdImageMainDirectory.getPath() + "/cuttingRoom/");
        cuttingDirectoy.mkdirs();
        
		FileOutputStream fileOutputStream = null;
		String fileName = "/IMG_CUT_" + UUID.randomUUID().toString() + ".jpg";
		try {

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 1;
			
			fileOutputStream = new FileOutputStream(cuttingDirectoy.toString() +fileName);
							
			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			image.compress(CompressFormat.JPEG, 100, bos);

			bos.flush();
			bos.close();
			
			String[] paths = new String[1];
			paths[0] = cuttingDirectoy.toString()+fileName;
			//start media scanner to send pic to gallery
			MediaScannerConnection.scanFile(ctx, paths, null, null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}