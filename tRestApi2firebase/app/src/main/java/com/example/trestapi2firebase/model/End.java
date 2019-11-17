package com.example.trestapi2firebase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class End {
    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("local")
    @Expose
    private String local;

    @SerializedName("utc")
    @Expose
    private String utc;

    public String getTImezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
