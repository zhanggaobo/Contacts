package com.zhanggb.contacts.app.model;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class Call {

    public String displayName;

    public String fullName; //wangwei

    public String briefName; //ww

    public CallItem lastItem = new CallItem();

    public int callTimes;

//    public long rawContactId;//该通话记录的联系人是否存在,若存在则赋予联系人编号

    public long contactId;//用于photo显示

    public long rawContactId;

    public long photoId;

    @Override
    public String toString() {
        return "displayName: " + displayName + "; contactId: " + contactId + "; rawContactId: " + rawContactId;
    }
}
