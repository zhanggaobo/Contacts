package com.zhanggb.contacts.app.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.zhanggb.contacts.app.util.ArrayUtils;
import com.zhanggb.contacts.app.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaobo
 * @since 12/06/2014
 */
public class PreferenceKeySupport {

    public static final String SPLIT_TAG = "|";

    protected SharedPreferences sharedPreferences;
    protected Context context;

    public PreferenceKeySupport(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("contacts.xml", Context.MODE_PRIVATE);
    }

    protected PreferenceKeyManager.Key<Boolean> getBooleanPreferenceKey(int resourceId) {
        return getBooleanPreferenceKey(context.getString(resourceId), false);
    }

    protected PreferenceKeyManager.Key<Boolean> getBooleanPreferenceKey(int resource, final boolean defaultValue) {
        return getBooleanPreferenceKey(context.getString(resource), defaultValue);
    }

    protected PreferenceKeyManager.Key<Boolean> getBooleanPreferenceKey(String resource) {
        return getBooleanPreferenceKey(resource, false);
    }

    protected PreferenceKeyManager.Key<Boolean> getBooleanPreferenceKey(final String resource, final boolean defaultValue) {
        return new PreferenceKeyManager.Key<Boolean>() {
            @Override
            public Boolean get() {
                return sharedPreferences.getBoolean(resource, defaultValue);
            }

            @Override
            public void set(Boolean value) {
                sharedPreferences.edit().putBoolean(resource, value).commit();
            }
        };
    }

    protected PreferenceKeyManager.Key<Long> getLongPreferenceKey(final int resource, final long defaultValue) {
        return new PreferenceKeyManager.Key<Long>() {
            @Override
            public Long get() {
                return sharedPreferences.getLong(context.getString(resource), defaultValue);
            }

            @Override
            public void set(Long value) {
                sharedPreferences.edit().putLong(context.getString(resource), value).commit();

            }
        };
    }

    protected PreferenceKeyManager.Key<String> getStringPreferenceKey(int resource) {
        return getStringPreferenceKey(context.getString(resource), StringUtils.EMPTY);
    }

    protected PreferenceKeyManager.Key<String> getStringPreferenceKey(String resource) {
        return getStringPreferenceKey(resource, StringUtils.EMPTY);
    }

    protected PreferenceKeyManager.Key<String> getStringPreferenceKey(final String resource, final String defaultValue) {
        return new PreferenceKeyManager.Key<String>() {
            @Override
            public String get() {
                return sharedPreferences.getString(resource, defaultValue);
            }

            @Override
            public void set(String value) {
                sharedPreferences.edit().putString(resource, value).commit();
            }
        };
    }

    protected PreferenceKeyManager.Key<Integer> getIntegerPreferenceKey(int resource) {
        return getIntegerPreferenceKey(context.getString(resource), 0);
    }

    protected PreferenceKeyManager.Key<Integer> getIntegerPreferenceKey(int resource, int defaultValue) {
        return getIntegerPreferenceKey(context.getString(resource), defaultValue);
    }

    protected PreferenceKeyManager.Key<Integer> getIntegerPreferenceKey(final String resource, final int defaultValue) {
        return new PreferenceKeyManager.Key<Integer>() {
            @Override
            public Integer get() {
                return sharedPreferences.getInt(resource, defaultValue);
            }

            @Override
            public void set(Integer integer) {
                sharedPreferences.edit().putInt(resource, integer).commit();
            }
        };
    }

    protected PreferenceKeyManager.Key<Long[]> getLongArrayPreferenceKey(final int resource) {
        return getLongArrayPreferenceKey(context.getString(resource));
    }

    protected PreferenceKeyManager.Key<Long[]> getLongArrayPreferenceKey(final String resource) {
        return new PreferenceKeyManager.Key<Long[]>() {
            @Override
            public Long[] get() {
                String json = sharedPreferences.getString(resource, null);
                if (StringUtils.isEmpty(json)) {
                    return null;
                }
                List<Long> ids = new ArrayList<Long>();
                String[] arr = StringUtils.split(json, SPLIT_TAG);
                for (String s : arr) {
                    ids.add(Long.valueOf(s));
                }
                return ArrayUtils.toArray(ids, Long.class);
            }

            @Override
            public void set(Long[] longs) {
                sharedPreferences.edit().putString(resource, StringUtils.join(longs, SPLIT_TAG)).commit();
            }
        };
    }

    protected PreferenceKeyManager.Key<String[]> getStringArrayPreferenceKey(final int resource) {
        return getStringArrayPreferenceKey(context.getString(resource));
    }

    protected PreferenceKeyManager.Key<String[]> getStringArrayPreferenceKey(final String resource) {
        return new PreferenceKeyManager.Key<String[]>() {
            @Override
            public String[] get() {
                String json = sharedPreferences.getString(resource, null);
                if (StringUtils.isEmpty(json)) {
                    return null;
                }
                return StringUtils.split(json, SPLIT_TAG);
            }

            @Override
            public void set(String[] strings) {
                sharedPreferences.edit().putString(resource, StringUtils.join(strings, SPLIT_TAG)).commit();
            }
        };
    }
}
