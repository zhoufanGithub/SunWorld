package com.steven.baselibrary.dialog.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by zhoufan on 2018/1/27.
 * 辅助类
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    // 设置TextView
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }


    // 设置点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }

    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        // 点击空白区是否可以取消
        public boolean mCancelable = true;
        // dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog dismiss 监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // dialog 按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        // 布局View
        public View mView;
        // 布局LayoutId
        public int mViewLayoutResId;
        // 存放所有的文本
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        // 存放所有的点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        // 设置宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置动画
        public int mAnimations = 0;
        // 设置从底部弹出
        public int mGravity = Gravity.CENTER;
        // 设置高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        // 绑定和设置参数
        public void apply(AlertController mAlert) {
            DialogViewHelper viewHelper = null;
            // 设置布局
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            mAlert.setViewHelper(viewHelper);

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }

            // 给Dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            // 设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 设置点击事件
            int textClickSize = mClickArray.size();
            for (int i = 0; i < textClickSize; i++) {
                viewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }


            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            if (mAnimations != 0)
                window.setWindowAnimations(mAnimations);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(params);

        }
    }
}
