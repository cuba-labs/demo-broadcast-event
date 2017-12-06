package com.company.demo.web.broadcast;

import org.springframework.context.ApplicationEvent;

public class GlobalMessageEvent extends ApplicationEvent {

    public GlobalMessageEvent(String message) {
        super(message);
    }

    public String getMessage() {
        return (String) getSource();
    }
}
