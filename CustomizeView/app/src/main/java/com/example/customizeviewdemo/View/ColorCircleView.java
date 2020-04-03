package com.example.customizeviewdemo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.customizeviewdemo.R;


public class ColorCircleView extends View {


    private int circleD;
    private int mColor;
    private Paint mPaint = new Paint();

    public ColorCircleView(Context context) {
        super(context);
        Log.i("mtpeng", "ColorCircleView new");
    }

    public ColorCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorCircleView);
        mColor = a.getColor(R.styleable.ColorCircleView_circle_color, getResources().getColor(R.color.colorPrimary));
        a.recycle();
        Log.i("mtpeng", "ColorCircleView new has Attrs");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getPaintSize(widthMeasureSpec);
        int height = getPaintSize(heightMeasureSpec);
        circleD = Math.min(width, height);
        setMeasuredDimension(circleD, circleD);
        Log.i("mtpeng", "ColorCircleView onMeasure");
    }

    private int getPaintSize(int measureSpec) {
        int mSize = 320;//圆的默认直径
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED://没有指定大小
                break;
            case MeasureSpec.AT_MOST://在尺寸范围内任意取值 wrap_content模式
                mSize = Math.min(size, mSize);
                break;
            case MeasureSpec.EXACTLY://固定尺寸   match_parent 或给定大小
                mSize = size;
                break;
        }
        return mSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(9);
        mPaint.setColor(getResources().getColor(R.color.deepRed));
        float[] ps = {0, 0, circleD, 0,
                0, circleD, circleD, circleD,
                0, 0, 0, circleD,
                circleD, 0, circleD, circleD};
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawLines(ps, mPaint);

        float lines = mPaint.getStrokeMiter();

//        canvas.drawRou

        float r = (circleD - lines) / 2;
        float paintX = r + lines / 2;
        float paintY = r + lines / 2;
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        canvas.drawCircle(paintX, paintY, r, mPaint);

        Log.i("mtpeng", "ColorCircleView onDraw");
    }
}
