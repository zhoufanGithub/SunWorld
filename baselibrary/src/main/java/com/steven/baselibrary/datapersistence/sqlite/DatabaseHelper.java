package com.steven.baselibrary.datapersistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * author: zhoufan
 * data: 2021/8/8 10:16
 * content: SQLite数据库操作类
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book("
            + "id integer primary key autoincrement,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "name text)";

    public static final String CREATE_CATEGORY = "create table Category("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";

    private Context mContext;

    /**
     * SQLite的数据库构造方法
     *
     * @param context 上下文
     * @param name    数据库名称
     * @param factory 一个可选的游标工厂（通常是 Null）
     * @param version 当前数据库的版本，值必须是整数并且是递增的状态
     */
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    /**
     * 对数据库进行初始化操作
     *
     * @param db 数据库操作类
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表名
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Book表创建成功", Toast.LENGTH_SHORT).show();
        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
    }

    /**
     * 数据库版本切换
     *
     * @param db         数据库操作类
     * @param oldVersion 之前的数据库的版本
     * @param newVersion 设置的数据库的版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
