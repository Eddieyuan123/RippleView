package com.example.eddie.rippleview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 2016/2/29.
 */
public class RippleView extends View {

    private Paint mPaint;
    private int mMaxWidth = 255;
    private List<String> alphaList = new ArrayList<>();
    private List<String> startWidthList = new ArrayList<>();

    private boolean isStarting;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        alphaList.add("255");
        startWidthList.add("0");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.TRANSPARENT);
        for (int i = 0; i < alphaList.size(); i++) {
            int alpha = Integer.parseInt(alphaList.get(i));
            int startWidth = Integer.parseInt(startWidthList.get(i));
            mPaint.setAlpha(alpha);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, startWidth + 50, mPaint);
            if (isStarting && alpha > 0 && startWidth < mMaxWidth) {
                alphaList.set(i, (alpha - 1) + "");
                startWidthList.set(i, (startWidth + 1) + "");
            }
        }
        if (isStarting && Integer.parseInt(startWidthList.get(startWidthList.size() - 1)) == mMaxWidth / 5) {
            alphaList.add("255");
            startWidthList.add("0");
        }
        if (isStarting && startWidthList.size() == 10) {
            startWidthList.remove(0);
            alphaList.remove(0);
        }
        invalidate();
    }

    public void start() {
        isStarting = true;
    }

    public void stop() {
        isStarting = false;
    }

    public boolean isStarting() {
        return isStarting;
    }
}
