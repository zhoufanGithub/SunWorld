package com.steven.baselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.steven.baselibrary.R;
import com.steven.baselibrary.util.DimensUtil;

/**
 * author: zhoufan
 * data: 2021/7/9 16:38
 * content: 自定义View实现步数计数器的效果
 * <p>
 * <p>
 * <com.steven.baselibrary.view.StepView
 * android:id="@+id/step_view"
 * android:layout_width="200dp"
 * android:layout_height="200dp"
 * app:step_view_fix_progress_color="@android:color/darker_gray"
 * app:step_view_current_progress_color="@android:color/holo_orange_dark"
 * app:step_view_progress_size="5dp"
 * app:step_view_text_color="@android:color/holo_orange_light"
 * app:step_view_text_size="20sp"
 * app:layout_constraintEnd_toEndOf="parent"
 * app:layout_constraintStart_toStartOf="parent"
 * app:layout_constraintTop_toBottomOf="@+id/materialDesigner" />
 * <p>
 * private fun startStep(){
 * step_view.setMaxStep(4000)
 * val valueAnimation = ObjectAnimator.ofInt(0,3000)
 * valueAnimation.duration = 1000
 * valueAnimation.addUpdateListener {
 * val value:Int = it.animatedValue as Int
 * step_view.setCurrentStep(value)
 * }
 * valueAnimation.start()
 * }
 */
public class StepView extends View {

    // 总步数对应的颜色
    private int mFixedProgressColor = Color.GRAY;

    // 当前步数对应的颜色
    private int mCurrentProgressColor = Color.YELLOW;

    // 进度条对应的大小
    private float mProgressSize = 5;

    // 字体对应的颜色
    private int mTextColor = Color.YELLOW;

    // 字体对应的大小(像素)
    private float mTextSize = 15;

    // 画总步数的画笔
    private Paint mFixProgressPaint;

    // 画变动步数的画笔
    private Paint mCurrentProgressPaint;

    // 画文字的画笔
    private Paint mTextPaint;

    // 设置最大的步数
    private int mMaxStep;

    // 设置当前的步数
    private int mCurrentStep;


    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 先理清一下思路，再来进行编码
        // 整个功能分为3个部分，底部的总步数条（固定）、加载的步数条（变动）、对应显示的文字步数
        // 1.获取自定义属性
        // <1> 底部的总步数条颜色
        // <2> 加载的步数条颜色
        // <3> 步数条宽度
        // <4> 显示的文字步数(字体大小、颜色)
        // 2.通过draw()方法绘制静态效果
        // 3.通过属性动画来实现动态加载

        // 1.获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mFixedProgressColor = array.getColor(R.styleable.StepView_step_view_fix_progress_color, mFixedProgressColor);
        mCurrentProgressColor = array.getColor(R.styleable.StepView_step_view_current_progress_color, mCurrentProgressColor);
        mProgressSize = array.getDimensionPixelSize(R.styleable.StepView_step_view_progress_size, (int) DimensUtil.dp2px(mProgressSize));
        mTextColor = array.getColor(R.styleable.StepView_step_view_text_color, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.StepView_step_view_text_size, (int) DimensUtil.sp2px(mTextSize));
        array.recycle();

        // 设置画笔
        mFixProgressPaint = initPaint(mFixedProgressColor);
        mCurrentProgressPaint = initPaint(mCurrentProgressColor);

        mTextPaint = new Paint();
        // 抗锯齿
        mTextPaint.setAntiAlias(true);
        // 防抖动
        mTextPaint.setDither(true);
        // 设置画笔的颜色
        mTextPaint.setColor(mTextColor);
        // 设置画笔的大小
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    private Paint initPaint(int color) {
        Paint paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        // 防抖动
        paint.setDither(true);
        // 设置画笔的颜色
        paint.setColor(color);
        // 设置画笔的大小
        paint.setStrokeWidth(mProgressSize);
        // 设置画笔的样式
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔的端的样式
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1.画固定的圆弧
        RectF rectF = new RectF(mProgressSize / 2, mProgressSize / 2, getWidth() - mProgressSize / 2, getHeight() - mProgressSize / 2);
        canvas.drawArc(rectF, 135, 270, false, mFixProgressPaint);
        // 2.画非固定圆弧
        if (mCurrentStep < 1) {
            return;
        }
        canvas.drawArc(rectF, 135, (float) mCurrentStep / mMaxStep * 270, false, mCurrentProgressPaint);
        // 3.画文字
        String value = String.valueOf(mCurrentStep);
        // 3.1计算文字的宽度和高度
        Rect rect = new Rect();
        mTextPaint.getTextBounds(value, 0, value.length(), rect);
        float x = getWidth() / 2 - rect.width() / 2;
        // 计算基线
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = getHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(value, x, y, mTextPaint);
    }

    /**
     * 设置最大步数值
     *
     * @param maxStep 最大步数值
     */
    public synchronized void setMaxStep(int maxStep) {
        if (maxStep < 0) {
            throw new IllegalArgumentException("步数最大值不允许小于0");
        }
        this.mMaxStep = maxStep;
    }

    /**
     * 设置当前步数值
     *
     * @param currentStep 当前步数值
     */
    public synchronized void setCurrentStep(int currentStep) {
        if (currentStep < 0) {
            throw new IllegalArgumentException("当前步数值不允许小于0");
        }
        if (currentStep > mMaxStep) {
            throw new IllegalArgumentException("当前步数值不允许大于最大值");
        }
        this.mCurrentStep = currentStep;
        invalidate();
    }
}
