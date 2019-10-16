package com.utte.growon.mono;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProgressView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;
    private int progress;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.CYAN);
    }

    private static final String TAG = "ProgressView";
    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        mWidth = w;
        mHeight = h;
        Log.d(TAG, "onSizeChanged: " + w + "........." + h);
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth * progress / 100, mHeight, mPaint);
        super.onDraw(canvas);
    }

    /**
     * 设置新进度 重新绘制
     */
    public void setProgress(int newProgress) {
        this.progress = newProgress;
        invalidate();
    }

    /**
     * 设置进度条颜色 * * @param color 色值
     */
    public void setColor(int color) {
        mPaint.setColor(color);
    }

}