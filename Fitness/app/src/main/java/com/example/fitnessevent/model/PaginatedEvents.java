package com.example.fitnessevent.model;

import java.io.Serializable;
import java.util.List;

public class PaginatedEvents implements Serializable {
    private Pagination pagination;
    private List<Event> events;



    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public PaginatedEvents() {
    }

    public PaginatedEvents(Pagination pagination, List<Event> events) {
        this.pagination = pagination;
        this.events = events;
    }
}
