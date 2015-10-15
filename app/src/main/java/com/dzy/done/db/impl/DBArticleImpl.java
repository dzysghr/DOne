package com.dzy.done.db.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dzy.done.db.DBHelper;
import com.dzy.done.db.IArticleDAO;

/**
 * Created by dzysg on 2015/10/12 0012.
 */
public class DBArticleImpl implements IArticleDAO {


    private DBHelper mHelper = null;

    public DBArticleImpl()
    {
        mHelper = DBHelper.getInstance();
    }

    @Override
    public synchronized String select(String title) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query("article", new String[]{"content"}, "title=?", new String[]{title}, null, null, null);
        String re=null;

        if(cursor.moveToNext())
        re = cursor.getString(cursor.getColumnIndex("content"));

        cursor.close();
        db.close();


        return re;
    }

    @Override
    public synchronized boolean insert(String title,String content) {

        SQLiteDatabase db = mHelper.getWritableDatabase();


        Cursor cursor = db.query("article", new String[]{"title"}, "title=?", new String[]{title}, null, null, null);

        boolean exists = cursor.moveToNext();

        if(exists) {
            cursor.close();
            db.close();
            return true;
        }

        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("content",content);

        long re = db.insert("article", null, cv);

        db.close();

        return re>=0;

    }

    @Override
    public synchronized boolean delete(String title) {
        return false;
    }

    @Override
    public synchronized boolean update(String title) {
        return false;
    }
}
