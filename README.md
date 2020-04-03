# hello-world
just begin

自定义View
测量 说明整个view 大小  onMeasure
根据view构造时传入的layout_width,layout_height,获取初始构造时的数据情况
在测量模式的限制下，在范围内,自定义设定view的size  --setMeasuredDimension
代码中，我们直接获取得到测量模式，和系统测量的初始数据，进行分情况取我们所需。

 int mode = MeasureSpec.getMode(measureSpec);
 int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED://没有指定大小，直接取自己设定值
                break;
            case MeasureSpec.AT_MOST://在尺寸范围内任意取值 wrap_content模式
                mSize = Math.min(size, mSize);
                break;
            case MeasureSpec.EXACTLY://固定尺寸   match_parent 或给定大小
                mSize = size;
                break;
        }
        
而在android-develop中 https://developer.android.com/training/custom-views/custom-drawing#layouteevent 给出的实例中，
举例我们使用辅助方法 resolveSizeAndState()，获取合适的size.
       // Try for a width based on our minimum
       int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
       int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

       // Whatever the width ends up being, ask for a height that would let the pie
       // get as big as it can
       int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
       int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

       setMeasuredDimension(w, h);
       
实际 resolveSizeAndState(int size  ,MeasureSpec spec ,int information)
size : 通过padding、miniWidth或我们自定义大小，表示当前view想要的大小
spec : (width/Height)MeasureSpec
information : about view's children state,the example use 0/1 ，we pass it temporarily

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
        
可见，resolveSizeAndState 实际只是在内部对比size和测量得到的size作对比，实现取合适的尺寸。
使用举例中方法例子，将尺寸的设定更加依赖于构造时对View的属性值设定。
而我们自己进行模式对比时，更偏向于意愿固定此View的固定尺寸。

ps:
构造View时，通过属性padding设置，实现View绘画时，
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
