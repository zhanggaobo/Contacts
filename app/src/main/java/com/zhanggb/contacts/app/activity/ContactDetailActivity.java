package com.zhanggb.contacts.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.util.BitmapUtil;
import com.zhanggb.contacts.app.model.Contact;
import com.zhanggb.contacts.app.util.DeviceUtils;
import com.zhanggb.contacts.app.util.IntentFactory;
import com.zhanggb.contacts.app.view.ContactContentItemView;

/**
 * @author zhanggaobo
 * @since 11/11/2016
 */
public class ContactDetailActivity extends BaseActivity {

    private ScrollView scrollContact;
    private LinearLayout linearContact;
    private LinearLayout linearContentContact;
    private LinearLayout linearDetailContact;
    private ImageView contactPhoto;
    private TextView nameText;

    private Contact contact;
    private int mWidth;
    private int mHeight;

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.contact_detail_activity);
        this.context = this;
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;
        resolveIntent();
        initView();
        setupContentView();
    }

    private void resolveIntent() {
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
    }

    private void initView() {
//        scrollContact = (ScrollView) findViewById(R.id.contact_scroll);
        linearContact = (LinearLayout) findViewById(R.id.contact_linear);
        linearContentContact = (LinearLayout) findViewById(R.id.contact_content_linear);
        linearDetailContact = (LinearLayout) findViewById(R.id.linear_contact_detail);
        contactPhoto = (ImageView) findViewById(R.id.image_contact_photo);
        nameText = (TextView) findViewById(R.id.text_contact_name);

        FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) linearContact.getLayoutParams();
        linearParams.height = mHeight - DeviceUtils.getPx(context, 120);
        linearParams.width = mWidth - DeviceUtils.getPx(context, 40);
        linearContact.setLayoutParams(linearParams);

        LinearLayout.LayoutParams linearContentParams = (LinearLayout.LayoutParams) linearContentContact.getLayoutParams();
        if (linearContentParams == null) {
            linearContentParams = new LinearLayout.LayoutParams(mWidth - DeviceUtils.getPx(context, 40), mHeight - DeviceUtils.getPx(context, 120));
        }
        linearContentContact.setLayoutParams(linearContentParams);
    }

    private void setupContentView() {
        if (contact == null) {
            return;
        }
        if (contact.getPhotoId() > 0) {
            BitmapUtil.getInstance().loadBitmap(String.valueOf(contact.getContactId()), contactPhoto, context);
        } else {
            contactPhoto.setImageResource(R.mipmap.default_avatar);
        }
        nameText.setText(contact.getName());
        new ContactContentItemView(context).builder()
                .setContentName(contact.getPhoneNumber())
                .setContentType("手机")
                .setLinearClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(IntentFactory.createCallIntent(contact.getPhoneNumber()));
                    }
                }).appendTo(linearDetailContact, true);
    }
}
