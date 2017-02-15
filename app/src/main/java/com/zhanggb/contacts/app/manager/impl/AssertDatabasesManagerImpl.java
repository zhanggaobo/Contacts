package com.zhanggb.contacts.app.manager.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zhanggb.contacts.app.IConstant;
import com.zhanggb.contacts.app.manager.AssertDatabasesManager;
import com.zhanggb.contacts.app.manager.DatabasesManager;
import com.zhanggb.contacts.app.model.AttributionItem;
import com.zhanggb.contacts.app.sqlite.Closure;
import com.zhanggb.contacts.app.sqlite.CursorTemplate;
import com.zhanggb.contacts.app.util.CursorUtils;
import com.zhanggb.contacts.app.util.StringUtils;

/**
 * @author zhanggaobo
 * @since 11/02/2016
 */
public class AssertDatabasesManagerImpl extends DatabasesManager implements AssertDatabasesManager {


    private SQLiteDatabase database_att;

    public AssertDatabasesManagerImpl() {
    }

    public AssertDatabasesManagerImpl(Context context) {
        super(context);
    }

    @Override
    public String getAttributionOfNumber(Context context, String number) {
        AttributionItem attributionItem = new AttributionItem();
        if (StringUtils.startsWith(number, "0")) {
            if (number.length() >= 3) {
                String city = AttributionItem.findCityByAreaCode(StringUtils.substring(number, 0, 3));
                if (city != null) {
                    return city;
                } else {
                    if (number.length() > 3) {
                        city = AttributionItem.findCityByAreaCode(StringUtils.substring(number, 0, 4));
                        return city == null ? "" : city;
                    }
                }
            }
        } else if (StringUtils.isNotEmpty(number) && number.length() >= 7) {
            attributionItem = getMobilePhoneAtt(context, number);
        }
        return attributionItem.getCity();
    }

    public AttributionItem getMobilePhoneAtt(Context context, String number) {
        if (StringUtils.startsWith(number, "+86")) {
            number = number.substring(3);
        }
        if (number.length() >= 7) {
            number = (StringUtils.substring(number, 0, 7));
        } else {
            return new AttributionItem();
        }
        final AttributionItem attributionItem = new AttributionItem();
        DatabasesManager.initManager(context);
        DatabasesManager mg = DatabasesManager.getManager();
        if (database_att == null) {
            database_att = mg.getDatabase("attributions.db", false
                    , new DatabasesManager.DataBasesCallBack() {
                @Override
                public void execute() {
                }
            });
        }
        try {
            final Cursor cur = database_att.query(IConstant.Attribution.ATTRIBUTION_TABLE_NAME,
                    new String[]{IConstant.Attribution.NUMBER,
                            IConstant.Attribution.AREA_CODE}, IConstant.Attribution.NUMBER + "=?", new String[]{number}, null, null, null);
            CursorTemplate.one(cur, new Closure<Cursor>() {
                @Override
                public void execute(Cursor input) {
                    attributionItem.setNumber(CursorUtils.getString(input, IConstant.Attribution.NUMBER));
                    String areaCode = CursorUtils.getString(input, IConstant.Attribution.AREA_CODE);
                    attributionItem.setAreaCode(areaCode);
                    attributionItem.setCity(AttributionItem.findCityByAreaCode(areaCode));
                }
            });
        } catch (Exception e) {
            return new AttributionItem();
        }
        return attributionItem;
    }

}
