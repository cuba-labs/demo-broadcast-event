package com.company.demo.web.broadcast;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Component
public class MessageEventBroadcaster {
    public final List<WeakReference<Consumer<GlobalMessageEvent>>> subscriptions = new ArrayList<>();

    @EventListener
    protected void onMessage(GlobalMessageEvent event) {
        synchronized (subscriptions) {
            Iterator<WeakReference<Consumer<GlobalMessageEvent>>> iterator = subscriptions.iterator();
            while (iterator.hasNext()) {
                WeakReference<Consumer<GlobalMessageEvent>> reference = iterator.next();
                Consumer<GlobalMessageEvent> eventConsumer = reference.get();
                if (eventConsumer == null) {
                    iterator.remove();
                } else {
                    eventConsumer.accept(event);
                }
            }
        }
    }

    public void subscribe(Consumer<GlobalMessageEvent> handler) {
        synchronized (subscriptions) {
            subscriptions.add(new WeakReference<>(handler));
        }
    }
}