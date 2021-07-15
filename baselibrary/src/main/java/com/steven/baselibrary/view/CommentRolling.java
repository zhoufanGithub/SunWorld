package com.steven.baselibrary.view;

import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.steven.baselibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: zhoufan
 * data: 2021/7/14 17:03
 * content:实现评论滚动的效果
 *
 * 第一步：设计XML文件
 *  <RelativeLayout
 *         android:layout_width="match_parent"
 *         android:layout_height="500dp"
 *         app:layout_constraintBottom_toBottomOf="parent"
 *         app:layout_constraintEnd_toEndOf="parent"
 *         app:layout_constraintStart_toStartOf="parent">
 *
 *         <ScrollView
 *             android:layout_width="match_parent"
 *             android:layout_height="wrap_content"
 *             android:layout_alignParentBottom="true"
 *             android:layout_marginLeft="20dp"
 *             android:layout_marginTop="80dp"
 *             android:layout_marginRight="20dp"
 *             android:layout_marginBottom="60dp"
 *             android:overScrollMode="never"
 *             android:scrollbars="none">
 *
 *             <com.steven.baselibrary.view.CommentRolling
 *                 android:id="@+id/comment_rolling_layout"
 *                 android:layout_width="match_parent"
 *                 android:layout_height="wrap_content" />
 *
 *         </ScrollView>
 *     </RelativeLayout>
 *
 * 第二步：自定义View继承自LinearLayout
 * 第三步：添加数据查看效果
 *        val list = mutableListOf<MutableMap<String, Any>>().apply {
 *             for (i in 0..10) {
 *                  val map = mutableMapOf<String,Any>().apply {
 *                      put("commentImage","https://i.52112.com/icon/jpg/256/20210315/113065/4866441.jpg")
 *                      put("commentText","当前数据当前数据当前数据当前数据当前数据为$i")
 *                  }
 *                 add(map)
 *             }
 *         }
 *         comment_rolling_layout.setList(list)
 */
public class CommentRolling extends LinearLayout {

    private Context mContext;
    private List<Map<String, Object>> mCommentList;
    private int mCurrent = 0;

    public CommentRolling(Context context) {
        this(context, null);
    }

    public CommentRolling(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentRolling(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(VERTICAL);
        mCommentList = new ArrayList<>();
    }


    /**
     * 设置数据
     */
    public void setList(List<Map<String, Object>> commentList) {
        if (mCommentList != null) {
            mCommentList.addAll(commentList);
            addText();
        }
    }

    /**
     * 添加单条数据
     */
    public void addList(Map<String, Object> map) {
        if (mCommentList != null) {
            mCommentList.add(map);

        }
    }

    /**
     * 添加View
     */
    private void addText() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_comment_rolling, this, false);
        ShapeableImageView imageView = view.findViewById(R.id.comment_rolling_image);
        TextView textView = view.findViewById(R.id.comment_rolling_text);
        Glide.with(mContext).load(mCommentList.get(mCurrent).get("commentImage")).into(imageView);
        textView.setText(String.valueOf(mCommentList.get(mCurrent).get("commentText")));
        if (mCurrent == 0) {
            view.setAlpha(0);
        }
        addView(view);
        view.post(() -> {
            ((ScrollView) getParent()).fullScroll(ScrollView.FOCUS_DOWN);
            ScaleAnimation scaleAnimation1 = new ScaleAnimation(0f, 1f, 0f, 1f, 0, view.getHeight());
            scaleAnimation1.setDuration(1000);
            scaleAnimation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (mCurrent == 0) {
                        view.setAlpha(1);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCurrent++;
                    if (mCurrent < mCommentList.size() - 1) {
                        addText();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(scaleAnimation1);
        });
    }
}
