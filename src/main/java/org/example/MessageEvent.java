package org.example;

import java.util.EventObject;

public class MessageEvent extends EventObject {
    private final String message;
    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
