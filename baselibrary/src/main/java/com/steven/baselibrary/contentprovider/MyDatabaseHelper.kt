package com.steven.baselibrary.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * author: zhoufan
 * data: 2021/6/18 16:42
 * content:
 */
class MyDatabaseHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private var mContext: Context? = null

    init {
        mContext = context
    }

    // 创建SQL语句
    var CREATE_BOOK =
        "create table Book (" + "id integer primary key autoincrement, " + "author text, " + "price real, " + "pages integer," + "name text)"
    var CREATE_CATEGORY =
        "create table Category (" + "id integer primary key autoincrement, " + "category_name text, " + "category_code integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BOOK)
        db?.execSQL(CREATE_CATEGORY)
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}