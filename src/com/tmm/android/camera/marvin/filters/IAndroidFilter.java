package com.tmm.android.camera.marvin.filters;

import com.tmm.android.camera.marvin.image.AndroidImage;

public interface IAndroidFilter {

	public AndroidImage process(AndroidImage imageIn);
}
