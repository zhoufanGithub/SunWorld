package com.steven.baselibrary.datapersistence.mmkv;

import com.steven.baselibrary.datapersistence.IOFactoryUtil;
import com.steven.baselibrary.datapersistence.IOHandler;
import com.tencent.mmkv.MMKV;

/**
 * author: zhoufan
 * data: 2021/8/8 9:29
 * content: 数据持久化过程中使用MMKV进行存储
 */
public class MMKVUtil implements IOHandler {

    // 使用单例生成唯一实例
    private static volatile MMKVUtil mInstance;
    private MMKV mMMKV;

    public static MMKVUtil getInstance() {
        if (mInstance == null) {
            synchronized (IOFactoryUtil.class) {
                if (mInstance == null) {
                    mInstance = new MMKVUtil();
                }
            }
        }
        return mInstance;
    }

    private MMKVUtil() {
        mMMKV = MMKV.defaultMMKV();
    }


    @Override
    public IOHandler saveString(String key, String value) {
        if (key == null || value == null) {
            return this;
        }
        mMMKV.encode(key, value);
        return this;
    }

    @Override
    public IOHandler saveFloat(String key, float value) {
        if (key == null) {
            return this;
        }
        mMMKV.encode(key, value);
        return this;
    }

    @Override
    public IOHandler saveInt(String key, int value) {
        if (key == null) {
            return this;
        }
        mMMKV.encode(key, value);
        return this;
    }

    @Override
    public IOHandler saveBoolean(String key, boolean value) {
        if (key == null) {
            return this;
        }
        mMMKV.encode(key, value);
        return this;
    }

    @Override
    public IOHandler saveLong(String key, long value) {
        if (key == null) {
            return this;
        }
        mMMKV.encode(key, value);
        return this;
    }

    @Override
    public String getString(String key) {
        if (key == null) {
            return null;
        }
        return mMMKV.decodeString(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        return mMMKV.decodeFloat(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        return mMMKV.decodeInt(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        return mMMKV.decodeBool(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        return mMMKV.decodeLong(key);
    }

    @Override
    public void delete(String key) {
        mMMKV.removeValueForKey(key);
    }

    @Override
    public void updateFileName(String fileName) {
        mMMKV = MMKV.mmkvWithID(fileName);
    }
}
