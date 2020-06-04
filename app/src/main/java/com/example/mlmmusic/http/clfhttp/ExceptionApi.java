package com.example.mlmmusic.http.clfhttp;


/**
 * Created by clf on 2019/5/19.
 */
public class ExceptionApi extends RuntimeException {

    private int code;
    private String msg;

    public ExceptionApi(int resultCode, String msg) {
        this.code = resultCode;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
