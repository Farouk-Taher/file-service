package com.example.filedemo.service;

import com.example.filedemo.exception.FileStorageException;
import com.example.filedemo.exception.MyFileNotFoundException;
import com.example.filedemo.model.DBFile;
import com.example.filedemo.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public Boolean deleteFile(String fileId) {
        try {
            dbFileRepository.deleteById(fileId);
            return true;
        } catch (Exception ex) {
            throw new MyFileNotFoundException("Could not delete file " + fileId, ex);
        }
    }

    @Transactional
    public DBFile updateFile(MultipartFile file, String fileId) {
        try {
            if (deleteFile(fileId)) {
                return storeFile(file);
            } else {
                throw new FileStorageException("Could not store file, Please try again!");
            }
        } catch (Exception ex) {
            throw new MyFileNotFoundException("Could not delete file " + fileId, ex);
        }
    }
}
