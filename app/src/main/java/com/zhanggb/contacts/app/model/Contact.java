package com.zhanggb.contacts.app.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class Contact implements Serializable{

    private static final long serialVersionUID = -1655107892915984630L;
    private int rawContactId;
    private int contactId;
    private int photoId;
    private String name;
    private String phoneNumber;

    private Bitmap photo;

    private String sortKey;
    private String[] matchs;
    private String matchNumber;
    private Set<String> sortKeys = new HashSet<String>();

    public Set<String> getSortKeys() {
        return sortKeys;
    }

    public void pushToSortKeys(String sk) {
        if (!sortKeys.contains(sk)) {
            sortKeys.add(sk);
        }
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String[] getMatchs() {
        return matchs;
    }

    public void setMatchs(String[] matchs) {
        this.matchs = matchs;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(int rawContactId) {
        this.rawContactId = rawContactId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "name: " + name + "; phoneNumber: " + phoneNumber
                + "; rawContactId: " + rawContactId + ";  contactId: " + contactId+ ";  photoId: " + photoId;
    }
}
