package com.reactnativenavigation.events;

import java.util.ArrayList;
import java.util.List;

public enum EventBus {
    instance;

    List<Subscriber> subscribers;

    EventBus() {
        subscribers = new ArrayList<>();
    }

    public void register(Subscriber subscriber) {
        if (subscribers.contains(subscriber)) {
            throw new RuntimeException("Subscriber already registered");
        }
        subscribers.add(subscriber);
    }

    public void unregister(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void post(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onEvent(event);
        }
    }
}
