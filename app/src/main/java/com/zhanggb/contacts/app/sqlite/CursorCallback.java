package com.zhanggb.contacts.app.sqlite;

import android.database.Cursor;

/**
 * @author snowway
 * @since 2010-4-22
 */
public interface CursorCallback {

    Object VETO = new Object();

    public Object doInCursor(Cursor cursor);
}
