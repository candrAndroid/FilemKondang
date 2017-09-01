package com.vpresa.filemkondang.event;

import com.vpresa.filemkondang.model.MovieResponse;

public class MovieEvent extends BaseEvent {
    private MovieResponse body;

    public MovieEvent(String message, MovieResponse body) {
        super(message);
        this.body = body;
    }

    public MovieResponse getBody() {
        return body;
    }
}