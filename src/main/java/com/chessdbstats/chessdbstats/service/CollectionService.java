package com.chessdbstats.chessdbstats.service;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.repository.CollectionRepository;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.github.bhlangonijr.chesslib.pgn.PgnIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    @Autowired
    FileManipulationService fileManipulationService;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public Collection createCollection(Collection collection) {
        Collection col =  collectionRepository.save(collection);
        String fileName = generateFileName(collection, collection.getId());
        try {
            fileManipulationService.createEmptyFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        col.setPgnPath("games/"+ fileName);

        return collectionRepository.save(col);
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    public Collection updateCollection(Collection collection) {
        if (collectionRepository.existsById(collection.getId())) {
            return collectionRepository.save(collection);
        }
        return null;
    }

    public void deleteCollectionById(Long id) {
        collectionRepository.deleteById(id);
    }

    public void uploadGames(MultipartFile gamesFile, Long id) {
        Collection col = getCollectionById(id);

        if (col == null) {
            throw new IllegalArgumentException("Collection not found with id: " + id);
        }
        String existingLocation = col.getPgnPath();
        String appendLocation;
        try {
            String fileName = generateFileName(col, id);
            Path filePath = Paths.get("games", fileName);
            appendLocation = filePath.toString();

            File file = filePath.toFile();
            gamesFile.transferTo(file.toPath());

            fileManipulationService.appendAndRenameFiles(existingLocation, appendLocation);
            String strPath = filePath.toString();
            col.setPgnPath(strPath);
            updateCollection(col);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }

    }
    private String generateFileName(Collection col, Long id) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return id + "_" + timestamp + "_" + col.getTitle() + ".pgn";
    }
}