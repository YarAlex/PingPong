package com.yar.pingpong;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyHandler{

    private static KeyHandler instance;
    private List<KeyHandlerEvent> events = new ArrayList<>();
    public static final String KEY_PRESSED = "KEY_PRESSED";
    public static final String KEY_RELEASED = "KEY_RELEASED";

    private EventHandler<KeyEvent> keyPressed = event -> process(event);
    private EventHandler<KeyEvent> keyReleased = event -> process(event);

    private KeyHandler(){

    }

    public static KeyHandler getInstance() {
        if (instance == null) {
            instance = new KeyHandler();
        }
        return instance;
    }

    public void process (KeyEvent event) {
        System.out.println("Code="+event.getCode()+" / type="+event.getEventType());
        for (KeyHandlerEvent keyHandlerEvent : events) {
            keyHandlerEvent.onKeyEvent(event);
        }
    }

    public void addKeyHandlerEvent(KeyHandlerEvent keyHandlerEvent) {
        events.add(keyHandlerEvent);
    }

    public EventHandler<KeyEvent> getKeyPressed() {
        return keyPressed;
    }

    public EventHandler<KeyEvent> getKeyReleased() {
        return keyReleased;
    }
}
