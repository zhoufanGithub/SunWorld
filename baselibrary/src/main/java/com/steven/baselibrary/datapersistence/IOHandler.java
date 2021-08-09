package com.steven.baselibrary.datapersistence;

/**
 * Created by zhoufan on 2018/2/24.
 * 实现对数据的存储
 */

public interface IOHandler {

    IOHandler saveString(String key, String value);

    IOHandler saveFloat(String key, float value);

    IOHandler saveInt(String key, int value);

    IOHandler saveBoolean(String key, boolean value);

    IOHandler saveLong(String key, long value);

    String getString(String key);

    float getFloat(String key, float defaultValue);

    int getInt(String key, int defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    long getLong(String key, long defaultValue);

    void delete(String key);

    void updateFileName(String fileName);

}
