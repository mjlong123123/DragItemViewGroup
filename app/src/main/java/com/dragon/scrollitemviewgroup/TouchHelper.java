package com.dragon.scrollitemviewgroup;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018/1/16.
 */

public class TouchHelper {

    float downX = 0;

    ViewGroup view;


    int touchSlop;
    boolean dragFlag;
    float lastX = 0;

    Scroller scroller;

    int direct = 0;

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e("dragon","running ---------------");
            if(!scroller.isFinished()){
                computeScroll();
            }
        }
    };

    public TouchHelper(ViewGroup view) {
        this.view = view;
        scroller = new Scroller(view.getContext());
        touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scroller.forceFinished(true);
                downX = ev.getX();
                lastX = downX;
                dragFlag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getX();
                lastX = currentX;
                float diffX = Math.abs(currentX - downX);
                if (diffX > touchSlop) {
                    dragFlag = true;
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return dragFlag;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return false;
//                break;
            case MotionEvent.ACTION_MOVE: {
                float currentX = event.getX();
                float offsetX = currentX - lastX;
                lastX = currentX;
                View childView = view.getChildAt(1);
                if (offsetX + childView.getLeft() > view.getRight()) {
                    offsetX = view.getRight() - childView.getLeft();
                } else if (offsetX + childView.getRight() < view.getRight()) {
                    offsetX = view.getRight() - childView.getRight();
                }
                childView.offsetLeftAndRight((int) offsetX);
                childView = view.getChildAt(0);
                childView.offsetLeftAndRight((int) offsetX);

            }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                float currentX = event.getX();

                direct = (int) (currentX - downX);
                View childView = view.getChildAt(1);
                int dx = direct > 0 ? view.getRight() - childView.getLeft() : view.getRight() - childView.getRight();
                scroller.startScroll(childView.getLeft(), 0, dx, 0);
                handler.post(runnable);
            }
                break;
        }
        return true;
    }
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            int x = scroller.getCurrX();
            View childView = view.getChildAt(1);
            int offset = x - childView.getLeft();
            childView.offsetLeftAndRight(offset);
            childView = view.getChildAt(0);
            childView.offsetLeftAndRight(offset);
            handler.post(runnable);
        }
    }
}
