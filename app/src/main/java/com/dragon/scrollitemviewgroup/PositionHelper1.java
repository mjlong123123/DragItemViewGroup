package com.dragon.scrollitemviewgroup;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/17.
 */

public class PositionHelper1 implements PositionHelper {

    public int checkBoundary(ViewGroup parent, View menuView, float offsetX) {
        if (offsetX + menuView.getLeft() > parent.getRight()) {
            offsetX = parent.getRight() - menuView.getLeft();
        } else if (offsetX + menuView.getRight() < parent.getRight()) {
            offsetX = parent.getRight() - menuView.getRight();
        }
        return (int) offsetX;
    }

    public void moveChild(View mainView, View menuView, int offsetX){
        mainView.offsetLeftAndRight(offsetX);
        menuView.offsetLeftAndRight(offsetX);
    }

    public int computeDx(ViewGroup parentView, View menuView, int direct){
        int dx = direct > 0 ? parentView.getRight() - menuView.getLeft() : parentView.getRight() - menuView.getRight();
        return dx;
    }

    public int getCurrentPosition(View view){
        return view.getLeft();
    }
}
