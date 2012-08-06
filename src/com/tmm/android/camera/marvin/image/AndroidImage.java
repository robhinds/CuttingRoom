package com.tmm.android.camera.marvin.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class AndroidImage {
	
	//original bitmap image
	private Bitmap image;
	
	//format of image (jpg/png)
	private String formatName;
	
	//dimensions of image
	private int width, height;
	
	// RGB Array Color
	protected int[] colourArray;
	
	public AndroidImage(Bitmap img){		
		this.image =  img;
		formatName = "jpg";
		width = img.getWidth();
		height = img.getHeight();
		updateColourArray();
	}
	
	
	/**
	 * Method to reset the image to a solid colour
	 * 
	 * @param color - colour to rest the entire image to
	 */
	public void clearImage(int color){
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				image.setPixel(x, y, color);
			}
		}
	}
	
	
	/**
	 * Set colour array for image - called on initialisation
	 * by constructor
	 * 
	 * @param bitmap
	 */
	private void updateColourArray(){
		colourArray = new int[width * height];
		image.getPixels(colourArray, 0, width, 0, 0, width, height);
		int r, g, b;
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int index = y * width + x;
				r = (colourArray[index] >> 16) & 0xff;
				g = (colourArray[index] >> 8) & 0xff;
				b = colourArray[index] & 0xff;
				colourArray[index] = 0xff000000 | (r << 16) | (g << 8) | b;
			}
		}
	}
	
	
	/**
	 * Method to set the colour of a specific pixel
	 * 
	 * @param x
	 * @param y
	 * @param colour
	 */
	public void setPixelColour(int x, int y, int colour){
		colourArray[((y*image.getWidth()+x))] = colour;
		image.setPixel(x, y, colour);
	}
	
	/**
	 * Get the colour for a specified pixel
	 * 
	 * @param x
	 * @param y
	 * @return colour
	 */
	public int getPixelColour(int x, int y){
		return colourArray[y*width+x];
	}
	
	/**
	 * Set the colour of a specified pixel from an RGB combo
	 * 
	 * @param x
	 * @param y
	 * @param c0
	 * @param c1
	 * @param c2
	 */
	public void setPixelColour(int x, int y, int c0, int c1, int c2){
		colourArray[((y*image.getWidth()+x))] = (255 << 24) + (c0 << 16) + (c1 << 8) + c2;
		image.setPixel(x, y, colourArray[((y*image.getWidth()+x))]);
	}
	
	/**
	 * Method to get the RED colour for the specified 
	 * pixel 
	 * @param x
	 * @param y
	 * @return colour of R
	 */
	public int getRComponent(int x, int y){
		return (getColourArray()[((y*width+x))]& 0x00FF0000) >>> 16;
	}

	
	/**
	 * Method to get the GREEN colour for the specified 
	 * pixel 
	 * @param x
	 * @param y
	 * @return colour of G
	 */
	public int getGComponent(int x, int y){
		return (getColourArray()[((y*width+x))]& 0x0000FF00) >>> 8;
	}


	/**
	 * Method to get the BLUE colour for the specified 
	 * pixel 
	 * @param x
	 * @param y
	 * @return colour of B
	 */
	public int getBComponent(int x, int y){
		return (getColourArray()[((y*width+x))] & 0x000000FF);
	}

	
	
	/**
	 * Method to rotate an image by the specified number of degrees
	 * 
	 * @param rotateDegrees
	 */
	public void rotate (int rotateDegrees){
        Matrix mtx = new Matrix();
        mtx.postRotate(rotateDegrees);
        image = Bitmap.createBitmap(image, 0, 0, width, height, mtx, true);
        width = image.getWidth();
        height = image.getHeight();
        updateColourArray();
	}
	

	/**
	 * @return the image
	 */
	public Bitmap getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}


	/**
	 * @return the formatName
	 */
	public String getFormatName() {
		return formatName;
	}


	/**
	 * @param formatName the formatName to set
	 */
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}


	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}


	/**
	 * @return the colourArray
	 */
	public int[] getColourArray() {
		return colourArray;
	}


	/**
	 * @param colourArray the colourArray to set
	 */
	public void setColourArray(int[] colourArray) {
		this.colourArray = colourArray;
	}

}
