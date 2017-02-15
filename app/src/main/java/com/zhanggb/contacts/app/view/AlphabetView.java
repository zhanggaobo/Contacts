package com.zhanggb.contacts.app.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.sqlite.Closure;
import com.zhanggb.contacts.app.sqlite.Predicate;
import com.zhanggb.contacts.app.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/01/2016
 */
public class AlphabetView extends LinearLayout {

    private List<String> candidateAlphabets;
    private String abc = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private List<String> pinyinList = new ArrayList<String>();
    private TextView currentView;

    public void setCandidateAlphabets(List<String> candidateAlphabets) {
        this.candidateAlphabets = candidateAlphabets;
        getABC();
        redrawAlphabets();
    }


    private void getABC() {
        pinyinList.clear();
        for (char a : abc.toCharArray()) {
            pinyinList.add(String.valueOf(a));
        }
    }

    /**
     * 切换选中的字母高亮显示
     *
     * @param predicate predicate
     * @param closure   closure
     */
    private void toggleHighlight(Predicate<TextView> predicate, Closure<TextView> closure) {
        this.setBackgroundColor(getResources().getColor(R.color.contact_indexer_bg));
        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i);
            if (predicate.eval(tv)) {
                currentView = tv;
                if (closure != null) {
                    closure.execute(tv);
                }
            }
        }
    }

    /**
     * 设置当前字母，当联系人滚动时调用
     *
     * @param alphabet 当前字母
     */
    public void setCurrentAlphabet(final String alphabet) {
        toggleHighlight(new Predicate<TextView>() {
            @Override
            public boolean eval(TextView textView) {
                return StringUtils.equals((String) textView.getTag(), alphabet);
            }
        }, null);
    }


    /**
     * 字母改变后回调
     */
    public interface AlphabetListener {

        void onChange(String current);

        void onUp();
    }

    protected AlphabetListener listener;


    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    notifyAlphabetListenerOnChange(motionEvent);
                } else {
                    notifyAlphabetListenerOnUp();
                }
                return true;
            }
        });
    }

    private void redrawAlphabets() {
        removeAllViews();
        if (pinyinList != null) {
            for (final String current : pinyinList) {
                TextView tv = new TextView(getContext());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                tv.setTextColor(Color.GRAY);

                //为存在的字母修改背景颜色
                if (candidateAlphabets != null) {
                    for (final String current2 : candidateAlphabets) {
                        if (StringUtils.equals(current, current2)) {
                            tv.setTextColor(getResources().getColor(R.color.theme));
                        }
                    }
                }
                tv.setText(current);
                tv.setTag(current);
                tv.setGravity(Gravity.CENTER);
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                params.weight = 1;
                tv.setLayoutParams(params);

                addView(tv);
            }
        }
    }

    private void notifyAlphabetListenerOnUp() {
        this.setBackgroundColor(Color.TRANSPARENT);
        if (currentView != null) {
            currentView.setBackgroundResource(android.R.color.transparent);
        }
        if (listener != null) {
            listener.onUp();
        }
    }

    private void notifyAlphabetListenerOnChange(final MotionEvent event) {
        toggleHighlight(new Predicate<TextView>() {
                            @Override
                            public boolean eval(TextView textView) {
                                Rect rect = new Rect();
                                textView.getHitRect(rect);
                                return rect.contains((int) event.getX(), (int) event.getY());
                            }
                        }, new Closure<TextView>() {
                            @Override
                            public void execute(TextView textView) {
                                if (listener != null) {
                                    String tag = (String) textView.getTag();
                                    listener.onChange(tag);
                                }
                            }
                        }
        );
    }

    public void setAlphabetListener(AlphabetListener listener) {
        this.listener = listener;
    }
}
