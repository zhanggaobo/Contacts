package com.zhanggb.contacts.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zhanggb.contacts.app.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private HomeCallFragment callFragment;
    private HomeContactFragment contactFragment;
    private HomeMessageFragment messageFragment;

    private RelativeLayout tabRel1;
    private RelativeLayout tabRel2;
    private RelativeLayout tabRel3;
    private ImageView tabImage1;
    private ImageView tabImage2;
    private ImageView tabImage3;
    private TextView tabText1;
    private TextView tabText2;
    private TextView tabText3;

    public List<Fragment> fragments = new ArrayList<Fragment>();

    private int pageCount = 0;
    private int currentTab = 0;
    private long mExitTime;

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        this.context = this;
        initView();
//        startActivity(new Intent(context, TestActivity.class));
    }

    private void initView() {
        callFragment = new HomeCallFragment();
        contactFragment = new HomeContactFragment();
        messageFragment = new HomeMessageFragment();

        fragments.add(callFragment);
        fragments.add(contactFragment);
        fragments.add(messageFragment);
        tabRel1 = (RelativeLayout) findViewById(R.id.home_tab_rel1);
        tabRel2 = (RelativeLayout) findViewById(R.id.home_tab_rel2);
        tabRel3 = (RelativeLayout) findViewById(R.id.home_tab_rel3);
        tabImage1 = (ImageView) findViewById(R.id.home_tab_image1);
        tabImage2 = (ImageView) findViewById(R.id.home_tab_image2);
        tabImage3 = (ImageView) findViewById(R.id.home_tab_image3);
        tabText1 = (TextView) findViewById(R.id.home_tab_text1);
        tabText2 = (TextView) findViewById(R.id.home_tab_text2);
        tabText3 = (TextView) findViewById(R.id.home_tab_text3);

        setTabSelection(1);

        tabRel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageCount == 0) {
                } else {
                    setTabSelection(0);
                }
            }
        });
        tabRel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageCount == 1) {
                } else {
                    setTabSelection(1);
                }
            }
        });
        tabRel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageCount == 2) {
                } else {
                    setTabSelection(2);
                }
            }
        });
    }


    /**
     * @param index
     */
    private void setTabSelection(int index) {

        switch (index) {
            case 0:
                pageCount = 0;
//                tabImage1.setBackgroundResource(R.drawable.icon_home_course_focus);
//                tabImage2.setBackgroundResource(R.drawable.icon_home_course_add);
//                tabImage3.setBackgroundResource(R.drawable.icon_home_square);
                tabText1.setTextColor(getResources().getColor(R.color.theme));
                tabText2.setTextColor(getResources().getColor(R.color.text_bg));
                tabText3.setTextColor(getResources().getColor(R.color.text_bg));
                break;
            case 1:
                pageCount = 1;
                tabText1.setTextColor(getResources().getColor(R.color.text_bg));
                tabText2.setTextColor(getResources().getColor(R.color.theme));
                tabText3.setTextColor(getResources().getColor(R.color.text_bg));
                break;
            case 2:
                pageCount = 2;
                tabText1.setTextColor(getResources().getColor(R.color.text_bg));
                tabText2.setTextColor(getResources().getColor(R.color.text_bg));
                tabText3.setTextColor(getResources().getColor(R.color.theme));
                break;
        }
        Fragment fragment = fragments.get(index);
        FragmentTransaction ft = obtainFragmentTransaction(index);
        getCurrentFragment().onPause();
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            ft.add(R.id.home_content_frame, fragment);
        }
        showTab(index);
        ft.commit();
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    /**
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        return ft;
    }

    /**
     * @param idx
     */
    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 5000) {
                Toast.makeText(context, "再按一次返回键将退出随记", Toast.LENGTH_SHORT)
                        .show();
                mExitTime = System.currentTimeMillis();
            } else {
                mExitTime = 0;
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
