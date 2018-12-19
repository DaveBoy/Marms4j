package com.daveboy.marms4j.frame.network;
/**
 *@description 服务器异常
 *@author mxm
 *@creatTime 2018/11/21  15:52
 */
public class ApiException extends Exception {
    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", displayMessage='" + displayMessage + '\'' +
                '}';
    }
}
