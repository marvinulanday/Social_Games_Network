package com.stucom.socialgamesnetwork.model;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("data")
    private Object data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMessage")
    private String errorMsg;

    public Data(Object data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        return "Data: " + data + " - ErrorCode: " + errorCode + " - ErrorMsg: " + errorMsg;

    }
}
