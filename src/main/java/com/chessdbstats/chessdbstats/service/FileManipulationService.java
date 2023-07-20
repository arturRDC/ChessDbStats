package com.chessdbstats.chessdbstats.service;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileManipulationService {

    public void appendFiles(String firstFileName, String secondFileName) throws IOException {
        File firstFile = new File(firstFileName);
        File secondFile = new File(secondFileName);

        if (!firstFile.exists() || !secondFile.exists()) {
            throw new FileNotFoundException("One or both of the files do not exist.");
        }

        String secondFileContent = FileUtils.readFileToString(secondFile, "UTF-8");

        FileUtils.writeStringToFile(firstFile, secondFileContent, "UTF-8", true);
    }

    public void renameFile(String oldFileName, String newFileName) throws IOException {
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);

        if (!oldFile.exists()) {
            throw new FileNotFoundException("File to be renamed does not exist.");
        }

        if (newFile.exists()) {
            if (!newFile.delete()) {
                throw new IOException("Failed to delete the existing file with the name " + newFileName);
            }
        }

        if (!oldFile.renameTo(newFile)) {
            throw new IOException("Failed to rename the file.");
        }
    }

    public void appendAndRenameFiles(String firstFileName, String secondFileName) throws IOException {
        appendFiles(firstFileName, secondFileName);
        renameFile(firstFileName, secondFileName);
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
