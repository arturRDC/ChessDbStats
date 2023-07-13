package com.chessdbstats.chessdbstats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
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
        System.out.println(formData.getTitle() + " " + formData.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(formData.getTitle() + " " + formData.getDescription());
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
    ResponseEntity<String> uploadGames(@RequestParam MultipartFile gamesFile, @PathVariable Long id) {
        System.out.println("received file for collection: " + id);
        try {
            gamesFile.transferTo(Paths.get("games", gamesFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
