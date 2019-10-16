package com.utte.growon.mono;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;

public class MonoGridView extends GridView {
    public MonoGridView(Context context) {
        super(context);
    }

    private static final String TAG = "MonoGridView";
    public MonoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "onMeasure: " + MeasureSpec.getMode(widthMeasureSpec) + "***************" + MeasureSpec.getMode(heightMeasureSpec));

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
