package com.zhanggb.contacts.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * @author zhanggaobo
 * @since 12/03/2014
 */
public class IntentFactory {

    public static final String MIMETYPE = "application/vnd.android.package-archive";


    /*
    安装下载包
    */
    public static void openInstaller(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), MIMETYPE);
        context.startActivity(intent);
    }


    public static Intent createCheckFile(Context context, File file, String type) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public static Intent createPickImageIntent() {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    public static Intent createVideoIntent() {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        return intent;
    }

    public static Intent createCaptureImageIntent(File captureImageFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureImageFile));
        return intent;
    }

    public static Intent createCropImageIntent(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static Intent createCropImageIntent(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static Intent createShareIntent(String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    public static Intent createCallIntent(String phone) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
        return intent;
    }

    public static Intent createCallPageIntent(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        return intent;
    }

}
