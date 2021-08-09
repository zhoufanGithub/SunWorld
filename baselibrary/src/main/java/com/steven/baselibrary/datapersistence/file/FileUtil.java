package com.steven.baselibrary.datapersistence.file;

import com.steven.baselibrary.IApplication;
import com.steven.baselibrary.R;
import com.steven.baselibrary.datapersistence.IOFactoryUtil;
import com.steven.baselibrary.datapersistence.IOHandler;
import com.steven.baselibrary.util.MyToast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * author: zhoufan
 * data: 2021/8/9 10:21
 * content: 对文件的操作进行进一步的封装
 */
public class FileUtil implements IOHandler {

    // 使用单例生成唯一实例
    private static volatile FileUtil mInstance;
    private String mFileName;

    public static FileUtil getInstance() {
        if (mInstance == null) {
            synchronized (IOFactoryUtil.class) {
                if (mInstance == null) {
                    mInstance = new FileUtil();
                }
            }
        }
        return mInstance;
    }

    private FileUtil() {
        mFileName = "fileCache";
    }


    @Override
    public IOHandler saveString(String key, String value) {
        BufferedWriter bufferedWriter = null;
        try {
            boolean isResult = FileTool.createFolderAndFileItemDir(mFileName, key);
            if (isResult) {
                FileWriter fileWriter = new FileWriter(key);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(value);
                bufferedWriter.flush();
            } else {
                MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.create_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public IOHandler saveFloat(String key, float value) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return this;
    }

    @Override
    public IOHandler saveInt(String key, int value) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return this;
    }

    @Override
    public IOHandler saveBoolean(String key, boolean value) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return this;
    }

    @Override
    public IOHandler saveLong(String key, long value) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return this;
    }

    @Override
    public String getString(String key) {
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            String path = FileTool.getItemDirPath() + key;
            File file = new File(path);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader, 512);
                String readBuff = null;
                while ((readBuff = bufferedReader.readLine()) != null) {
                    builder.append(readBuff);
                }
            } else {
                MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_no_exists));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return 0;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return false;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        MyToast.showCenterSortToast(IApplication.getContext(), IApplication.getContext().getString(R.string.file_type_error));
        return 0;
    }

    @Override
    public void delete(String key) {
        FileTool.deleteFile(key);
    }

    @Override
    public void updateFileName(String fileName) {
        this.mFileName = fileName;
    }
}
