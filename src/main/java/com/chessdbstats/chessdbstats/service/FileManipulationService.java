package com.chessdbstats.chessdbstats.service;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileManipulationService {

    public void appendAndRenameFiles(String firstFileName, String secondFileName) throws IOException {
        File firstFile = new File(firstFileName);
        File secondFile = new File(secondFileName);

        // Check if both files exist
        if (!firstFile.exists() || !secondFile.exists()) {
            throw new FileNotFoundException("One or both of the files do not exist.");
        }

        // Reading content from the second file
        String secondFileContent = FileUtils.readFileToString(secondFile, "UTF-8");

        // Appending content of the second file to the first file
        FileUtils.writeStringToFile(firstFile, secondFileContent, "UTF-8", true);

        // Renaming the first file with the second file's name
        if (firstFile.exists()) {
            File renamedFile = new File(secondFileName);
            if (renamedFile.exists()) {
                if (!renamedFile.delete()) {
                    throw new IOException("Failed to delete the existing file with the name " + secondFileName);
                }
            }
            if (!firstFile.renameTo(renamedFile)) {
                throw new IOException("Failed to rename the file.");
            }
        } else {
            throw new FileNotFoundException("First file does not exist.");
        }
    }

    public void createEmptyFile(String fileName) throws IOException {
        Path filePath = Paths.get("games", fileName);
        File file = filePath.toFile();

        // Create an empty file
        if (file.createNewFile()) {
            return;
        } else {
            throw new IOException("File creation failed. File may already exist.");
        }
    }
}
