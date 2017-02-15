package com.zhanggb.contacts.app.sqlite;

import android.provider.BaseColumns;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class NoteTable {

    public final static String TABLE_NAME = "note_table";

    public final static class NoteTables implements BaseColumns {
        public final static String THEME = "theme";
        public final static String CONTENT = "content";
        public final static String CREATE_TIME = "create_time";
        public final static String UPDATE_TIME = "update_time";
    }
}
