package com.zhanggb.contacts.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.zhanggb.contacts.app.R;

/**
 * @author zhanggaobo
 * @since 12/09/2016
 */
public class TestActivity extends BaseActivity {
    private AlertDialog alertDialog;

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        testDialog();
    }

    private void testDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("AlertDialog")
                .setMessage("AlertDialogAlertDialog")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                }).create();
        alertDialog.show();
    }
}
