package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;

/**
 * @author zhanggaobo
 * @since 12/05/2016
 */
public class LinearItemView extends LinearLayout {

    private View view;
    private TextView textView;

    public LinearItemView(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.linear_item_view, this);
        textView = (TextView) findViewById(R.id.text_choose);
    }

    public LinearItemBuilder builder() {
        return new LinearItemBuilder();
    }

    public class LinearItemBuilder {

        public LinearItemBuilder setContentText(String content) {
            textView.setText(content);
            return this;
        }

        public LinearItemBuilder setOnClick(OnClickListener onClick) {
            view.setOnClickListener(onClick);
            return this;
        }

        public void appendTo(ViewGroup parent) {
            appendTo(parent, true);
        }

        public void appendTo(ViewGroup parent, boolean separator) {
            if (separator) {
                parent.addView(createSeparator());
            }
            parent.addView(view);
        }

        public ImageView createSeparator() {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1));
            image.setBackgroundColor(getResources().getColor(R.color.line_bg));
            return image;
        }
    }
}
