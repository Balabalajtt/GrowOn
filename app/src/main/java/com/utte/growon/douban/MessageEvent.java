package com.utte.growon.douban;

/**
 * Created by 江婷婷 on 2017/11/25.
 */

public class MessageEvent {

    private String message;
    private int eventCode;
    public MessageEvent(String message) {
        this.message = message;
        switch (message) {
            case "home page data already":
                eventCode = 1;
                break;
            case "get next page":
                eventCode = 2;
                break;
            case "find page data already":
                eventCode = 3;
                break;
            case "tuchong data already":
                eventCode = 4;
                break;
            case "douban data already":
                eventCode = 5;
                break;
            case "one data already":
                eventCode = 6;
                break;
            case "one refresh already":
                eventCode = 7;
                break;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
