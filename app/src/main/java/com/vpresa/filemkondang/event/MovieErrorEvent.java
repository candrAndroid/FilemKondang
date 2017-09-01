package com.vpresa.filemkondang.event;

public class MovieErrorEvent extends BaseEvent {
    public MovieErrorEvent(String message) {
        super(message);
    }
}