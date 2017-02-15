package com.zhanggb.contacts.app.manager;

import com.zhanggb.contacts.app.model.Note;

import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public abstract class NoteDatabaseManager {

    public abstract List<Note> findNotes();

    public abstract void updateNote(Note note);

    public abstract void insertNote(Note note);

    public abstract void deleteNote(String id);
}
