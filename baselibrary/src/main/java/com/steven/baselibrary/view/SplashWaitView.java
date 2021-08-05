package com.steven.baselibrary.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
 * data: 2021/7/18 16:24
 * content: 实现启动页5秒等待的效果
 *
 *  <com.steven.baselibrary.view.SplashWaitView
 *         android:id="@+id/splash_wait_view"
 *         android:layout_width="60dp"
 *         android:layout_height="80dp"
 *         app:splash_view_background_color="@color/colorAccent"
 *         app:splash_view_progress_color="@android:color/holo_orange_dark"
 *         app:splash_view_progress_size="5dp"
 *         app:splash_view_text_color="@android:color/holo_red_dark"
 *         app:splash_view_text_size="20sp"
 *         app:splash_view_wait_time="5"
 *         app:layout_constraintEnd_toEndOf="parent"
 *         app:layout_constraintStart_toStartOf="parent"
 *         app:layout_constraintTop_toBottomOf="@+id/button" />
 *
 *  splash_wait_view.startCountDown()
 */
public class SplashWaitView extends View {

    // 背景颜色
    private int mBackgroundColor = Color.GRAY;

    // 进度条的颜色
    private int mProgressColor = Color.RED;

    // 进度条宽度
    private float mProgressWidth = 5;

    // 字体颜色
    private int mTextSizeColor = Color.RED;

    // 字体大小
    private int mTextSize = 9;

    // 等待总时间
    private int mWaitTotalTime = 10;
    // 已经经过的时间
    private int mCurrentWaitTime = 0;

    private int mCurrentProgress = 0;

    private Paint mBackgroundPaint;
    private Paint mProgressPaint;
    private Paint mTextPaint;

    public SplashWaitView(Context context) {
        this(context, null);
    }

    public SplashWaitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashWaitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SplashWaitView);
        mBackgroundColor = array.getColor(R.styleable.SplashWaitView_splash_view_background_color, mBackgroundColor);
        mProgressColor = array.getColor(R.styleable.SplashWaitView_splash_view_progress_color, mProgressColor);
        mProgressWidth = array.getDimensionPixelSize(R.styleable.SplashWaitView_splash_view_progress_size, (int) DimensUtil.dp2px(mProgressWidth));
        mTextSizeColor = array.getColor(R.styleable.SplashWaitView_splash_view_text_color, mTextSizeColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.SplashWaitView_splash_view_text_size, (int) DimensUtil.sp2px(mTextSize));
        mWaitTotalTime = array.getInt(R.styleable.SplashWaitView_splash_view_wait_time, mWaitTotalTime);
        array.recycle();

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mBackgroundColor);

        mProgressPaint = new Paint();
        mProgressPaint.setDither(true);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextSizeColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentProgress == 0) {
            return;
        }
        // 1.先绘制背景
        int radius = getWidth() / 2;
        canvas.drawCircle(radius, radius, radius, mBackgroundPaint);
        // 2.绘制进度条
        float distance = mProgressWidth / 2;
        RectF rect = new RectF(distance, distance, getWidth() - distance, getHeight() - distance);
        canvas.drawArc(rect, 0, mCurrentProgress, false, mProgressPaint);
        // 3.绘制文字
        String value = String.valueOf(mWaitTotalTime - mCurrentWaitTime);
        // 宽度的一半减去字体长度的一半
        Rect rectText = new Rect();
        mTextPaint.getTextBounds(value, 0, value.length(), rectText);
        float x = getWidth() / 2 - rectText.width() / 2;
        // 基线
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float dy = (metrics.bottom - metrics.top) / 2 - metrics.bottom;
        float y = getHeight() / 2 + dy;
        canvas.drawText(value, x, y, mTextPaint);
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 360);
        animator.setDuration(mWaitTotalTime * 1000);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            mCurrentProgress = (int) value;
            mCurrentWaitTime = mCurrentProgress * mWaitTotalTime / 360;
            invalidate();
        });
        animator.start();
    }
}
