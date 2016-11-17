package com.cxd.sliderecyclerview.data;

/**
 * Created by caixd on 2016/11/2.
 */

public class Message {
    private int resId;
    private String name;
    private String lastMsg;
    private int notReadCount;

    public Message(int resId, String name, String lastMsg, int notReadCount) {
        this.resId = resId;
        this.name = name;
        this.lastMsg = lastMsg;
        this.notReadCount = notReadCount;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(int notReadCount) {
        this.notReadCount = notReadCount;
    }
}
