package com.zhanggb.contacts.app.manager;

import android.content.Context;

/**
 * @author zhanggaobo
 * @since 11/02/2016
 */
public interface AssertDatabasesManager {

    String getAttributionOfNumber(Context context, String number);

    void closeAllDatabase();
}
