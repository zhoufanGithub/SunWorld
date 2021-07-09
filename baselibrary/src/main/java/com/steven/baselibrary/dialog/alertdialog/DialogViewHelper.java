package com.steven.baselibrary.dialog.alertdialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by zhoufan on 2018/1/27.
 * DialogView的辅助处理类
 */

public class DialogViewHelper {

    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    // 设置布局
    public void setContentView(View mView) {
        this.mContentView = mView;
    }

    // 设置TextView
    public void setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }


    // 设置点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View tv = getView(viewId);
        if (tv != null) {
            tv.setOnClickListener(listener);
        }
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);
        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null)
                mViews.put(viewId, new WeakReference<>(view));
        }
        return (T) view;
    }

    // 获取Content内容的View
    public View getContentView() {
        return mContentView;
    }
}
