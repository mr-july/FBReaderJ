/*
 * Copyright (C) 2007-2010 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.ui.android.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.geometerplus.zlibrary.core.image.ZLImageData;

public final class ZLAndroidImageData implements ZLImageData {
	private byte[] myArray;
	private Bitmap myBitmap;
	private int myRealWidth;
	private int myRealHeight;
	private int myLastRequestedWidth;
	private int myLastRequestedHeight;

	ZLAndroidImageData(byte[] array) {
		myArray = array;
	}

	public synchronized Bitmap getBitmap(int maxWidth, int maxHeight) {
		if ((maxWidth == 0) || (maxHeight == 0)) {
			return null;
		}
		if ((maxWidth != myLastRequestedWidth) || (maxHeight != myLastRequestedHeight)) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
			}
			try {
				final BitmapFactory.Options options = new BitmapFactory.Options();
				if (myRealWidth <= 0) {
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeByteArray(myArray, 0, myArray.length, options);
					myRealWidth = options.outWidth;
					myRealHeight = options.outHeight;
				}
				options.inJustDecodeBounds = false;
				options.inScaled = true;
				float scaleCoeff = Math.min(((float)maxWidth) / ((float)myRealWidth),
						((float)maxHeight) / ((float)myRealHeight));
				int scaledWidth = myRealWidth;
				int scaledHeight = myRealHeight;
				if (scaleCoeff < 1.0)
				{
					scaledWidth =  (int)(Math.round(scaleCoeff * ((float)myRealWidth)));
					scaledHeight =  (int)(Math.round(scaleCoeff * ((float)myRealHeight)));
				}
				// image will be prescaled while decoding to save memory
				options.inSampleSize = (int)(1.0f / scaleCoeff);
				myBitmap = Bitmap.createScaledBitmap(
						BitmapFactory.decodeByteArray(myArray, 0, myArray.length, options),
						scaledWidth, scaledHeight, true);
				if (myBitmap != null) {
					myLastRequestedWidth = maxWidth;
					myLastRequestedHeight = maxHeight;
				}
			} catch (OutOfMemoryError e) {
			}
		}
		return myBitmap;
	}
}
