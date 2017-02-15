package com.zhanggb.contacts.app.model;

import java.util.Date;

/**
 * @author zhanggaobo
 * @since 10/24/2016
 */
public class CallItem {

    public static enum Type {
        ALL("所有通话"),
        INCOMING("已接来电"),
        MISSED("未接来电"),
        OUTGOING("已拨电话"),
        RECENT_CONTACT("经常联系");
        private String desc;

        Type(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public String id;

    public String number;

    public Date callTime;

    public Type type;

    public int duration;//通话时间(秒)

    public String displayName;

    public int network;


    public boolean isIncoming() {
        return type == CallItem.Type.INCOMING;
    }

    public boolean isOutgoing() {
        return type == Type.OUTGOING;
    }

    public boolean isMissed() {
        return type == CallItem.Type.MISSED;
    }
}
