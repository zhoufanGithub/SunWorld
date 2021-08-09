package com.steven.baselibrary.datapersistence.sharedperference;

import android.content.SharedPreferences;

import com.steven.baselibrary.IApplication;
import com.steven.baselibrary.datapersistence.IOFactoryUtil;
import com.steven.baselibrary.datapersistence.IOHandler;


/**
 * Created by zhoufan on 2018/2/24.
 * 使用SharePreferences来保存数据
 * <p>
 * commit和apply方法的区别：
 * commit和apply虽然都是原子性操作，但是原子的操作不同，commit是原子提交到数据库，所以从提交数据到存在Disk中都是同步过程，中间不可打断。
 * 而apply方法的原子操作是原子提交的内存中，而非数据库，所以在提交到内存中时可打断，之后再异步提交数据到数据库中，因此也不会有相应的返回值。
 * 所有commit提交是同步过程，效率会比apply异步提交的速度慢，但是apply没有返回值，永远无法知道存储是否失败。
 * 在不关心提交结果是否成功的情况下，优先考虑apply方法。
 */

public class SharePreferencesUtil implements IOHandler {

    // 使用单例生成唯一实例
    private static volatile SharePreferencesUtil mInstance;
    private String mFileName;

    public static SharePreferencesUtil getInstance() {
        if (mInstance == null) {
            synchronized (IOFactoryUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharePreferencesUtil();
                }
            }
        }
        return mInstance;
    }

    private SharePreferencesUtil() {
        mFileName = "sharedPreferenceCache";
    }

    @Override
    public SharePreferencesUtil saveString(String key, String value) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        return this;
    }

    @Override
    public SharePreferencesUtil saveFloat(String key, float value) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
        return this;
    }

    @Override
    public SharePreferencesUtil saveInt(String key, int value) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
        return this;
    }

    @Override
    public SharePreferencesUtil saveBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        return this;
    }

    @Override
    public SharePreferencesUtil saveLong(String key, long value) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
        return this;
    }


    @Override
    public String getString(String key) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        return sharedPreferences.getString(key, null);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
        return sharedPreferences.getLong(key, defaultValue);
    }

    // 执行删除操作
    @Override
    public void delete(String key) {
        try {
            SharedPreferences sharedPreferences = IApplication.getContext().getSharedPreferences(mFileName, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.contains(key)) {
                editor.remove(key);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFileName(String fileName) {
        this.mFileName = fileName;
    }
}
