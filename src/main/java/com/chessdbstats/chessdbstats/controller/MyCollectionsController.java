package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyCollectionsController {
    @GetMapping("my-collections")
    String getDashboard(Model model) {
        return "my-collections";
    }
}
