package com.shiluying.wordbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public final static String TABLE_NAME = "record";
    public final static String RECORD_WORD = "word";
    public final static String RECORD_WORD_MEANING = "wordmeaning";
    public final static String RECORD_WORD_SAMPLE = "wordsample";
    public static final String[] TABLE_COLUMNS = {
            RECORD_WORD,
            RECORD_WORD_MEANING,
            RECORD_WORD_SAMPLE
    };

    public DBHelper(Context context) {
        super(context, "Database.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            SQLiteStatement stmt;
            // 涉及多个表和数据用事务保证完整一致
            db.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    RECORD_WORD + " TEXT," +
                    RECORD_WORD_MEANING + " TEXT," +
                    RECORD_WORD_SAMPLE + " TEXT)";
            Log.i("TEST", sql);
            db.execSQL(sql);
            //成功提交
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.i("TEST","初始化数据库失败!!");
        }finally{
            db.endTransaction();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("word","apple");
        contentValues.put("wordmeaning","苹果");
        contentValues.put("wordsample","This is an apple");
        db.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.e("INSERT", "INSERT DATA");
        contentValues.put("word","orange");
        contentValues.put("wordmeaning","橘子");
        contentValues.put("wordsample","This is an orange");
        db.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.e("INSERT", "INSERT DATA");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase("Database.db");
    }

}
