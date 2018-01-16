package com.dragon.scrollitemviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chenjiulong
 */

public class DragItemViewGroup extends ViewGroup {
    private TouchHelper touchHelper;

    public DragItemViewGroup(Context context) {
        super(context);
        init();
    }

    public DragItemViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        touchHelper = new TouchHelper(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() != 2) {
            throw new RuntimeException("child count error!");
        }

        View childView = getChildAt(0);
        measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        childView = getChildAt(1);
        measureChild(childView, widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() != 2) {
            throw new RuntimeException("child count error!");
        }
        View childView = getChildAt(0);
        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
        childView = getChildAt(1);
        childView.layout(getRight(), 0, getRight() + childView.getMeasuredWidth(), childView.getMeasuredHeight());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (touchHelper.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchHelper.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
//
//    @Override
//    public void computeScroll() {
//        touchHelper.computeScroll();
//    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
