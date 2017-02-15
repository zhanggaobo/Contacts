package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class HeaderView extends RelativeLayout {

    private Context context;
    private View view;
    private ImageView leftImage;
    private ImageView rightImage;
    private TextView rightText;
    private TextView middleText;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.header_view, this);
        leftImage = (ImageView) findViewById(R.id.header_left_image);
        rightImage = (ImageView) findViewById(R.id.header_right_image);
        rightText = (TextView) findViewById(R.id.header_right_view);
        middleText = (TextView) findViewById(R.id.header_middle_text);
    }

    public ImageView getLeftImage() {
        return leftImage;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public TextView getRightText() {
        return rightText;
    }

    public TextView getMiddleText() {
        return middleText;
    }
}
