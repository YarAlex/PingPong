package com.yar.pingpong;

import com.yar.pingpong.element.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyHandler{

    private Map<KeyCode, EventType<KeyEvent>> keyMap = new ConcurrentHashMap<>();

    private String KEY_PRESSED = "KEY_PRESSED";
    private String KEY_RELEASED = "KEY_RELEASED";
    private Platform platform;

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            System.out.println("Code="+event.getCode()+" / type="+event.getEventType());
            if (!event.getEventType().equals(keyMap.get(event.getCode()))) {
                keyMap.put(event.getCode(), event.getEventType());
                KeyHandler.this.process(event);
            }
        }
    };

    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            System.out.println("Code="+event.getCode()+" / type="+event.getEventType());
            keyMap.put(event.getCode(), event.getEventType());
            KeyHandler.this.process(event);
        }
    };

    public void process (KeyEvent event) {
        if (platform == null) {
            return;
        }
        if (event.getEventType().toString().equals(KEY_RELEASED)) {
            platform.stopMove();
            return;
        }
        switch (event.getCode()) {
            case LEFT:
                platform.moveLeft();
                break;
            case RIGHT:
                platform.moveRight();
                break;
        }
    }

    public EventHandler<KeyEvent> getKeyPressed() {
        return keyPressed;
    }

    public EventHandler<KeyEvent> getKeyReleased() {
        return keyReleased;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
