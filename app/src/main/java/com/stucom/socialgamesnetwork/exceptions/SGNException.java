package com.stucom.socialgamesnetwork.exceptions;

import java.util.Arrays;
import java.util.List;

public class SGNException extends CodeException {

    public static final int USER_NOT_FOUND = 0;

    public SGNException(int code) {
        super(code);
    }

    private final List<String> messages = Arrays.asList(
            "USER NOT FOUND"
    );

    @Override
    public String getMessage() {
        return messages.get(getCode());
    }
}
