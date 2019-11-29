package com.example.fitnessevent.model;

import java.util.List;

public class User {
    private List<Event> likes;
    private List<Event> interests;

    public User() {
    }

    public User(List<Event> likes, List<Event> interests) {
        this.likes = likes;
        this.interests = interests;
    }

    public List<Event> getLikes() {
        return likes;
    }

    public void setLikes(List<Event> likes) {
        this.likes = likes;
    }

    public List<Event> getInterests() {
        return interests;
    }

    public void setInterests(List<Event> interests) {
        this.interests = interests;
    }
}

