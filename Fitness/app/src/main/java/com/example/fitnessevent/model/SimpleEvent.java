package com.example.fitnessevent.model;

import java.io.Serializable;

public class SimpleEvent implements Serializable {
    private String id;
    private String nameText;
    private String descriptionText;
    private String startTime;
    private String endTime;
    private String latitude;
    private String longitude;
    private String logoUrl;

    public SimpleEvent() {

    }

    public SimpleEvent(String id, String nameText, String descriptionText, String startTime, String endTime, String latitude, String longitude, String logoUrl) {
        this.id = id;
        this.nameText = nameText;
        this.descriptionText = descriptionText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.logoUrl = logoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longitude;
    }

    public void setLongtitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
