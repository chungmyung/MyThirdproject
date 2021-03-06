package com.chungmyung.multychatting;

/**
 * Created by user on 2017-10-30.
 */

public class MsgInfo {
    private String nickName;
    private String message;
    private long time;

    public MsgInfo(String nickName, String message, long time) {
        this.nickName = nickName;
        this.message = message;
        this.time = time;
    }

    @Override
    public String toString() {
        return "MsgInfo [nickName=" + nickName + ", message=" + message + ", time=" + time + "]";
    }
}