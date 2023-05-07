package com.example.mylostandfoundapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ID + " INTEGER PRIMARY KEY , " + Util.STATUS + " TEXT, " + Util.NAME + " TEXT, " + Util.PHONE + " TEXT, " + Util.DESC + " TEXT, " + Util.DATE + " TEXT, " + Util.LOC + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    public long insertNew(Item newLostArticle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.STATUS, newLostArticle.Status);
        contentValues.put(Util.NAME, newLostArticle.Name);
        contentValues.put(Util.PHONE, newLostArticle.Phone);
        contentValues.put(Util.DESC, newLostArticle.Description);
        contentValues.put(Util.DATE, newLostArticle.Date);
        contentValues.put(Util.LOC, newLostArticle.Location);
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }
}
