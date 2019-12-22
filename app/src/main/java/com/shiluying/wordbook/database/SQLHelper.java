package com.shiluying.wordbook.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shiluying.wordbook.enity.*;

import java.util.ArrayList;

public class SQLHelper {
    public ArrayList<Record> queryData(SQLiteDatabase db,String arg) {
        String selection = null;
        String[] selectionArgs=null;
        if (arg != "") {
            selection = "word=?";
            selectionArgs = new String[]{arg};
        }
        ArrayList<Record> recordList = new ArrayList<Record>();
        Cursor cursor;
        cursor = db.query(DBHelper.TABLE_NAME, DBHelper.TABLE_COLUMNS, selection,selectionArgs, null, null, null);
        try {
            while (cursor != null && cursor.moveToNext()) {
                Record record = new Record();
                record.setWord(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD)));
                record.setWordMeaning(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD_MEANING)));
                record.setWordSample(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD_SAMPLE)));
                recordList.add(record);
                Log.i("QUERY", "queryData record = " + record.toString());
            }
        } catch (SQLException e) {
            Log.e("QUERY", "queryData exception", e);
        }
        cursor.close();
        return recordList;
    }
    public void insertData(SQLiteDatabase db,String word,String meaning,String sample){
        ContentValues contentValues = new ContentValues();
        contentValues.put("word",word);
        contentValues.put("wordmeaning",meaning);
        contentValues.put("wordsample",sample);
        db.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.e("INSERT", "INSERT DATA");
    }
    public void deleteData(SQLiteDatabase db,String arg){
        db.delete(DBHelper.TABLE_NAME,"word=?", new String[]{arg});
    }
    public void updateData(SQLiteDatabase db,String word,String wordmeaning,String wordsample){
        ContentValues values;
        values=new ContentValues();
        values.put("word",word);
        db.update(DBHelper.TABLE_NAME,values,"word=?", new String[]{word});
        values=new ContentValues();
        values.put("wordmeaning",wordmeaning);
        db.update(DBHelper.TABLE_NAME,values,"word=?", new String[]{word});
        values=new ContentValues();
        values.put("wordsample",wordsample);
        db.update(DBHelper.TABLE_NAME,values,"word=?", new String[]{word});
    }
    public ArrayList<Record> fuzzyQuery(SQLiteDatabase db,String str){
        String selection = selection = "word";
        ArrayList<Record> recordList = new ArrayList<Record>();
        Cursor cursor;
        cursor = db.query(DBHelper.TABLE_NAME, DBHelper.TABLE_COLUMNS, selection+" LIKE ? ",
                new String[] { "%" + str + "%" }, null, null, null);
        try {
            while (cursor != null && cursor.moveToNext()) {
                Record record = new Record();
                record.setWord(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD)));
                record.setWordMeaning(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD_MEANING)));
                record.setWordSample(cursor.getString(cursor.getColumnIndex(DBHelper.RECORD_WORD_SAMPLE)));
                recordList.add(record);
                Log.i("QUERY", "queryData record = " + record.toString());
            }
        } catch (SQLException e) {
            Log.e("QUERY", "queryData exception", e);
        }
        cursor.close();
        return recordList;
    }
}
