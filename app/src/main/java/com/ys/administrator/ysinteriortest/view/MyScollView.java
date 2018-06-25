package com.ys.administrator.ysinteriortest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by amos on 2016/5/24.
 */
public class MyScollView extends ScrollView {

    public MyScollView(Context context) {
        super(context);
    }

    public MyScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/4);
    }
}
