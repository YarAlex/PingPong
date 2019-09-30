package com.yar.pingpong;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class KeyHandler{

    private static final Logger log = org.apache.log4j.Logger.getLogger(KeyHandler.class);

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
        log.debug("Key = "+event.getCode()+", eventType = "+event.getEventType());
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
