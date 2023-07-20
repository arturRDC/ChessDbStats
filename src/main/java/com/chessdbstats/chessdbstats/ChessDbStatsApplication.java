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

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

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



        Collection col = new Collection(demo,"Fischer Games", "Some games from the legend Bobby Fischer");
        collectionService.createCollection(col);
        fileManipulationService.appendFiles(col.getPgnPath(), Paths.get("games", "demo1.pgn").toString());


        Collection col2 = new Collection(demo,"World Championship 2014", "World Championship Carlsen - Anand");
        collectionService.createCollection(col2);
        fileManipulationService.appendFiles(col2.getPgnPath(), Paths.get("games", "demo2.pgn").toString());

        Collection col3 = new Collection(demo,"World Championship 2018", "World Championship Carlsen - Caruana");
        collectionService.createCollection(col3);
        fileManipulationService.appendFiles(col3.getPgnPath(), Paths.get("games", "demo3.pgn").toString());
//        collectionService.uploadGames(file, 1L);


//        Path filePath = Paths.get("games", "chusa23.pgn");
//        PgnHolder pgn = new PgnHolder(filePath.toString());
//        PgnHolder pgn = new PgnHolder("study.pgn");
//        PgnHolder pgn2 = new PgnHolder("study3.pgn");
//        try {
//            pgn.loadPgn();
//
//            System.out.println("chusa magnus count " + pgn.countGamesInPgnFile());
//
//            pgn2.loadPgn();
//            System.out.println("qatar magnus2 count " + pgn2.countGamesInPgnFile());
//            pgn.setFileName("study2.pgn");
//            pgn.loadPgn();
//            System.out.println("both count " + pgn.countGamesInPgnFile());
////            pgn.setFileName("study3.pgn");
//            pgn.savePgn();
//            List<Game> games = pgn.getGames();
//            int count = 1;
//            for (Game game : games) {
//                System.out.println(count);
//                System.out.println(game.getOpening());
//                count++;
//            }
//        } catch (Exception e) {
//            System.out.println("error");
//            throw new RuntimeException(e);
//        }

    }

}
