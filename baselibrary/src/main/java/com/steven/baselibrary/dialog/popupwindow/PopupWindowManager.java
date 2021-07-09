package com.steven.baselibrary.dialog.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.steven.baselibrary.R;

/**
 * author: zhoufan
 * data: 2021/7/8 11:41
 * content:PopupWindow管理类
 * 基本的使用方式：
 *         val popView = LayoutInflater.from(this).inflate(R.layout.pop_view, null)
 *         PopupWindowManager.getInstance().init(popView).setOnDismissListener {
 *             PopupWindowManager.getInstance().close()
 *         }.setTouchInterceptor { v, event ->
 *             PopupWindowManager.getInstance().close()
 *             false
 *         }.showAsDropDown(view)
 */
public class PopupWindowManager {

    // 设置默认的宽高
    private int mPopupWindowWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private int mPopupWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    private MyPopupWindow mPopupWindow;
    private static volatile PopupWindowManager mInstance;

    /**
     * 通过懒汉式来构建PopupWindowManager
     *
     * @return PopupWindowManager实例对象
     */
    public static PopupWindowManager getInstance() {
        if (mInstance == null) {
            synchronized (PopupWindowManager.class) {
                if (mInstance == null) {
                    mInstance = new PopupWindowManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置私有的构造函数
     */
    private PopupWindowManager() {
        mPopupWindow = new MyPopupWindow();
    }

    /**
     * 获取PopupWindow的实例
     *
     * @return PopupWindow
     */
    private PopupWindow getMyPopupWindow() {
        return mPopupWindow;
    }

    /**
     * 初始化设置(默认宽高)
     *
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager init(View contentView) {
        return init(contentView, mPopupWindowWidth, mPopupWindowHeight);
    }

    /**
     * @param contentView 加载的View
     * @param width       设置的宽度
     * @param height      设置的高度
     * @return PopupWindowManager实例对象
     * 默认情况下：（1）popupWindow点击外部区域可以关闭
     * （2）popupWindow可以聚焦
     * // 设置PopupWindow可以聚焦
     * // 如果不设置，在PopupWindow弹出的时候，点击返回键将直接退出Activity
     * // 设置之后，在PopupWindow弹出的时候，点击返回键不会直接退出Activity而是关闭当前弹出的PopupWindow
     * （3）popupWindow弹出的动画高度为0
     * （4）popupWindow内容区域可以触摸
     */
    public PopupWindowManager init(View contentView, int width, int height) {
        this.mPopupWindowWidth = width;
        this.mPopupWindowHeight = height;
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setElevation(0);
        mPopupWindow.setTouchable(true);
        setBackgroundDrawable();
        mPopupWindow.setWidth(mPopupWindowWidth);
        mPopupWindow.setHeight(mPopupWindowHeight);
        mPopupWindow.setAnimationStyle(R.style.popup_window_anim_style);
        return this;
    }

    /**
     * 设置popupWindow的背景
     *
     * @return PopupWindowManager实例对象
     */
    private PopupWindowManager setBackgroundDrawable() {
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#50000000"));
        mPopupWindow.setBackgroundDrawable(dw);
        return this;
    }

    /**
     * 设置popupWindow的背景
     *
     * @param color 设置的背景颜色
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setBackgroundDrawable(int color) {
        ColorDrawable dw = new ColorDrawable(color);
        mPopupWindow.setBackgroundDrawable(dw);
        return this;
    }

    /**
     * 设置popupWindow的动画效果
     * @param animationStyle 动画样式
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setAnimationStyle(int animationStyle){
        mPopupWindow.setAnimationStyle(animationStyle);
        return this;
    }

    /**
     * 设置popupWindow点击外部区域是否可以关闭
     *
     * @param isOutsideTouchable true代表可以关闭 false代表不可以关闭
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setOutsideTouchable(boolean isOutsideTouchable) {
        mPopupWindow.setOutsideTouchable(isOutsideTouchable);
        return this;
    }

    /**
     * popupWindow是否可以聚焦
     *
     * @param isFocusable true代表可以聚焦 false代表不可以聚焦
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setFocusable(boolean isFocusable) {
        mPopupWindow.setFocusable(isFocusable);
        return this;
    }

    /**
     * 设置popupWindow弹出的动画高度
     *
     * @param elevation 高度
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setElevation(float elevation) {
        mPopupWindow.setElevation(elevation);
        return this;
    }

    /**
     * 设置popupWindow内容区域是否可以触摸
     *
     * @param touchable true代表可以触摸 false代表不可以触摸
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setTouchable(boolean touchable) {
        mPopupWindow.setTouchable(touchable);
        return this;
    }

    /**
     * 设置关闭PopupWindow的监听
     *
     * @param onDismissListener 设置的监听实例
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        mPopupWindow.setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * 在PopupWindow弹出之后点击任意区域关闭
     *
     * @param listener 设置的监听实例
     * @return PopupWindowManager实例对象
     */
    public PopupWindowManager setTouchInterceptor(View.OnTouchListener listener) {
        mPopupWindow.setTouchInterceptor(listener);
        return this;
    }

    /**
     * 在指定视图的下方展示
     *
     * @param anchor 目标View
     *               一般情况下展示的视图以目标View的左下方的位置作为锚点
     */
    public void showAsDropDown(View anchor) {
        mPopupWindow.showAsDropDown(anchor);
    }

    /**
     * 在指定视图的下方展示
     *
     * @param anchor 目标View
     * @param xoff   x轴的偏移量
     * @param yoff   y轴的偏移量
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 在指定视图的下方展示
     *
     * @param anchor  目标View
     * @param xoff    x轴的偏移量
     * @param yoff    y轴的偏移量
     * @param gravity 相对于View的定位，系统默认是Gravity.TOP | Gravity.START;
     */
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        mPopupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    /**
     * 以绝对值（x,y）来进行显示
     *
     * @param parent  View parent代表的是要能获取到window唯一标示的（也就是只要能获取到window 标示，view是什么控件都可以）
     * @param gravity int gravity代表的是位置，即屏幕的上下左右，注意系统默认都是在屏幕中间
     * @param x       x轴的偏移量
     * @param y       y轴的偏移量
     */
    public void showAtLocation(View parent, int gravity, int x, int y) {
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 关闭PopupWindow
     */
    public void close() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
