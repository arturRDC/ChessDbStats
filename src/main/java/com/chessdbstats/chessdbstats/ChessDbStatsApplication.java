package com.chessdbstats.chessdbstats;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.chessdbstats.chessdbstats.service.FileManipulationService;
import com.chessdbstats.chessdbstats.service.UserService;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@SpringBootApplication
public class ChessDbStatsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChessDbStatsApplication.class, args);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private FileManipulationService fileManipulationService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Executing commands on startup...");
        User demo = new User();
        demo.setEmail("demo@email.com");
        demo.setPassword("demo");
        demo.setFirstName("Demo");
        demo.setLastName("Demo last name");
        userService.addUser(demo);



        Collection col = new Collection(demo,"Lichess Rapid", "Some games from a lichess rapid tournament");
        collectionService.createCollection(col);
        fileManipulationService.appendFiles(col.getPgnPath(), Paths.get("games", "demo1.pgn").toString());


        Collection col2 = new Collection(demo,"World Championship 2014", "World Championship Carlsen - Anand");
        collectionService.createCollection(col2);
        fileManipulationService.appendFiles(col2.getPgnPath(), Paths.get("games", "demo2.pgn").toString());

        Collection col3 = new Collection(demo,"World Championship 2018", "World Championship Carlsen - Caruana");
        collectionService.createCollection(col3);
        fileManipulationService.appendFiles(col3.getPgnPath(), Paths.get("games", "demo3.pgn").toString());

    }

}
