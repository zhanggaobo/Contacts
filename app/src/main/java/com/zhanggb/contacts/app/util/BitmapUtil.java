package com.zhanggb.contacts.app.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.LruCache;
import android.widget.ImageView;
import com.zhanggb.contacts.app.R;

import java.io.InputStream;

/**
 * @author zhanggaobo
 * @since 10/28/2016
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BitmapUtil {

    private static final BitmapUtil INSTANCE = new BitmapUtil();

    public static BitmapUtil getInstance() {
        return INSTANCE;
    }

    private BitmapUtil() {
    }

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    private final int cacheSize = maxMemory / 8;

    private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


    public void loadBitmap(String resId, ImageView imageView, Context context) {
        // 查看下内存缓存中是否缓存了这张图片
        final Bitmap bitmap = getBitmapFromMemCache(resId);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.default_avatar);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, context);
            task.execute(resId);
        }
    }


    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        // Decode image in background.
        private ImageView imageView;
        private Context context;

        public BitmapWorkerTask(ImageView imageView, Context context) {
            this.imageView = imageView;
            this.context = context;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            ContentResolver cr = context.getContentResolver();
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(params[0]));
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            if (bitmap != null) {
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            }
            return bitmap;
        }
    }


}
