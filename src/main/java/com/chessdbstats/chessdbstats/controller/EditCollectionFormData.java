package com.chessdbstats.chessdbstats.controller;

import jakarta.validation.constraints.NotNull;

public record EditCollectionFormData(
        @NotNull
        Long id,
        @NotNull
        String title,
        @NotNull
        String description
) {

}
