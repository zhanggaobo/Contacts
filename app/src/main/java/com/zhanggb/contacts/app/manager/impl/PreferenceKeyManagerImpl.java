package com.zhanggb.contacts.app.manager.impl;


import android.content.Context;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.manager.PreferenceKeyManager;
import com.zhanggb.contacts.app.manager.PreferenceKeySupport;

/**
 * @author zhanggaobo
 * @since 04/03/2015
 */
public class PreferenceKeyManagerImpl extends PreferenceKeySupport implements PreferenceKeyManager {

    public PreferenceKeyManagerImpl(Context context) {
        super(context);
    }

    @Override
    public AccountSettings getAccountSettings() {
        return new AccountSettings() {

            @Override
            public Key<String> accountId() {
                return getStringPreferenceKey(R.string.KEY_ACCOUNT_AID);
            }

            @Override
            public Key<Boolean> guide() {
                return getBooleanPreferenceKey(R.string.KEY_ACCOUNT_GUIDE);
            }

            @Override
            public Key<String> versionPrevious() {
                return null;
            }

            @Override
            public void clear() {
                accountId().set(null);
            }
        };
    }


    @Override
    public FeatureSet getFeatureSet() {
        return new FeatureSet() {
            @Override
            public Key<Boolean> dialConfirm() {
                return getBooleanPreferenceKey(R.string.KEY_SETTING_DIALCONFIRM, true);
            }
        };
    }

    @Override
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }
}
