package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import com.chessdbstats.chessdbstats.service.UserService;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCollection(@PathVariable("id") Integer id) {
        System.out.println("deleted " + id);
        return ResponseEntity.ok(id.toString());
    }

    @PostMapping("")
    ResponseEntity<String> createCollection(@Valid @RequestBody CreateCollectionFormData formData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
        System.out.println("created collection");
//        PgnHolder pgn = new PgnHolder("Fischer.pgn");
//        try {
//            pgn.loadPgn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        PgnIterator games = null;
//        try {
//            games = new PgnIterator("chusa23.pgn");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
////        PgnIterator games = new PgnIterator("Fischer.pgn");
//
//        for (Game game: games) {
//            System.out.println(game.getWhitePlayer());
//
//        }
        System.out.println(formData.getTitle() + " " + formData.getDescription());

        User loggedInUser = customUserDetailsService.getLoggedInUser();
        collectionService.createCollection(new Collection(loggedInUser,formData.getTitle(), formData.getDescription()));
        return ResponseEntity.status(HttpStatus.OK).body("Created collection!");
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateCollection(@Valid @RequestBody EditCollectionFormData formData, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
        System.out.println("updated collection" + id);
        System.out.println(formData.title() + " " + formData.description());
        return ResponseEntity.status(HttpStatus.OK).body(formData.title() + " " + formData.description());
    }

@PostMapping("/{id}/upload-games")
public ResponseEntity<String> uploadGames(@RequestParam MultipartFile gamesFile, @PathVariable Long id) {
    try {
        collectionService.uploadGames(gamesFile, id);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

}
