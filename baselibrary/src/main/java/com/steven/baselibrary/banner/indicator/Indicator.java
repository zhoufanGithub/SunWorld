package com.steven.baselibrary.banner.indicator;

import android.view.View;

import androidx.annotation.NonNull;

import com.steven.baselibrary.banner.config.IndicatorConfig;
import com.steven.baselibrary.banner.listener.OnPageChangeListener;

public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
