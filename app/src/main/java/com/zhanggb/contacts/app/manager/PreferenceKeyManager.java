package com.zhanggb.contacts.app.manager;


/**
 * @author zhanggaobo
 * @since 12/23/2014
 */
public interface PreferenceKeyManager {

    AccountSettings getAccountSettings();

    FeatureSet getFeatureSet();

    void clear();

    //账户设置
    interface AccountSettings {
        /**
         * 帐号设置
         */
        Key<String> accountId();

        Key<Boolean> guide();

        //上一个版本的版本号
        Key<String> versionPrevious();

        void clear();
    }

    //功能设置
    interface FeatureSet {
        Key<Boolean> dialConfirm();

    }


    interface Key<T> {
        T get();

        void set(T t);
    }

}
