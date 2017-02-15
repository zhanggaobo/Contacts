package com.zhanggb.contacts.app.model;

import java.io.Serializable;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class Note implements Serializable{

    private static final long serialVersionUID = 8177395335377270724L;
    private int id;
    private String theme;
    private String createDate;
    private String uploadDate;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
