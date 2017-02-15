package com.zhanggb.contacts.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import com.zhanggb.contacts.app.R;


/**
 * @author zhanggaobo
 * @since 12/02/2014
 */
public class DialogFactory {

    /**
     * 确认对话框
     *
     * @param context                  Context
     * @param message                  提示信息
     * @param positiveOnClickListener  确定按钮按下事件
     * @param negativeOnClicklListener 取消按钮按下事件
     * @param positiveTitle            positive title
     * @param negativeTitle            negative title
     * @return AlertDialog
     */
    public static Dialog createConfirmDialog(
            Context context, String title, String message,
            DialogInterface.OnClickListener positiveOnClickListener, String positiveTitle,
            DialogInterface.OnClickListener negativeOnClicklListener, String negativeTitle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveTitle, positiveOnClickListener)
                    .setNegativeButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        } else {
            return new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(positiveTitle, positiveOnClickListener)
                    .setPositiveButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        }
    }


    public static Dialog createConfirmDialog(
            Context context, String title, View view,
            DialogInterface.OnClickListener positiveOnClickListener,
            DialogInterface.OnClickListener nativeListener
    ) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setView(view)
                    .setPositiveButton(context.getString(R.string.ok), positiveOnClickListener)
                    .setNegativeButton(context.getString(R.string.cancel), nativeListener)
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        } else {
            return new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setView(view)
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.ok), positiveOnClickListener)
                    .setPositiveButton(context.getString(R.string.cancel), nativeListener)
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        }

    }

    public static Dialog createViewDialog(Context context, View view) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.shape_dialog_bg);
        return dialog;
    }

    public static Dialog createExitViewDialog(Context context, View view, int res) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        if (res != 0) {
            window.setBackgroundDrawableResource(res);
        } else {
            window.setBackgroundDrawable(new BitmapDrawable());
        }
        return dialog;
    }

    public static AlertDialog createChooseDialog(
            Context context,
            String title,
            String[] items,
            DialogInterface.OnClickListener positiveOnClickListener
    ) {
        return new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setItems(items, positiveOnClickListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                        }
                        return false;
                    }
                }).create();
    }

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;

    }

    public static ProgressDialog createProgressDialog(
            Context context, String message, ProgressDialogCallback callback) {
        ProgressDialog progressDialog = new WrapperProgressDialg(context, callback);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_SEARCH || i == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return progressDialog;
    }

    public static ProgressDialog createHorizontalProgressDialog(
            Context context, String message, final ProgressDialogCallback callback) {
        final ProgressDialog progressDialog = new WrapperProgressDialg(context, callback);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onCancel(progressDialog);
                    }
                }
        );
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static ProgressDialog createHorizontalProgressDialogImportContact(
            Context context, String message, final ProgressDialogCallback callback) {
        final ProgressDialog progressDialog = new WrapperProgressDialg(context, callback);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }


    public static ProgressDialog createHorizontalProgressDialog(
            Context context, int messageId, final ProgressDialogCallback callback) {
        return createHorizontalProgressDialog(context, context.getString(messageId), callback);
    }

    /**
     * 创建进度条对话框
     *
     * @param context  Context
     * @param message  提示信息
     * @param max      最大值
     * @param callback 进度条回调接口，需要增加进度条的值
     * @return ProgressDialog
     */
    public static ProgressDialog createHorizontalProgressDialog(
            Context context, String message, int max, final ProgressDialogCallback callback) {
        ProgressDialog progressDialog = new WrapperProgressDialg(context, callback);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(max);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static interface ProgressDialogCallback {

        void onShow(ProgressDialog dialog);

        void onDismiss(ProgressDialog dialog);

        void onCancel(ProgressDialog dialog);

        void onException(ProgressDialog dialog, Exception exception);

        public static class Adapter implements ProgressDialogCallback {
            @Override
            public void onShow(ProgressDialog dialog) {
            }

            @Override
            public void onDismiss(ProgressDialog dialog) {
            }

            @Override
            public void onCancel(ProgressDialog dialog) {
            }

            public void onException(ProgressDialog dialog, Exception exception) {

            }
        }
    }

    public static ProgressDialog createProgressDialog(String message, Context context) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        return dialog;
    }


    private static class WrapperProgressDialg extends ProgressDialog {

        private ProgressDialogCallback callback;

        private Handler handler;

        private WrapperProgressDialg(Context context, ProgressDialogCallback callback) {
            super(context);
            this.callback = callback;
            handler = new Handler();
        }

        @Override
        public void show() {
            try {
                super.show();
            } catch (Throwable t) {
//                logger.error(t.getMessage(), t);
            }
            startTaskThread();
        }


        private void startTaskThread() {
            final ProgressDialog instance = this;
            new Thread() {
                @Override
                public void run() {
                    try {
                        if (callback != null) {
                            callback.onShow(instance);
                        }
                        Thread.sleep(1000);
                    } catch (final Exception ex) {
//                        logger.error(ex.getMessage(), ex);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onException(instance, ex);
                                }
                            }
                        });
                    } finally {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (callback != null) {
                                        callback.onDismiss(instance);
                                    }
                                } catch (Throwable t) {
//                                    logger.error(t.getMessage(), t);
                                }
                            }
                        });
                        try {
                            instance.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

    }
}
