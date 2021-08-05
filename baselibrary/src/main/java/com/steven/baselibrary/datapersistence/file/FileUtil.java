package com.steven.baselibrary.datapersistence.file;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.steven.baselibrary.IApplication;

import java.io.File;
import java.io.IOException;

/**
 * author: zhoufan
 * data: 2021/8/3 12:05
 * content: 所有的文件操作类
 * 内存（Memory）
 * 内部存储（InternalStorage）：不需要申请权限
 * 由系统进行维护，Context.getFileDir()：获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感数据如用户个人信息
 * Context.getCacheDir()：获取内置存储下的缓存目录，可以用来保存一些缓存文件如图片，当内置存储的空间不足时将系统自动被清除
 * 绝对路径：Context.getFileDir()：/data/data/应用包名/files/
 * Context.getCacheDir()：/data/data/应用包名/cache/
 * 外部存储（ExternalStorage）：需要申请权限
 * 由于内部存储空间有限，在开发中我们一般都是操作外部存储空间。
 * Google官方建议我们App的数据应该存储在外部存储的私有目录中该App的包名下，这样当用户卸载掉App之后，相关的数据会一并删除，
 * 如果你直接在/storage/sdcard目录下创建了一个应用的文件夹，那么当你删除应用的时候，这个文件夹就不会被删除。
 */
public class FileUtil {

    // 判断SD卡是否被挂载
    private static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // 获取SDCard卡的根目录
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            File sdDir = null;
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                sdDir = IApplication.getContext().getExternalFilesDir(null);
            } else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
            return sdDir.toString();
        }
        return null;
    }

    // 获取SDCard的完整空间大小，返回MB
    public static long getSDCardSize() {
        if (isSDCardMounted()) {
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = fs.getBlockCountLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    // 获取SDCard卡的剩余空间大小
    public static long getSDCardFreeSize() {
        if (isSDCardMounted()) {
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = fs.getFreeBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    // 获取SDCard卡的可用空间大小
    public static long getSDCardAvailableSize() {
        if (isSDCardMounted()) {
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = fs.getAvailableBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }


    // 在手机存储（外部存储）的根目录下面创建文件夹和文件
    public static boolean createFolderAndFileBaseDir(String folderName, String fileName) {
        boolean isSuccess = false;
        if (isSDCardMounted()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath() + File.separator + folderName;
                File file = new File(path);
                if (!file.exists()) {
                    // 创建文件夹
                    file.mkdirs();
                }
                file = new File(path, fileName);
                if (!file.exists()) {
                    isSuccess = file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    // 在手机存储（外部存储）的根目录下面创建文件夹
    public static boolean createFolderBaseDir(String folderName) {
        boolean isSuccess = false;
        if (isSDCardMounted()) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + folderName;
            File file = new File(path);
            if (!file.exists()) {
                // 创建文件夹
                isSuccess = file.mkdirs();
            }
        }
        return isSuccess;
    }


    // 在项目的根目录下面创建文件夹和文件
    public static boolean createFolderAndFileItemDir(String folderName, String fileName) {
        boolean isSuccess = false;
        if (isSDCardMounted()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/" + IApplication.getContext().getPackageName() + File.separator + folderName;
                File file = new File(path);
                if (!file.exists()) {
                    // 创建文件夹
                    file.mkdirs();
                }
                file = new File(path, fileName);
                if (!file.exists()) {
                    isSuccess = file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    // 在项目的根目录下面创建文件夹
    public static boolean createFolderItemDir(String folderName) {
        boolean isSuccess = false;
        if (isSDCardMounted()) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/" + IApplication.getContext().getPackageName() + File.separator + folderName;
            File file = new File(path);
            if (!file.exists()) {
                // 创建文件夹
                isSuccess = file.mkdirs();
            }
        }
        return isSuccess;
    }

    // 删除文件或文件夹
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    @SuppressLint("LongLogTag")
    private static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            for (String fileName : file.list()) {
                File item = new File(file, fileName);
                if (item.isDirectory()) {
                    deleteFile(item);
                } else {
                    if (!item.delete()) {
                        Log.d("Failed in recursively deleting a file, file's path is: ", item.getPath());
                    }
                }
            }
            if (!file.delete()) {
                Log.d("Failed in recursively deleting a directory, directories' path is: ", file.getPath());
            }
        } else {
            if (!file.delete()) {
                Log.d("Failed in deleting this file, its path is: ", file.getPath());
            }
        }
        return true;
    }

    // 获取手机存储（外部存储）的根目录路径
    public static String getBaseDirPath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    // 获取项目的根目录路径
    public static String getItemDirPath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/" + IApplication.getContext().getPackageName() + File.separator;
    }
}
