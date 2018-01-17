package com.dragon.scrollitemviewgroup;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/17.
 */

public class PositionHelper2 implements PositionHelper {

    public int checkBoundary(ViewGroup parent, View menuView, float offsetX) {
        int max = menuView.getMeasuredWidth();
        int min = 0;
        int scroll = parent.getScrollX();
        if (scroll - offsetX < min) {
            offsetX = -(min - scroll);
        } else if (scroll - offsetX > max) {
            offsetX = -(max - scroll);
        }
        return (int) offsetX;
    }

    public void moveChild(View mainView, View menuView, int offsetX){
        ViewGroup parent = (ViewGroup)mainView.getParent();
        parent.scrollBy(-offsetX,0);
//        mainView.scrollBy(-offsetX,0);
//        menuView.scrollBy(-offsetX, 0);
    }

    public int computeDx(ViewGroup parentView, View menuView, int direct){
        int max = menuView.getMeasuredWidth();
        int scroll = parentView.getScrollX();
        int dx = -direct > 0 ? max - scroll: -scroll;
        return -dx;
    }

    public int getCurrentPosition(View view){
        ViewGroup parent = (ViewGroup)view.getParent();
        return -parent.getScrollX();
    }
}
