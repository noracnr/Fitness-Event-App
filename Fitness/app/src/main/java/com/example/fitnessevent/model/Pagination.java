package com.example.fitnessevent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pagination implements Serializable {
    @SerializedName("object_count")
    @Expose
    private int objectCount;

    @SerializedName("page_number")
    @Expose
    private int pageNumber;

    @SerializedName("page_size")
    @Expose
    private int pageSize;

    @SerializedName("page_count")
    @Expose
    private int pageCount;

    private String continuation;

    public int getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getContinuation() {
        return continuation;
    }

    public void setContinuation(String continuation) {
        this.continuation = continuation;
    }

    public Pagination() {
    }

    public Pagination(int objectCount, int pageNumber, int pageSize, int pageCount, String continuation) {
        this.objectCount = objectCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.continuation = continuation;
    }
}
