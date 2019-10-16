package com.utte.growon.mono;

import android.graphics.Bitmap;
import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestUtil {

    private static final String TAG = "RequestUtil";

    public static Bitmap downloadBitmap(String imageUrl, View view) {
        Bitmap bitmap;
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (view == null) {
            bitmap = ReduceBitmap.reduceDecodeStream(url, view.getWidth(), view.getHeight());
        } else {
            bitmap = ReduceBitmap.reduceDecodeStream(url, view.getWidth(), view.getHeight());
        }
        return bitmap;
    }

}