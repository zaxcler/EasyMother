package com.example.demobyimage;

import android.app.Service;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;

public class DensityUtil {
	// private static final int desing_heightPixels = 960;
	public static final int design_widthPixels = 540;// The pixels in width of
														// the design device.
	public static final int design_densityDpi = 240;// The density of the design
													// device.
	public static final int design_iconDpi = 432;// The density of icon for game
	private float font_scaleFactor;// The adjusted factor of font.
	private DisplayMetrics displayMetrics;

	public DensityUtil(Context context) {
		this.displayMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Service.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(displayMetrics);
		int width = Math.min(displayMetrics.widthPixels,
				displayMetrics.heightPixels);
		this.font_scaleFactor = (float) width * design_densityDpi
				/ (design_widthPixels * displayMetrics.densityDpi);
	}

	/**
	 * Adjust the font size.
	 * 
	 * @param view
	 *            - The TextView being adjusted.
	 */
	public void adjustFontSize(TextView view) {
		view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.getTextSize()
				* this.font_scaleFactor);
	}

	/**
	 * Get the max icon size of shortcut for this phone.
	 * 
	 * @return The max icon size.
	 */
	public int getMaxIconSize() {
		return displayMetrics.densityDpi * 48 / 160;
	}

	public DisplayMetrics getDisplayMetrics() {
		return displayMetrics;
	}

	public int convertDipsToPixels(float pDIPs) {
		return Math.round(pDIPs * displayMetrics.density);
	}
}
