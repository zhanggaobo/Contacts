package com.zhanggb.contacts.app.manager.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zhanggb.contacts.app.manager.NoteDatabaseManager;
import com.zhanggb.contacts.app.manager.NoteSQLiteOpenHelper;
import com.zhanggb.contacts.app.model.Note;
import com.zhanggb.contacts.app.sqlite.Closure;
import com.zhanggb.contacts.app.sqlite.CursorTemplate;
import com.zhanggb.contacts.app.sqlite.NoteTable;
import com.zhanggb.contacts.app.util.CursorUtils;
import com.zhanggb.contacts.app.util.Log;
import com.zhanggb.contacts.app.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class NoteDatabaseManagerImpl extends NoteDatabaseManager {

    private Context context;
    private NoteSQLiteOpenHelper sqLiteOpenHelper;
    private Log logger = Log.build(getClass());

    public NoteDatabaseManagerImpl(Context context) {
        this.context = context;
        sqLiteOpenHelper = new NoteSQLiteOpenHelper(context);
    }

    @Override
    public List<Note> findNotes() {
        final List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        try {
            final Cursor cur = sqLiteDatabase.query(NoteTable.TABLE_NAME,
                    null, null, null, null, null, NoteTable.NoteTables._ID + " desc", null);
            logger.debug("CursorCount: " + cur.getCount());
            CursorTemplate.each(cur, new Closure<Cursor>() {
                @Override
                public void execute(Cursor input) {
                    Note note = new Note();
                    note.setId(CursorUtils.getInt(input, NoteTable.NoteTables._ID));
                    note.setTheme(CursorUtils.getString(input, NoteTable.NoteTables.THEME));
                    note.setContent(CursorUtils.getString(input, NoteTable.NoteTables.CONTENT));
                    note.setCreateDate(CursorUtils.getString(input, NoteTable.NoteTables.CREATE_TIME));
                    note.setUploadDate(CursorUtils.getString(input, NoteTable.NoteTables.UPDATE_TIME));
                    notes.add(note);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return notes;
        }
        return notes;
    }

    @Override
    public void updateNote(Note note) {
        final ContentValues values = new ContentValues();
        long updateTime = System.currentTimeMillis();
//        if (note.getId() != 0) {
//            values.put(NoteTable.NoteTables._ID, note.getId());
//        }
        values.put(NoteTable.NoteTables.CONTENT, note.getContent());
        values.put(NoteTable.NoteTables.THEME, note.getTheme());
        values.put(NoteTable.NoteTables.UPDATE_TIME, updateTime);
        if (StringUtils.isNotEmpty(note.getCreateDate())) {
            values.put(NoteTable.NoteTables.CREATE_TIME, note.getCreateDate());
        } else {
            values.put(NoteTable.NoteTables.CREATE_TIME, updateTime);
        }

        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        long update = sqLiteDatabase.update(NoteTable.TABLE_NAME, values, NoteTable.NoteTables._ID + "=?", new String[]{note.getId() + ""});
        logger.debug("update: ", update + "");
    }

    @Override
    public void insertNote(Note note) {
        final ContentValues values = new ContentValues();
        long updateTime = System.currentTimeMillis();
        if (note.getId() != 0) {
            values.put(NoteTable.NoteTables._ID, note.getId());
        }
        values.put(NoteTable.NoteTables.CONTENT, note.getContent());
        values.put(NoteTable.NoteTables.THEME, note.getTheme());
        values.put(NoteTable.NoteTables.UPDATE_TIME, updateTime);
        if (StringUtils.isNotEmpty(note.getCreateDate())) {
            values.put(NoteTable.NoteTables.CREATE_TIME, note.getCreateDate());
        } else {
            values.put(NoteTable.NoteTables.CREATE_TIME, updateTime);
        }

        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        long insert = sqLiteDatabase.insert(NoteTable.TABLE_NAME, null, values);
        logger.debug("insert: ", insert + "");
    }

    @Override
    public void deleteNote(String id) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        int delete = sqLiteDatabase.delete(NoteTable.TABLE_NAME, NoteTable.NoteTables._ID + "=?", new String[]{id});
        logger.debug("delete: ", delete + "");
    }
}
