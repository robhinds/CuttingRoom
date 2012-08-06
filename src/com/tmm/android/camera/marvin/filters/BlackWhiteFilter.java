package com.tmm.android.camera.marvin.filters;

import com.tmm.android.camera.marvin.image.AndroidImage;

/**
 * @author robert.hinds
 *
 * Filter class implementing a Grayscale conversion - used as a core aspect of several
 * other filters
 *
 */
public class BlackWhiteFilter implements IAndroidFilter {

	@Override
	public AndroidImage process(AndroidImage imageIn) {
		int r,g,b,corfinal;
		for (int x = 0; x < imageIn.getWidth(); x++) {
			for (int y = 0; y < imageIn.getHeight(); y++) {
				r = imageIn.getRComponent(x, y);
				g = imageIn.getGComponent(x, y);
				b = imageIn.getBComponent(x, y);
				corfinal = (int)((r*0.3)+(b*0.59)+(g*0.11));
				imageIn.setPixelColour(x,y,corfinal,corfinal,corfinal);
			}
		}
		return imageIn;
	}

}
