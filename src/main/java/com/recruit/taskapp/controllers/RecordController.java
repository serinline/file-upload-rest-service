package com.recruit.taskapp.controllers;

import com.recruit.taskapp.helpers.ReadFileHelper;
import com.recruit.taskapp.models.Message;
import com.recruit.taskapp.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:8090")
@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/upload")
    public ResponseEntity<Message> uploadFile(@RequestParam("file") MultipartFile file) {

        if (ReadFileHelper.hasProperFormat(file) && ReadFileHelper.validateFile(file)) {
            try {
                recordService.saveToDatabase(file);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new Message("Uploaded the file successfully"));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new Message("Could not upload the file"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Upload proper file"));
    }
}
