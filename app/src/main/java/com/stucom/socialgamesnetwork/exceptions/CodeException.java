package com.stucom.socialgamesnetwork.exceptions;

public abstract class CodeException extends Exception {

    private final int code;

    public CodeException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
