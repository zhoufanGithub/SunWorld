package com.steven.baselibrary.banner.config;


import com.steven.baselibrary.banner.util.BannerUtils;

public class BannerConfig {
    public static final boolean IS_AUTO_LOOP = true;
    public static final boolean IS_INFINITE_LOOP = true;
    public static final int LOOP_TIME = 3000;
    public static final int SCROLL_TIME = 600;
    public static final int INCREASE_COUNT = 2;
    public static final int INDICATOR_NORMAL_COLOR = 0x88ffffff;
    public static final int INDICATOR_SELECTED_COLOR = 0x88000000;
    public static final int INDICATOR_NORMAL_WIDTH = BannerUtils.dp2px(5);
    public static final int INDICATOR_SELECTED_WIDTH = BannerUtils.dp2px(7);
    public static final int INDICATOR_SPACE = BannerUtils.dp2px(5);
    public static final int INDICATOR_MARGIN = BannerUtils.dp2px(5);
    public static final int INDICATOR_HEIGHT = BannerUtils.dp2px(3);
    public static final int INDICATOR_RADIUS = BannerUtils.dp2px(3);

}
