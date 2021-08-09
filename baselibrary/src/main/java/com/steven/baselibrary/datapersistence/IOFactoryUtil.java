package com.steven.baselibrary.datapersistence;

import com.steven.baselibrary.datapersistence.file.FileUtil;
import com.steven.baselibrary.datapersistence.mmkv.MMKVUtil;
import com.steven.baselibrary.datapersistence.sharedperference.SharePreferencesUtil;

/**
 * Created by zhoufan on 2018/2/24.
 * 使用抽象工厂方法实现对缓存的处理
 */

public class IOFactoryUtil implements IOFactory {

    // 使用单例生成唯一实例
    private static volatile IOFactoryUtil ioFactoryUtil;
    public static final String MMKV_HANDLER = "1";
    public static final String SHAREDPREFERENCE_HANDLER = "2";
    public static final String FILE_HANDLER = "3";
    private IOHandler mIOHandler;

    public static IOFactoryUtil getIOFactoryUtil() {
        if (ioFactoryUtil == null) {
            synchronized (IOFactoryUtil.class) {
                if (ioFactoryUtil == null) {
                    ioFactoryUtil = new IOFactoryUtil();
                }
            }
        }
        return ioFactoryUtil;
    }

    @Override
    public IOHandler create(String type) {
        switch (type) {
            case SHAREDPREFERENCE_HANDLER:
                mIOHandler = getSharePreferencesHandler();
            case FILE_HANDLER:
                mIOHandler = getFileHandler();
            default:
                mIOHandler = getMMKVHandler();
        }
        return mIOHandler;
    }

    // SharePreferences存储
    private IOHandler getSharePreferencesHandler() {
        return SharePreferencesUtil.getInstance();
    }

    // MMKV存储
    private IOHandler getMMKVHandler() {
        return MMKVUtil.getInstance();
    }

    // 文件存储
    private IOHandler getFileHandler() {
        return FileUtil.getInstance();
    }

    // 默认的存储方式
    public IOHandler getUserHandler() {
        return create(MMKV_HANDLER);
    }
}
