package com.company.demo.web.screens;

import com.company.demo.web.broadcast.GlobalMessageEvent;
import com.company.demo.web.broadcast.MessageEventBroadcaster;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.gui.executors.BackgroundWorker;
import com.haulmont.cuba.gui.executors.UIAccessor;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;
import java.util.Map;
import java.util.function.Consumer;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private MessageEventBroadcaster broadcaster;

    @Inject
    private BackgroundWorker backgroundWorker;

    // Store reference to field, because broadcaster stores weak references
    private Consumer<GlobalMessageEvent> messageHandler;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        UIAccessor uiAccessor = backgroundWorker.getUIAccessor();
        // save reference to message handler
        messageHandler = event -> uiAccessor.access(() -> {
            showNotification(event.getMessage());
        });

        broadcaster.subscribe(messageHandler);
    }

    // Send event

    @Inject
    private Events events;

    public void onSendBtnClick() {
        events.publish(new GlobalMessageEvent("Demo for all!"));
    }
}