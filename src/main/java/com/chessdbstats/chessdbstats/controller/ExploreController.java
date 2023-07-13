package com.chessdbstats.chessdbstats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExploreController {
    @GetMapping("/explore/{id}")
    public String getExplore(@PathVariable Long id) {
        return "explore";
    }
}
