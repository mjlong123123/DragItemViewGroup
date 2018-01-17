package com.dragon.scrollitemviewgroup;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/17.
 */

public interface PositionHelper {

    int checkBoundary(ViewGroup parent, View menuView, float offsetX);

    void moveChild(View mainView, View menuView, int offsetX);

    int computeDx(ViewGroup parentView, View menuView, int direct);

    int getCurrentPosition(View view);
}
