package com.steven.baselibrary.datapersistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.steven.baselibrary.IApplication;
import com.steven.baselibrary.datapersistence.IOFactoryUtil;

/**
 * author: zhoufan
 * data: 2021/8/8 10:27
 * content:SQLite数据库操作类
 * 1》 getReadableDatabase()和getWritableDatabase()都可以创建或者打开一个数据库。
 * 如果数据库存在则直接打开，如果不存在则直接创建。同时返回一个可对数据库进行读写操作的对象。
 * 二者不同的是，当数据库不可写入的时候（比如磁盘空间已满），getReadableDatabase()方法返回的
 * 对象将以只读的方式去打开数据库，而getWritableDatabase()方法将出现异常。
 * 2》数据库文件会放在data/data/包名/databases目录下面
 * 3》创建表的时候：
 * （1）integer表示整型
 * （2）real表示浮点型
 * （3）text表示文本类型
 * （4）blob表示二进制类型
 * （5）primary key设为主键
 * （6）autoincrement自增长
 */
public class SQLiteUtil {

    // 使用单例生成唯一实例
    private static volatile SQLiteUtil mInstance;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase db;

    public static SQLiteUtil getInstance() {
        if (mInstance == null) {
            synchronized (IOFactoryUtil.class) {
                if (mInstance == null) {
                    mInstance = new SQLiteUtil();
                }
            }
        }
        return mInstance;
    }

    private SQLiteUtil() {
        mDatabaseHelper = new DatabaseHelper(IApplication.getContext(), "Book.db", null, 1);
        db = mDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入数据
     */
    public void insertData() {
        ContentValues contentValues = new ContentValues();
        // 开始组装第一条数据
        contentValues.put("name", "android开发艺术");
        contentValues.put("author", "任玉刚");
        contentValues.put("price", 88.8);
        contentValues.put("pages", 778);
        long result = db.insert("Book", null, contentValues);
        if (result > -1) {
            Toast.makeText(IApplication.getContext(), "插入数据成功", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 修改数据
     */
    public void updateData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", 66.6);
        // String table, ContentValues values, String whereClause, String[] whereArgs
        // 第一个参数对应的是表名
        // 第二个参数对应的是修改内容
        // 第三、四个参数对应的是约束的条件
        long result = db.update("Book", contentValues, "author=?", new String[]{"任玉刚"});
        if (result > -1) {
            Toast.makeText(IApplication.getContext(), "修改数据成功", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 删除数据
     */
    public void deleteData() {
        // 第一个参数对应的是表名
        // 第二、三个参数对应的是约束的条件
        long result = db.delete("Book", "pages>?", new String[]{"500"});
        if (result > -1) {
            Toast.makeText(IApplication.getContext(), "删除数据成功", Toast.LENGTH_LONG).show();
        }
    }

    public void queryData() {
        // String table, String[] columns, String selection,String[] selectionArgs, String groupBy, String having,String orderBy
        // 第一个参数代表的是表名
        // 第二个参数代表的是查询的列名
        // 第三个参数代表的是指定where的约束条件
        // 第四个参数代表的是为where中指定的占位符提供具体的值
        // 第五个参数代表的是指定需要group by的列
        // 第六个参数代表的是对group by后的结果进一步约束
        // 第七个参数代表的是指定查询结果的排序方式
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                String showValue = "书名：" + name + "\t作者名：" + author + "\t总页数：" + pages + "\t价格：" + prices;
                Toast.makeText(IApplication.getContext(), showValue, Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
