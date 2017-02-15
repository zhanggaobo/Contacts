package com.zhanggb.contacts.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.activity.NoteEditActivity;
import com.zhanggb.contacts.app.manager.CallBack;
import com.zhanggb.contacts.app.model.Note;
import com.zhanggb.contacts.app.util.DateUtil;
import com.zhanggb.contacts.app.util.StringUtils;
import com.zhanggb.contacts.app.view.DialogFactory;
import com.zhanggb.contacts.app.view.LinearItemView;

import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/30/2016
 */
public class NoteListAdapter extends CommonAdapter<Note> {

    private CallBack callBack;

    public NoteListAdapter(Context context, List<Note> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void convert(ViewHolder viewHolder, final Note item) {
        TextView textTheme = viewHolder.getView(R.id.note_theme_text);
        TextView textTime = viewHolder.getView(R.id.note_time_text);
        RelativeLayout relativeNote = viewHolder.getView(R.id.relative_note);
        String time = DateUtil.getTime("yyyy-MM-dd", Long.parseLong(item.getUploadDate()));
        if (StringUtils.isNotEmpty(item.getTheme())) {
            textTheme.setText(item.getTheme());
        } else {
            textTheme.setText(item.getContent());
        }
        textTime.setText(time);
        relativeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteEditActivity.class);
                intent.putExtra("note", item);
                context.startActivity(intent);
            }
        });
        relativeNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createChooseView(item);
                return false;
            }
        });
    }

    private void createChooseView(final Note item) {
        View chooseView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_view, null);
        LinearLayout chooseLinear = (LinearLayout) chooseView.findViewById(R.id.choose_linear);
        ImageView closeImage = (ImageView) chooseView.findViewById(R.id.dialog_close_image);
        final Dialog dialog = DialogFactory.createViewDialog(context, chooseView);
        new LinearItemView(context).builder()
                .setContentText("编辑")
                .setOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, NoteEditActivity.class);
                        intent.putExtra("note", item);
                        context.startActivity(intent);
                    }
                }).appendTo(chooseLinear);
        new LinearItemView(context).builder()
                .setContentText("删除")
                .setOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (callBack != null) {
                            callBack.callBack(item.getId() + "");
                        }
                    }
                }).appendTo(chooseLinear);
        dialog.show();
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
