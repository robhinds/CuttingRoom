package com.tmm.android.camera.marvin.filters;

import com.tmm.android.camera.marvin.image.AndroidImage;

public class InvertFilter implements IAndroidFilter{

	@Override
	public AndroidImage process(AndroidImage imageIn) {
		int r, g, b;
		for (int x = 0; x < imageIn.getWidth(); x++) {
			for (int y = 0; y < imageIn.getHeight(); y++) {
				r = (255-(int)imageIn.getRComponent(x, y));
				g = (255-(int)imageIn.getGComponent(x, y));
				b = (255-(int)imageIn.getBComponent(x, y));

				imageIn.setPixelColour(x,y,r,g,b);
			}
		}
		
		return imageIn;
	}

}
