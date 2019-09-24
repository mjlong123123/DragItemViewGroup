package com.dragon.scrollitemviewgroup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;

/**
 * @author dragon
 */

public class DragItemViewGroup extends ViewGroup {
    private TouchHelper touchHelper;
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_DRAGGING) {
                touchHelper.smoothScroll(1);
            }
        }
    };

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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) getParent();
            recyclerView.addOnScrollListener(onScrollListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getParent() instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) getParent();
            recyclerView.removeOnScrollListener(onScrollListener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() != 2) {
            throw new RuntimeException("child count error!You can only add two child view");
        }
        View childView = getChildAt(0);
        measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        childView = getChildAt(1);
        measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), childView.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() != 2) {
            throw new RuntimeException("child count error!You only can add two child view");
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

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
