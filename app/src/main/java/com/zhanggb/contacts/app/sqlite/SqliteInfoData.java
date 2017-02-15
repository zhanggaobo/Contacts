package com.zhanggb.contacts.app.sqlite;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public interface SqliteInfoData {
    interface Environment {
        String DATABASE_NAME = "note_info.db";
        int DATABASE_VERSION = 2016113000;
    }

    interface INoteTable {
        interface Sql {
            String CREATE_NOTE_TABLE =
                    "CREATE TABLE IF NOT EXISTS " + NoteTable.TABLE_NAME +
                            "(" + NoteTable.NoteTables._ID + " INTEGER PRIMARY KEY," +
                            NoteTable.NoteTables.CONTENT + " TEXT," +
                            NoteTable.NoteTables.THEME + " TEXT," +
                            NoteTable.NoteTables.UPDATE_TIME + " TEXT," +
                            NoteTable.NoteTables.CREATE_TIME + " TEXT" +
                            ")";

            String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS " + NoteTable.TABLE_NAME;
            String DELETE_NOTE_TABLE = "DELETE FROM " + NoteTable.TABLE_NAME;
        }
    }
}
