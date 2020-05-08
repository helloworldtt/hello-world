# hello-world
just begin

自定义View

* 引入重写至少两个构造函数。简单super即可

  代码创建view时,多用view（this）;xml配置view时，实现使用View(context,atts)

* 测量 说明整个view 大小  重写onMeasure

根据view构造时传入的layout_width,layout_height,获取初始构造时的数据情况
在测量模式的限制下，在范围内,自定义设定view的size
代码中，我们直接获取得到测量模式，和系统测量的初始数据，进行分情况取我们所需。
最后setMeasuredDimension(width,heigth) 完成测量，记录数据

在onMeasure(widthSpec,HeightSpec)方法参数中，我们可以从中获取当前view在使用的测量模式和初始尺寸
/**int型数据占用32个bit，而google实现的是，将int数据的前面2个bit用于区分不同的布局模式，后面30个bit存放的是尺寸的数据。**/

       int mode = MeasureSpec.getMode(measureSpec);//获取当前测量模式
       int size = MeasureSpec.getSize(measureSpec);
       测量模式有三种：
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
构造View时，通过属性padding等设置，实现View绘画时，注意测量时把设置的padding等get数据加入测量；
getSuggestedMinimumWidth()>0，当有对view setMinimumWidth;

* 绘制 重写onDraw

屏幕坐标系，默认以左上角为原点，x轴往右为正，y轴往下为正。
对于view.getTop(),getLeft()...都是以其父容器的左上角为原点得到的数据位置，注意别误会为其在整个屏幕的坐标

*******[画笔 Paint]*******
画笔用于配置颜色，绘制效果等
常用方法有：

    Paint.setStyle()   设置画笔绘制模式
    STROKE 描边 
    FILL 填充 画笔内部区域
    FILL_AND_STROKE 描边并填充.
    描边时注意画笔宽度:由Paint.setStrokeWidth设置，画笔宽度由宽度中线左右扩展
    注意绘制时适度调整图形大小以免受画笔宽度影响效果,获取画笔宽度为mPaint.getStrokeMiter()

Paint.setAntiAlias(true)

画笔抗锯齿属性，使得view绘制圆滑。减少使用Paint.setFlags(Paint.ANTI_ALIAS_FLAG);//因为该设置会直接覆盖，不方便多条件使用

*******[画布 Canvas]*******
画板画布，用于绘制view的具体形状
常见形状都有封装，直接canvas.drawLine(线)、drawCircle(圆)、drawRect(矩形)、drawRountRect(圆角矩形)...

***ps*** 
1、 当前画布原点(0,0)为当前view所在位置的左上角，！注意非父或主屏幕的原点

2、当前页面前后台切换，画笔对象保留沿用，会重新调用onDraw，若绘制前对部分属性没有重置会导致效果差异。因此注意对画笔的设置尽量统一

3、当旋转屏幕时，view会从构造函数重新绘制
       
       
       
       
       
       
       
       
       
       
