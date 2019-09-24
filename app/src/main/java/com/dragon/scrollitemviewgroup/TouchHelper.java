package com.dragon.scrollitemviewgroup;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by dragon
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
            if (!scroller.isFinished()) {
                computeScroll();
            }
        }
    };

    PositionHelper positionHelper;

    public TouchHelper(ViewGroup view) {
        this.view = view;
        scroller = new Scroller(view.getContext());
        touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        positionHelper = new PositionHelper2();
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
            case MotionEvent.ACTION_MOVE: {
                float currentX = event.getX();
                float offsetX = currentX - lastX;
                lastX = currentX;
                View mainView = view.getChildAt(0);
                View menuView = view.getChildAt(1);
                offsetX = positionHelper.checkBoundary(view, menuView, offsetX);
                positionHelper.moveChild(mainView, menuView, (int) offsetX);
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                float currentX = event.getX();
                direct = (int) (currentX - downX);
                smoothScroll(direct);
            }
            break;
        }
        return true;
    }

    public void smoothScroll(int direct) {
        View menuView = view.getChildAt(1);
        int dx = positionHelper.computeDx(view, menuView, direct);
        if (dx == 0) {
            return;
        }
        scroller.startScroll(positionHelper.getCurrentPosition(menuView), 0, dx, 0);
        handler.post(runnable);
    }

    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int x = scroller.getCurrX();
            View mainView = view.getChildAt(0);
            View menuView = view.getChildAt(1);
            int offset = x - positionHelper.getCurrentPosition(menuView);
            positionHelper.moveChild(mainView, menuView, offset);
            handler.post(runnable);
        }
    }
}
