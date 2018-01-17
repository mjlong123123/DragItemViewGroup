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
                View mainView = view.getChildAt(0);
                View menuView = view.getChildAt(1);
                offsetX = checkBoundary(menuView, offsetX);
                moveChild(mainView,menuView,(int)offsetX);
            }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                float currentX = event.getX();

                direct = (int) (currentX - downX);
                View menuView = view.getChildAt(1);
                scroller.startScroll(getCurrentPosition(menuView), 0, computeDx(view, menuView, direct), 0);
                handler.post(runnable);
            }
                break;
        }
        return true;
    }
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            int x = scroller.getCurrX();
            View mainView = view.getChildAt(0);
            View menuView = view.getChildAt(1);
            int offset = x - getCurrentPosition(menuView);
            moveChild(mainView,menuView,offset);
            handler.post(runnable);
        }
    }


    private int checkBoundary(View menuView, float offsetX) {
        if (offsetX + menuView.getLeft() > view.getRight()) {
            offsetX = view.getRight() - menuView.getLeft();
        } else if (offsetX + menuView.getRight() < view.getRight()) {
            offsetX = view.getRight() - menuView.getRight();
        }
        return (int) offsetX;
    }

    private void moveChild(View mainView, View menuView, int offsetX){
        mainView.offsetLeftAndRight(offsetX);
        menuView.offsetLeftAndRight(offsetX);
    }

    private int computeDx(ViewGroup parentView, View menuView, int direct){
        int dx = direct > 0 ? parentView.getRight() - menuView.getLeft() : parentView.getRight() - menuView.getRight();
        return dx;
    }

    private int getCurrentPosition(View view){
        return view.getLeft();
    }
}
