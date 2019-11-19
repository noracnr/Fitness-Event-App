package com.example.trestapi2firebase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {
    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("resource_uri")
    @Expose
    private String resourceUri;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("age_restriction")
    @Expose
    private String ageRestriction;

    @SerializedName("capacity")
    @Expose
    private String capacity;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Address getAddress() {
        return address;
    }



    public String getId() {
        return id;
    }



    public String getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
