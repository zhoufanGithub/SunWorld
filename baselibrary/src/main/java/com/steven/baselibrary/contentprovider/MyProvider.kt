package com.steven.baselibrary.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

/**
 * author: zhoufan
 * data: 2021/6/18 16:10
 * content:自定义内容提供者
 */
class MyProvider : ContentProvider() {

    private val BOOK_DIR = 0
    private val BOOK_ITEM = 1
    private val CATEGORY_DIR = 2
    private val CATEGORY_ITEM = 3
    private val AUTHORITY = "com.steven.sunworld.provider"
    private var uriMatcher: UriMatcher? = null
    private var dbHelper: MyDatabaseHelper? = null

    init {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher!!.addURI(AUTHORITY, "book", BOOK_DIR)
        uriMatcher!!.addURI(AUTHORITY, "book/#", BOOK_ITEM)
        uriMatcher!!.addURI(AUTHORITY, "category", CATEGORY_DIR)
        uriMatcher!!.addURI(AUTHORITY, "category/#", CATEGORY_ITEM)
    }

    /**
     * 初始化的时候调用，通常会在这里完成对数据库的创建和升级
     * 返回true表示创建成功，false表示创建失败
     * 注意只有当存在ContentResolver尝试访问我们程序中的数据时，内容提供器才会被初始化
     */
    override fun onCreate(): Boolean {
        dbHelper = MyDatabaseHelper(context, "BookStore.db", null, 2)
        return true
    }

    /**
     * 从内容提供器中查询数据。
     * uri：查询哪张表
     * projection：查询哪一列
     * selection/selectionArgs：约束查询行
     * sortOrder：对结果进行排序
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper!!.readableDatabase
        var cursor: Cursor? = null
        when (uriMatcher?.match(uri)) {
            BOOK_DIR -> {
                cursor =
                    db.query("Book", projection, selection, selectionArgs, null, null, sortOrder)
            }
            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                cursor =
                    db.query("Book", projection, "id = ?", arrayOf(bookId), null, null, sortOrder)
            }
            CATEGORY_DIR -> {
                cursor = db.query(
                    "Category",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                cursor = db.query(
                    "Category",
                    projection,
                    "id = ?",
                    arrayOf(categoryId),
                    null,
                    null,
                    sortOrder
                )
            }
        }
        return cursor
    }

    /**
     * 向内容提供者添加一条数据
     * uri：插入到哪张表
     * values：插入的值
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper?.writableDatabase
        var uriReturn: Uri? = null
        when (uriMatcher?.match(uri)) {
            BOOK_DIR, BOOK_ITEM -> {
                val newBookId = db?.insert("Book", null, values)
                uriReturn = Uri.parse("content://$AUTHORITY/book/$newBookId")
            }
            CATEGORY_DIR, CATEGORY_ITEM -> {
                val newCategoryId = db?.insert("Category", null, values)
                uriReturn = Uri.parse("content://$AUTHORITY/category/$newCategoryId")
            }
        }
        return uriReturn
    }

    /**
     * 更新内容提供者中已有的数据
     * uri：更新哪一张表的数据
     * values：更新的数据
     * selection/selectionArgs：约束更新行
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val db = dbHelper?.writableDatabase
        var updatedRows = 0
        when (uriMatcher!!.match(uri)) {
            BOOK_DIR -> {
                updatedRows = db!!.update("Book", values, selection, selectionArgs)
            }
            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                updatedRows = db!!.update("Book", values, "id=?", arrayOf(bookId))
            }
            CATEGORY_DIR -> {
                updatedRows = db!!.update("Category", values, selection, selectionArgs)
            }
            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                updatedRows = db!!.update("Category", values, "id=?", arrayOf(categoryId))
            }
        }
        return updatedRows
    }

    /**
     * 从内容提供者中删除数据
     * uri：删除哪一张表的数据
     * selection/selectionArgs：约束删除行
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper?.writableDatabase
        var deletedRows = 0
        when (uriMatcher!!.match(uri)) {
            BOOK_DIR -> {
                deletedRows = db!!.delete("Book", selection, selectionArgs)
            }
            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                deletedRows = db!!.delete("Book",  "id=?", arrayOf(bookId))
            }
            CATEGORY_DIR -> {
                deletedRows = db!!.delete("Category", selection, selectionArgs)
            }
            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                deletedRows = db!!.delete("Category", "id=?", arrayOf(categoryId))
            }
        }
        return deletedRows
    }

    /**
     * 返回的MIME类型
     */
    override fun getType(uri: Uri): String? {
        var type: String? = null
        when (uriMatcher?.match(uri)) {
            BOOK_DIR -> {
                type =
                    "vnd.android.cursor.dir/vnd.com.steven.sunworld.provider.book"
            }
            BOOK_ITEM -> {
                type =
                    "vnd.android.cursor.item/vnd.com.steven.sunworld.provider.book"
            }
            CATEGORY_DIR -> {
                type =
                    "vnd.android.cursor.dir/vnd.com.steven.sunworld.provider.category"
            }
            CATEGORY_ITEM -> {
                type =
                    "vnd.android.cursor.item/vnd.com.steven.sunworld.provider.category"
            }
        }
        return type
    }
}