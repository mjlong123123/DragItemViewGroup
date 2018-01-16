package com.dragon.scrollitemviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chenjiulong
 */

public class DragItemViewGroup extends ViewGroup {
	public DragItemViewGroup(Context context) {
		super(context);
	}

	public DragItemViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() != 2) {
			throw new RuntimeException("child count error!");
		}
		View childView = getChildAt(0);
		childView.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
		childView = getChildAt(1);
		childView.layout(getRight(), getPaddingTop(), getRight() + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
	}
}
