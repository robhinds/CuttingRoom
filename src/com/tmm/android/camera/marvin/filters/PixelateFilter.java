package com.tmm.android.camera.marvin.filters;

import com.tmm.android.camera.marvin.image.AndroidImage;

public class PixelateFilter implements IAndroidFilter {

	@Override
	public AndroidImage process(AndroidImage imageIn) {
		int l_rgb;
		
		int squareSize = 16;
			
		for (int x = 0; x < imageIn.getWidth(); x+=squareSize) {
			for (int y = 0; y < imageIn.getHeight(); y+=squareSize) {				
				l_rgb = getPredominantRGB(imageIn, x,y,squareSize);
				fillRect(imageIn, x,y,squareSize, l_rgb);					
			}
		}
		
		return imageIn;
	}
	
	
	
	/**
	 * Method gets the predominant colour pixels to extrapolate
	 * the pixelation from
	 * 
	 * @param a_image
	 * @param a_x
	 * @param a_y
	 * @param squareSize
	 * @return
	 */
	private int getPredominantRGB(AndroidImage a_image, int a_x, int a_y, int squareSize){
		int l_red=-1;
		int l_green=-1;
		int l_blue=-1;
		
		for(int x=a_x; x<a_x+squareSize; x++){
			for(int y=a_y; y<a_y+squareSize; y++){
				if(x < a_image.getWidth() && y < a_image.getHeight()){
					
					if(l_red == -1)		l_red = a_image.getRComponent(x,y);
					else				l_red = (l_red+a_image.getRComponent(x,y))/2;
					if(l_green == -1)	l_green = a_image.getGComponent(x,y);
					else				l_green = (l_green+a_image.getGComponent(x,y))/2;
					if(l_blue == -1)	l_blue = a_image.getBComponent(x,y);
					else				l_blue = (l_blue+a_image.getBComponent(x,y))/2;	
				}				
			} 
		}
		return (255<<24)+(l_red<<16)+(l_green<<8)+l_blue;
	}
	
	/**
	 * Method to extrapolate out
	 * 
	 * @param a_image
	 * @param a_x
	 * @param a_y
	 * @param squareSize
	 * @param a_rgb
	 */
	private void fillRect(AndroidImage a_image, int a_x, int a_y, int squareSize, int a_rgb){
		for(int x=a_x; x<a_x+squareSize; x++){
			for(int y=a_y; y<a_y+squareSize; y++){
				if(x < a_image.getWidth() && y < a_image.getHeight()){
					a_image.setPixelColour(x,y,a_rgb);
				}
			}
		}					
	}

}
