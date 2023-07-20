package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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

        collections.add(new CollectionView(1L,"title1", "description1", "05.06.2023"));
        collections.add(new CollectionView(2L,"title2", "description2", "05.07.2023"));
        collections.add(new CollectionView(3L,"title3", "description3", "05.08.2023"));
        model.addAttribute("collections",collections);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "my-collections";
    }
}
