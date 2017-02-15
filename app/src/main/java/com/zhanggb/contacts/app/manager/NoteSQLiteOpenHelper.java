package com.zhanggb.contacts.app.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.zhanggb.contacts.app.sqlite.SqliteInfoData;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class NoteSQLiteOpenHelper extends SQLiteOpenHelper {

    public NoteSQLiteOpenHelper(Context context) {
        super(context, SqliteInfoData.Environment.DATABASE_NAME, null, SqliteInfoData.Environment.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqliteInfoData.INoteTable.Sql.CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(SqliteInfoData.INoteTable.Sql.DROP_NOTE_TABLE);
            onCreate(db);
        }
    }
}
