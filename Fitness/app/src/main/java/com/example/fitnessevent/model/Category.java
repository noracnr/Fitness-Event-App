package com.example.fitnessevent.model;

import androidx.annotation.NonNull;

public class Category {
    private String name;

    private String categoryId;

    public Category(String name, String categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
