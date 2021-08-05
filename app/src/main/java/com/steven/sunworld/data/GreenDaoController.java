package com.steven.sunworld.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * author: zhoufan
 * data: 2021/7/20 14:31
 * content:GreenDao的控制类
 */
public class GreenDaoController {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;
    private PersonInfoDao personInfoDao;
    private static GreenDaoController mInstance;

    public static GreenDaoController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (GreenDaoController.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoController(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     */
    private GreenDaoController(Context context) {
        mContext = context;
        mHelper = new DaoMaster.DevOpenHelper(mContext, "book.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        personInfoDao = mDaoSession.getPersonInfoDao();
    }

    /**
     * 获取可读取数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(mContext, "book.db", null);
        }
        return mHelper.getReadableDatabase();
    }

    /**
     * 获取可写入数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(mContext, "book.db", null);
        }
        return mHelper.getWritableDatabase();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public long insertOrReplace(PersonInfo personInfo) {
        return personInfoDao.insertOrReplace(personInfo);
    }

    /**
     * 插入一条新数据
     */
    public long insert(PersonInfo personInfo) {
        return personInfoDao.insert(personInfo);
    }

    /**
     * 更新数据
     */
    public void update(PersonInfo personInfo) {
        PersonInfo oldPersonInfo = personInfoDao.queryBuilder().where(PersonInfoDao.Properties.BookId.eq(personInfo.getBookId())).build().unique();
        if (oldPersonInfo != null) {
            oldPersonInfo.setBookName(personInfo.getBookName());
            personInfoDao.update(oldPersonInfo);
        }
    }

    /**
     * 查询所有数据
     */
    public List<PersonInfo> searchAll() {
        return personInfoDao.queryBuilder().list();
    }

    /**
     * 根据bookId删除数据
     */
    public void delete(int bookId) {
        personInfoDao.queryBuilder().where(PersonInfoDao.Properties.BookId.eq(bookId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
