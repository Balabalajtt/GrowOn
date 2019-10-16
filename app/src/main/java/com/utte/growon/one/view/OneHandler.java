package com.utte.growon.one.view;

import android.os.Handler;
import android.os.Message;

public class OneHandler extends Handler {

    static final int REFRESH_HOME_DATA = 1;

    @Override
    public void handleMessage(Message msg) {
        msg.getCallback().run();
    }
}
