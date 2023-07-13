package com.chessdbstats.chessdbstats.controller;

import jakarta.validation.constraints.NotNull;

public class CreateCollectionFormData {
    @NotNull
    private String title;
    @NotNull
    private String description;

    public CreateCollectionFormData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
