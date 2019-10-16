package com.utte.growon.mono;

import android.os.Handler;
import android.os.Message;

import com.utte.growon.mono.model.Music;

import java.lang.ref.WeakReference;

public class MonoHandler extends Handler {
    private WeakReference<MonoFragment> mWeakReference;

    public static final int REFRESH_DATA = 0;
    public static final int REFRESH_MUSIC_DATA = 1;

    public MonoHandler(MonoFragment fragment) {
        mWeakReference= new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        final MonoFragment fragment = mWeakReference.get();
        switch (msg.what) {
            case REFRESH_DATA:
                fragment.refresh((MonoData) msg.obj);
                break;
            case REFRESH_MUSIC_DATA:
                fragment.refreshMusic((Music) msg.obj);
                break;
        }
    }
}