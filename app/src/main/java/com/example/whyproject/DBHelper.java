package com.example.whyproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "TESTC.db", null, 2);
    }

    private static final String CreateTB1 = "CREATE TABLE IF NOT EXISTS PWDTB (_id INTEGER PRIMARY KEY AUTOINCREMENT, SET_VALUE INTEGER DEFAULT 0, PASSWORD TEXT);";
    private static final String CreateTB2 = "CREATE TABLE IF NOT EXISTS STRESSTB (_id INTEGER PRIMARY KEY AUTOINCREMENT, S_DATE TEXT, S_CONTENT TEXT, S_VALUE INTEGER);";
    private static final String CreateTB3 = "CREATE TABLE IF NOT EXISTS RANKTB (_id INTEGER PRIMARY KEY AUTOINCREMENT, TARGET_NAME TEXT, COUNT INT);";


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTB1);
        db.execSQL(CreateTB2);
        db.execSQL(CreateTB3);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PWDTB");
        db.execSQL("DROP TABLE IF EXISTS STRESSTB");
        db.execSQL("DROP TABLE IF EXISTS RANKTB");
        onCreate(db);
    }

    //public void InsertDB(SQLiteDatabase db, )

}
