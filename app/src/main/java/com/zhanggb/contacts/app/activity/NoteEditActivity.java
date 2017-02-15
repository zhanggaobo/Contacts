package com.zhanggb.contacts.app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.manager.NoteDatabaseManager;
import com.zhanggb.contacts.app.manager.impl.NoteDatabaseManagerImpl;
import com.zhanggb.contacts.app.model.Note;
import com.zhanggb.contacts.app.util.DateUtil;
import com.zhanggb.contacts.app.util.StringUtils;
import com.zhanggb.contacts.app.view.HeaderView;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class NoteEditActivity extends BaseActivity {

    private HeaderView headerView;
    private EditText editTheme;
    private EditText editText;
    private TextView textTime;

    private NoteDatabaseManager noteDatabaseManager;
    private Note note;

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_edit);
        this.context = this;
        noteDatabaseManager = new NoteDatabaseManagerImpl(context);
        resolveIntent();
        initView();
    }

    private void resolveIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra("note");
        }
    }


    private void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        editTheme = (EditText) findViewById(R.id.edit_theme);
        editText = (EditText) findViewById(R.id.edit_note);
        textTime = (TextView) findViewById(R.id.text_note_time);
        headerView.getMiddleText().setText("记笔记");
        headerView.getLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                finish();
            }
        });
        String time = "";
        if (note != null) {
            time = DateUtil.getTime("yyyy-MM-dd", Long.parseLong(note.getUploadDate()));
            editText.setText(note.getContent());
            editTheme.setText(note.getTheme());
            editTheme.setSelection(note.getTheme().length());
        } else {
            time = DateUtil.getTime("yyyy-MM-dd", System.currentTimeMillis());
        }
        textTime.setText(time);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void saveNote() {
        String content = editText.getText().toString().trim();
        if (StringUtils.isNotEmpty(content)) {
            if (note == null) {
                note = new Note();
                note.setCreateDate(System.currentTimeMillis() + "");
            }
            note.setUploadDate(System.currentTimeMillis() + "");
            note.setTheme(editTheme.getText().toString().trim());
            note.setContent(content);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (note.getId() == 0) {
                        noteDatabaseManager.insertNote(note);
                    } else {
                        noteDatabaseManager.updateNote(note);
                    }
                    return null;
                }
            }.execute();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveNote();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
