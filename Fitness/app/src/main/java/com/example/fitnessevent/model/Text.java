package com.example.fitnessevent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Text {

    public Text() {
    }

    public Text(String t, String h) {
        text = t;
        html = h;
    }

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("html")
    @Expose
    private String html;

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
