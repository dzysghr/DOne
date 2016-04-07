package com.dzy.done.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dzy.done.config.app;

/**
 * 数据库，已淘汰
 * Created by Troy on 2015/9/23.
 */
@Deprecated
public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;

    public static String DATABASE_NAME = "One.db";

    public static String CREATE_ARTICLE_TABLE = "CREATE TABLE if not exists webview_activity("
            + "id INTEGER PRIMARY KEY autoincrement,"
            + "title TEXT NOT NULL,"
            + "content TEXT NOT NULL)";


    public static String CREATE_PICTURE_TABLE = "CREATE TABLE if not exists picture("
            + "id SMALLINT PRIMARY KEY,"
            + "img TEXT,"
            + "num TEXT,"
            + "content TEXT,"
            + "day TEXT,"
            + "year TEXT)";

    public static String CREATE_THING_TABLE = "CREATE TABLE if not exists thing("
            + "id SMALLINT PRIMARY KEY,"
            + "title TEXT,"
            + "img TEXT ,"
            + "content TEXT)";


    public static String CREATE_LISTITEM_TABLE = "CREATE TABLE if not exists listitem("
            + "id SMALLINT PRIMARY KEY,"
            + "title TEXT,"
            + "img TEXT,"
            + "date TEXT,"
            + "url TEXT)";

    public DBHelper() {
        super(app.getContext(), DATABASE_NAME, null, VERSION);
    }

    private static DBHelper dbHelper = null;

    public static DBHelper getInstance(){
        if (dbHelper==null)
            dbHelper = new DBHelper();

        return dbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE_TABLE);
        db.execSQL(CREATE_PICTURE_TABLE);
        db.execSQL(CREATE_THING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
