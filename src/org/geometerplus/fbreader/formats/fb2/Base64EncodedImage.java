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

package org.geometerplus.fbreader.formats.fb2;

import java.io.*;

import org.geometerplus.zlibrary.core.image.ZLBase64EncodedImage;

import org.geometerplus.fbreader.Paths;

final class Base64EncodedImage extends ZLBase64EncodedImage {
	private final String BASE64_FILE_EXTENSION = "b64";
	
	/**
	 *  format for base64 encoded file name construction
	 *  1-st param - directory
	 *  2-nd param - number
	 *  3-d param - file extension
	 */
	private final String FORMAT_ENCODED = "%s/image_%03x.%s";
	
	/**
	 *  format for decoded file name construction
	 *  1-st param - directory
	 *  2-nd param - number
	 *  3-d param - file extension
	 */
	private final String FORMAT_DECODED = "%s/dimage_%03x.%s";
	
	private static int ourCounter;

	static void resetCounter() {
		ourCounter = 0;
	}

	private final String myDirName;
	private final int myFileNumber;
	private OutputStreamWriter myStreamWriter;
	
	public Base64EncodedImage(String contentType) {
		// TODO: use contentType
		super(contentType);
		myDirName = Paths.cacheDirectory();
		new File(myDirName).mkdirs();
		myFileNumber = ourCounter++;
		try {
			myStreamWriter = new OutputStreamWriter(new FileOutputStream(encodedFileName()), "UTF-8");
		} catch (IOException e) {
		}
	}

	@Override
	protected String encodedFileName() {
		return String.format(FORMAT_ENCODED, myDirName, myFileNumber, BASE64_FILE_EXTENSION);
	}

	@Override
	protected String decodedFileName() {
		return String.format(FORMAT_DECODED, myDirName, myFileNumber, getFileExtension());
	}

	void addData(char[] data, int offset, int length) {
		if (myStreamWriter != null) {
			try {
				myStreamWriter.write(data, offset, length);
			} catch (IOException e) {
			}
		}
	}

	void close() {
		try {
			myStreamWriter.close();
		} catch (IOException e) {
		}
	}
	
	
	private String getFileExtension() {
		String fileExtension = "jpg";
		
		String tmpMime = mimeType().toLowerCase();
		if (tmpMime.contains("png"))
		{
			fileExtension = "png";
		} else if (tmpMime.contains("gif"))
		{
			fileExtension = "gif";
		} else if (tmpMime.contains("bmp") || tmpMime.contains("bitmap"))
		{
			fileExtension = "bmp";
		}
		
		return fileExtension;
	}
}
