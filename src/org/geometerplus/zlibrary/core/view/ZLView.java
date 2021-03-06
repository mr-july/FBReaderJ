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

package org.geometerplus.zlibrary.core.view;

abstract public class ZLView {
	public final ZLPaintContext Context;

	public static final int MODE_READ = 0;
	public static final int MODE_SELECT = 1;
	protected int myMode = MODE_READ;

	public ZLView(ZLPaintContext context) {
		Context = context;
	}

	public static final int PAGE_CENTRAL = 0;
	public static final int PAGE_LEFT = 1;
	public static final int PAGE_RIGHT = 2;
	public static final int PAGE_TOP = 3;
	public static final int PAGE_BOTTOM = 4;

	public static final int SCROLLBAR_HIDE = 0;
	public static final int SCROLLBAR_SHOW = 1;
	public static final int SCROLLBAR_SHOW_AS_PROGRESS = 2;
	public static final int SCROLLBAR_SHOW_AS_FOOTER = 3;

	abstract public void paint(int viewPage);
	abstract public void onScrollingFinished(int viewPage);

	public boolean onStylusPress(int x, int y) {
		return false;
	}

	public boolean onStylusRelease(int x, int y) {
		return false;
	}

	public boolean onStylusMove(int x, int y) {
		return false;
	}

	public boolean onStylusMovePressed(int x, int y) {
		return false;
	}

	public void setMode(int mode) {
		myMode = mode;
	}

	public abstract boolean showScrollbar();
	public abstract int scrollbarType();
	public abstract int getScrollbarFullSize();
	public abstract int getScrollbarThumbPosition(int viewPage);
	public abstract int getScrollbarThumbLength(int viewPage);
	public abstract float getProgress(float offset);
	public abstract void  setProgress(float progress);
}
