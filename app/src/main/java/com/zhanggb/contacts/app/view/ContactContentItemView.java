package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.util.DeviceUtils;

/**
 * @author zhanggaobo
 * @since 11/14/2016
 */
public class ContactContentItemView extends RelativeLayout {

    private Context context;
    private View view;
    private LinearLayout linearContent;
    private TextView textName;
    private TextView textType;

    public ContactContentItemView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.contact_content_item_view, this);
        linearContent = (LinearLayout) findViewById(R.id.linear_content);
        textName = (TextView) findViewById(R.id.text_name);
        textType = (TextView) findViewById(R.id.text_type);
    }

    public LinearLayout getLinearContent() {
        return linearContent;
    }

    public TextView getTextName() {
        return textName;
    }

    public TextView getTextType() {
        return textType;
    }

    public ContactContentBuilder builder() {
        return new ContactContentBuilder();
    }

    public class ContactContentBuilder {

        public ContactContentBuilder setContentName(String contentName) {
            textName.setText(contentName);
            return this;
        }

        public ContactContentBuilder setContentType(String contentType) {
            textType.setText(contentType);
            return this;
        }

        public ContactContentBuilder setLinearClick(OnClickListener onClickListener) {
            linearContent.setOnClickListener(onClickListener);
            return this;
        }

        public void appendTo(ViewGroup parent) {
            parent.addView(view);
        }

        public void appendTo(ViewGroup parent, boolean separator) {
            parent.addView(view);
            if (separator) {
                parent.addView(createSeparator());
            }
        }

        public ImageView createSeparator() {
            ImageView image = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(DeviceUtils.getPx(context, 10), 0, DeviceUtils.getPx(context, 10), 0);
            image.setLayoutParams(layoutParams);
            image.setBackgroundColor(getResources().getColor(R.color.line_bg));
            return image;
        }
    }
}
