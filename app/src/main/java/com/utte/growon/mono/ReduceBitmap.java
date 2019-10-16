package com.utte.growon.mono;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReduceBitmap {

    private static final String TAG = "ReduceBitmap";

    public static Bitmap reduceDecodeStream(URL url, int reqWidth, int reqHeight) {
        Bitmap b = null;
        HttpURLConnection con;
        try {
//            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(5 * 1000);
//            con.setReadTimeout(10 * 1000);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(con.getInputStream(), null, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//            con.disconnect();

            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);

            options.inJustDecodeBounds = false;
            b = BitmapFactory.decodeStream(con.getInputStream(), null, options);
            Log.e(TAG, "decodeSampledBitmapFromStream: " + b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片高宽
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        // 图片高宽大了才变
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}