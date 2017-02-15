package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;

/**
 * @author zhanggaobo
 * @since 10/25/2016
 */
public class CallListItemView extends LinearLayout {

    private Context context;
    private View view;
    private LinearLayout callLinear;
    private ImageView photoImage;
    private ImageView typeImage;
    private TextView nameText;
    private TextView phoneText;
    private TextView timeText;
    private TextView durationText;
    private TextView attrText;

    public CallListItemView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.call_list_item_view, this);
        callLinear = (LinearLayout) findViewById(R.id.call_linear);
        photoImage = (ImageView) findViewById(R.id.photo_image);
        typeImage = (ImageView) findViewById(R.id.call_type_image);
        nameText = (TextView) findViewById(R.id.call_name_text);
        phoneText = (TextView) findViewById(R.id.call_phone_text);
        timeText = (TextView) findViewById(R.id.call_time_text);
        durationText = (TextView) findViewById(R.id.call_duration_text);
        attrText = (TextView) findViewById(R.id.call_att_text);
    }

    public LinearLayout getCallLinear() {
        return callLinear;
    }

    public ImageView getPhotoImage() {
        return photoImage;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getPhoneText() {
        return phoneText;
    }

    public TextView getTimeText() {
        return timeText;
    }

    public TextView getAttrText() {
        return attrText;
    }

    public ImageView getTypeImage() {
        return typeImage;
    }

    public TextView getDurationText() {
        return durationText;
    }
}
