package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.util.StringUtils;

/**
 * @author zhanggaobo
 * @since 10/27/2016
 */
public class ContactListItemView extends LinearLayout {

    private Context context;
    private View view;
    private LinearLayout contactLinear;
    private ImageView photoImage;
    private TextView nameText;
    private TextView phoneText;

    public ContactListItemView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.contact_list_item_view, this);
        contactLinear = (LinearLayout) findViewById(R.id.contact_item_linear);
        photoImage = (ImageView) findViewById(R.id.photo_image);
        nameText = (TextView) findViewById(R.id.contact_name_text);
        phoneText = (TextView) findViewById(R.id.contact_phone_text);
    }

    public LinearLayout getContactLinear() {
        return contactLinear;
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

    public void setDisplayName(String displayName, String[] keywords) {
        if (null == keywords || keywords.length == 0) {
            setDisplayName(displayName);
        } else {
            int index = -1;
            int start = 0;
            SpannableStringBuilder spannable = new SpannableStringBuilder(displayName);
            for (int i = 0; i < keywords.length; i++) {
                index = StringUtils.indexOf(displayName.toUpperCase(), keywords[i].toUpperCase(), start);
                if (index > -1) {
                    start = index + keywords[i].length();
                    ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.theme));
                    spannable.setSpan(span, index, index + keywords[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            setDisplayName(spannable);
        }
    }

    public void setPhoneNum(String phone, String keywords) {
        if (StringUtils.isEmpty(keywords)) {
            phoneText.setText(phone);
        } else {
            int index = -1;
            int start = 0;
            SpannableStringBuilder spannable = new SpannableStringBuilder(phone);
            index = StringUtils.indexOf(phone.toUpperCase(), keywords.toUpperCase(), start);
            if (index > -1) {
                ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.theme));
                spannable.setSpan(span, index, index + keywords.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            phoneText.setText(spannable);
        }
    }

    public void setDisplayName(SpannableStringBuilder displayName) {
        nameText.setText(displayName);
    }

    public void setDisplayName(String displayName) {
        nameText.setText(displayName);
    }
}
