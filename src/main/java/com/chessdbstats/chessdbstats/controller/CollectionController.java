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
    @Autowired
    UserService userService;

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCollection(@PathVariable("id") Long id) {
        collectionService.deleteCollectionById(id);
        return ResponseEntity.ok(id.toString());
    }

    @PostMapping("")
    ResponseEntity<String> createCollection(@Valid @RequestBody CreateCollectionFormData formData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
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

        Boolean valid = userService.validateUserHasCollection(id);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to update this collection");
        }
        collectionService.updateCollection(formData);


        return ResponseEntity.status(HttpStatus.OK).body("Collection updated.");
    }

    @PostMapping("/{id}/upload-games")
    public ResponseEntity<String> uploadGames(@RequestParam MultipartFile gamesFile, @PathVariable Long id) {
        Boolean valid = userService.validateUserHasCollection(id);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to upload to this collection");
        }
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
