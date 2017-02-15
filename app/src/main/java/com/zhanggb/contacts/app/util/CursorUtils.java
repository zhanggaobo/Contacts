package com.zhanggb.contacts.app.util;

import android.database.Cursor;

/**
 * @author zhanggaobo
 * @since 10/24/2016
 */
public class CursorUtils {

    public static String getString(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getString(cursor.getColumnIndexOrThrow(colName));
        }
        return "";
    }

    public static int getInt(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getInt(cursor.getColumnIndexOrThrow(colName));
        }
        return 0;
    }

    public static long getLong(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getLong(cursor.getColumnIndexOrThrow(colName));
        }
        return 0;
    }

    public static boolean getBoolean(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getInt(cursor.getColumnIndexOrThrow(colName)) > 0;
        }
        return false;
    }

    public static float getFloat(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getFloat(cursor.getColumnIndexOrThrow(colName));
        }
        return 0;
    }

    public static double getDouble(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getDouble(cursor.getColumnIndexOrThrow(colName));
        }
        return 0;
    }

    public static byte[] getBlob(Cursor cursor, String colName) {
        if (cursor.getColumnIndex(colName) >= 0) {
            return cursor.getBlob(cursor.getColumnIndexOrThrow(colName));
        }
        return null;
    }
}
