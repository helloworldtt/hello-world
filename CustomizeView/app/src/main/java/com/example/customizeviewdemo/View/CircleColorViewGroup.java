package com.example.customizeviewdemo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CircleColorViewGroup extends ViewGroup {
    private int childCount;
    private int maxWidth;
    private int maxHeight;

    public CircleColorViewGroup(Context context) {
        super(context);
    }

    public CircleColorViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //该方法参数为组件容器位于整个页面的位置坐标
        //内部方法是子View 相对于组件的坐标，即重新以容器左上角坐标为原点
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (i - 1 >= 0) {
                View pre_child = getChildAt(i - 1);
                int gap = (pre_child.getMeasuredWidth() - width) / 2;
                int gap2 = (pre_child.getMeasuredHeight() - height) / 2;
                l = l + gap;
                t = t + gap2;
            } else {
                t = 0;
            }
            child.layout(l, t, l + width, t + height);
        }
    }

    // intention: colorCircle_Frame
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子view和决定自己的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量自己的：由子view中判断
        childCount = getChildCount();
        if (childCount == 0) { //不包含子view,即空
            setMeasuredDimension(0, 0);
        }

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {//仅当wrap+content 才没确定数据
            int childMaxW = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int width = child.getMeasuredWidth();
                childMaxW = Math.max(width, childMaxW);
            }
            maxWidth = childMaxW;
        }

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (hMode == MeasureSpec.AT_MOST) {
            int childMaxH = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int width = child.getMeasuredHeight();
                childMaxH = Math.max(width, childMaxH);
            }
            maxHeight = childMaxH;
        }
        setMeasuredDimension(maxWidth, maxHeight);
    }
}
