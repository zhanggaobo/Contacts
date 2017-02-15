package com.zhanggb.contacts.app.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanggaobo
 * @since 11/02/2016
 */
public class DatabasesManager {

    private static String databasePath = "/data/data/%s/databases";

    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();
    private Context context = null;
    private static DatabasesManager mInstance = null;

    public DatabasesManager() {
    }

    public static void initManager(Context context) {
        if (mInstance == null) {
            mInstance = new DatabasesManager(context);
        }
    }

    public static DatabasesManager getManager() {
        return mInstance;
    }

    protected DatabasesManager(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getDatabase(String dbFile, boolean flag, DataBasesCallBack callBack) {
        if (databases.get(dbFile) != null) {
            return (SQLiteDatabase) databases.get(dbFile);
        }
        if (context == null)
            return null;

        String sPath = getDatabaseFilePath();
        String sFile = getDatabaseFile(dbFile);

        File file = new File(sFile);
        if (!flag || !file.exists()) {
            file = new File(sPath);
            if (!file.exists() && !file.mkdirs()) {
                return null;
            }
            if (!copyAssetsToFilesystem(dbFile, sFile)) {
                return null;
            }
            callBack.execute();
        }

        SQLiteDatabase db = SQLiteDatabase.openDatabase(sFile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        if (db != null) {
            databases.put(dbFile, db);
        }
        return db;
    }

    private String getDatabaseFilePath() {
        return String.format(databasePath, context.getApplicationInfo().packageName);
    }

    private String getDatabaseFile(String dbFile) {
        return getDatabaseFilePath() + "/" + dbFile;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des) {
        InputStream iStream = null;
        OutputStream oStream = null;
        try {
            AssetManager am = context.getAssets();
            iStream = am.open(assetsSrc);
            oStream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = iStream.read(buffer)) > 0) {
                oStream.write(buffer, 0, length);
            }
            iStream.close();
            oStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (iStream != null)
                    iStream.close();
                if (oStream != null)
                    oStream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean closeDatabase(String dbFile) {
        if (databases.get(dbFile) != null) {
            SQLiteDatabase db = (SQLiteDatabase) databases.get(dbFile);
            db.close();
            databases.remove(dbFile);
            return true;
        }
        return false;
    }

    public void closeAllDatabase() {
        if (mInstance != null) {
            for (int i = 0; i < mInstance.databases.size(); ++i) {
                if (mInstance.databases.get(i) != null) {
                    mInstance.databases.get(i).close();
                }
            }
            mInstance.databases.clear();
        }
    }

    public interface DataBasesCallBack {
        void execute();
    }
}
