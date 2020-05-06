package com.example.whyproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "TEST3.db", null, 2);
    }

    //private static final String CreateTB = "CREATE TABLE IF NOT EXISTS " + AddFriends.TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + AddFriends.KEY_NAME + " TEXT, " + AddFriends.KEY_PHONE + " TEXT);";
    private static final String CreateTB1 = "CREATE TABLE IF NOT EXISTS PWDTB (_id INTEGER PRIMARY KEY AUTOINCREMENT, SET_VALUE INTEGER DEFAULT 0, PASSWORD INTEGER);";
    private static final String CreateTB2 = "CREATE TABLE IF NOT EXISTS STRESSTB (_id INTEGER PRIMARY KEY AUTOINCREMENT, S_DATE DATE, S_CONTENT TEXT, S_VALUE INTEGER);";
 //   private static final String CreateTB3 = "CREATE TABLE IF NOT EXISTS BORROWED (_id INTEGER PRIMARY KEY AUTOINCREMENT, borrowname TEXT, borrowmoney INTEGER);";
 //   private static final String CreateTB4 = "CREATE TABLE IF NOT EXISTS DUTCHPAY (_id INTEGER PRIMARY KEY AUTOINCREMENT, kinds TEXT, content TEXT, price TEXT);";
  //  private static final String CreateTB5 = "CREATE TABLE IF NOT EXISTS ADDNAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";

    public void onCreate(SQLiteDatabase db) {
      //  db.execSQL(CreateTB);
        db.execSQL(CreateTB1);
        db.execSQL(CreateTB2);
//        db.execSQL(CreateTB3);
//        db.execSQL(CreateTB4);
//        db.execSQL(CreateTB5);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//      db.execSQL("DROP TABLE IF EXISTS " + AddFriends.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS PWDTB");
        db.execSQL("DROP TABLE IF EXISTS STRESSTB");
//      db.execSQL("DROP TABLE IF EXISTS BORROWED");
//      db.execSQL("DROP TABLE IF EXISTS DUTCHPAY");
//      db.execSQL("DROP TABLE IF EXISTS ADDNAME");
//      onCreate( db );
    }

}
