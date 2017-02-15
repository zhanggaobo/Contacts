package com.zhanggb.contacts.app.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import com.zhanggb.contacts.app.IConstant;
import com.zhanggb.contacts.app.model.Call;
import com.zhanggb.contacts.app.model.CallItem;
import com.zhanggb.contacts.app.model.Contact;
import com.zhanggb.contacts.app.sqlite.Closure;
import com.zhanggb.contacts.app.sqlite.CursorTemplate;
import com.zhanggb.contacts.app.util.CharsUtils;
import com.zhanggb.contacts.app.util.CursorUtils;
import com.zhanggb.contacts.app.util.StringUtils;
import com.zhanggb.contacts.app.util.pinyin.PinyinGenerator;
import com.zhanggb.contacts.app.util.pinyin.PyCode;

import java.util.*;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class ContactCacheManager {

    private final List<Contact> contactsList = new ArrayList<Contact>();
    private final List<Call> callLists = new ArrayList<Call>();
    private static final ContactCacheManager INSTANCE = new ContactCacheManager();

    public static ContactCacheManager getInstance() {
        return INSTANCE;
    }

    private ContactCacheManager() {
    }

    public List<Call> findCall(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        List<Call> callList = new ArrayList<Call>();
        try {
            cursor = contentResolver.query(
                    CallLog.Calls.CONTENT_URI, null, null, null,
                    CallLog.Calls.DATE + " desc");
            if (cursor == null)
                return null;
            while (cursor.moveToNext()) {
                final CallItem item = new CallItem();
                String displayName = CursorUtils.getString(cursor, CallLog.Calls.CACHED_NAME);
                String phoneNumber = CursorUtils.getString(cursor, CallLog.Calls.NUMBER);
                displayName = StringUtils.isBlank(displayName) ? phoneNumber : displayName;

                item.id = CursorUtils.getString(cursor, CallLog.Calls._ID);
                item.number = phoneNumber;
                item.callTime = new Date(CursorUtils.getLong(cursor, CallLog.Calls.DATE));
                item.type = getCallType(CursorUtils.getInt(cursor, CallLog.Calls.TYPE));
                item.duration = CursorUtils.getInt(cursor, CallLog.Calls.DURATION);
                item.displayName = displayName;
                item.network = CursorUtils.getInt(cursor, IConstant.Extra.CALL_NETWORK);
                Contact contact = getContactCacheByPhone(phoneNumber);
                final Call call = new Call();
                call.displayName = displayName;
                call.lastItem = item;
                if (contact != null) {
                    call.contactId = contact.getContactId();
                    call.rawContactId = contact.getRawContactId();
                    call.photoId = contact.getPhotoId();
                }
                int num = CursorUtils.getInt(cursor, IConstant.Extra.CALL_TIMES);
                call.callTimes = num == 0 ? 1 : num;
                callList.add(call);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        callLists.clear();
        callLists.addAll(callList);
        return callList;
    }

    public List<Call> searchCall(String keyword) {
        List<Call> callList = new ArrayList<Call>();
        for (Call call : callLists) {
            if (StringUtils.indexOf(call.lastItem.number, keyword) > -1) {
                callList.add(call);
            }
        }
        return callList;
    }

    public List<Contact> findContacts(Context context) {
        final List<Contact> contacts = new ArrayList<Contact>();
        final Map<Long, String> idAndNames = new HashMap<Long, String>();
        final Map<Long, Long> idAndContactIds = new HashMap<Long, Long>();
        String selection = ContactsContract.RawContacts.DELETED + "=0 AND " + ContactsContract.Contacts.DISPLAY_NAME + "<>''";
        Cursor rawContactCursor = null;
        try {
            rawContactCursor = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[]{
                    ContactsContract.RawContacts._ID,
                    ContactsContract.RawContacts.CONTACT_ID,
                    ContactsContract.Contacts.DISPLAY_NAME
            }, selection, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CursorTemplate.each(rawContactCursor, new Closure<Cursor>() {
            @Override
            public void execute(Cursor input) {
                Long rawContactId = CursorUtils.getLong(input, ContactsContract.RawContacts._ID);
                Long contactId = CursorUtils.getLong(input, ContactsContract.RawContacts.CONTACT_ID);
                String displayName = CursorUtils.getString(input, ContactsContract.Contacts.DISPLAY_NAME);
                idAndNames.put(rawContactId, displayName);
                idAndContactIds.put(rawContactId, contactId);
            }
        });

        List<Long> ids = new ArrayList<Long>();
        for (long id : idAndNames.keySet()) {
            ids.add(id);
        }
        StringBuilder sql = new StringBuilder();
        if (ids.size() == 0) {
            return contacts;
        }
        sql.append(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " in (");
        for (int i = 0; i < ids.size(); i++) {
            if (i == ids.size() - 1) {
                sql.append(ids.get(i) + ")");
            } else {
                sql.append(ids.get(i) + ",");
            }
        }
        Cursor numberCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                sql.toString(), null, null);
        CursorTemplate.each(numberCursor, new Closure<Cursor>() {
            int i = 0;

            @Override
            public void execute(Cursor input) {
                long rawContactId = CursorUtils.getLong(input, ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID);
                long photoId = CursorUtils.getLong(input, ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
                String phone = CursorUtils.getString(input, ContactsContract.CommonDataKinds.Phone.NUMBER);
                Contact contact = new Contact();
                contact.setRawContactId((int) rawContactId);
                contact.setContactId(idAndContactIds.get(rawContactId).intValue());
                contact.setPhotoId((int) photoId);
                contact.setPhoneNumber(phone);
                contact.setName(idAndNames.get(rawContactId));
                contact.setSortKey(PinyinGenerator.generate(contact.getName()));
                contacts.add(contact);
//                contactIndexs.put(i, rawContactId);
//                contactCacheManager.putContactItem(rawContactId, contact);
                ++i;
            }
        });
//        contactCaches = contacts;
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                if (lhs.getSortKey() == null || rhs.getSortKey() == null) {
                    return -1;
                }
                return lhs.getSortKey().compareTo(rhs.getSortKey());
            }
        });
        contactsList.clear();
        contactsList.addAll(contacts);
        return contacts;
    }

    public List<Contact> searchContacts(String keyword) {
        List<Contact> searchContacts = new ArrayList<Contact>();
        boolean keywordIsNumber = false;
        if (CharsUtils.isInteger(keyword)) {
            keywordIsNumber = true;
        }
        for (Contact contact : contactsList) {
            parserDisplayNamePinyins(contact);
            contact.setMatchs(null);
            contact.setMatchNumber(null);
            if (StringUtils.indexOf(contact.getName().toUpperCase(), keyword) > -1) {
                contact.setMatchs(new String[]{keyword});
                searchContacts.add(contact);
            } else if (keywordIsNumber) {
                if (StringUtils.indexOf(contact.getPhoneNumber().toUpperCase(), keyword) > -1) {
                    contact.setMatchNumber(keyword);
                    searchContacts.add(contact);
                }
            } else {
                String[] matchs = CharsUtils.getMatchLetters(contact.getSortKey(), keyword);
                if (null != matchs) {
                    contact.setMatchs(matchs);
                    searchContacts.add(contact);
                } else {
                    for (String sortKey : contact.getSortKeys()) {
                        if (!StringUtils.equals(contact.getSortKey(), sortKey)) {
                            matchs = CharsUtils.getMatchLetters(sortKey, keyword);
                            if (null != matchs) {
                                contact.setMatchs(matchs);
                                searchContacts.add(contact);
                            }
                        }
                    }
                }
            }
        }
        return searchContacts;
    }

    private Contact getContactCacheByPhone(String phone) {
        for (Contact contact : contactsList) {
            if (StringUtils.equals(phone, contact.getPhoneNumber())) {
                return contact;
            }
        }
        return null;
    }

    protected CallItem.Type getCallType(int type) {
        switch (type) {
            case CallLog.Calls.MISSED_TYPE:
                return CallItem.Type.MISSED;
            case CallLog.Calls.INCOMING_TYPE:
                return CallItem.Type.INCOMING;
            case CallLog.Calls.OUTGOING_TYPE:
                return CallItem.Type.OUTGOING;
        }
        return CallItem.Type.INCOMING;
    }

    private void parserDisplayNamePinyins(Contact item) {
        if (StringUtils.isNotBlank(item.getName()) && StringUtils.isNotBlank(item.getSortKey())) {
            item.pushToSortKeys(item.getSortKey());
            for (char c : item.getName().toCharArray()) {
                String letter = String.valueOf(c);
                if (CharsUtils.isChinese(letter)) {
                    String pinyin = PyCode.polyCodes.get(letter);
                    if (StringUtils.isNotBlank(pinyin)) {
                        String[] pinyins = StringUtils.split(pinyin);
                        for (String p : pinyins) {
                            makeAllSortKeys(item, p, letter);
                        }
                    }
                }
            }
        }
    }

    private void makeAllSortKeys(Contact item, String pinyin, String letter) {
        Set<String> sortKeys = new HashSet<String>();
        for (String sk : item.getSortKeys()) {
            String[] sortKey = StringUtils.split(sk);
            sortKey[getPinyinIndex(sortKey, letter)] = pinyin;
            String py = makeSortKey(sortKey);
            sortKeys.add(py);
        }

        for (String py : sortKeys) {
            item.pushToSortKeys(py);
        }
    }

    private int getPinyinIndex(String[] sortKey, String letter) {
        for (int i = 0; i < sortKey.length; i++) {
            if (StringUtils.equals(sortKey[i], letter)) {
                return i - 1;
            }
        }
        return -1;
    }

    private String makeSortKey(String[] sortKey) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sortKey.length; i++) {
            if (0 != i) {
                builder.append(" ");
            }
            builder.append(sortKey[i]);
        }
        return builder.toString();
    }
}
