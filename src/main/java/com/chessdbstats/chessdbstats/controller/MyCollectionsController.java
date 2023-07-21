package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyCollectionsController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CollectionService collectionService;
    @GetMapping("my-collections")
    String getDashboard(Model model) {
        List<CollectionView> collections = collectionService.listCollections();

        model.addAttribute("collections",collections);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "my-collections";
    }
}
