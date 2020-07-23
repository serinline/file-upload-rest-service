package com.recruit.taskapp.services;

import com.recruit.taskapp.helpers.ReadFileHelper;
import com.recruit.taskapp.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    public void saveToDatabase(MultipartFile file) {
        try {
            recordRepository.saveAll(ReadFileHelper.processDataToRecords(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("fail to store data: " + e.getMessage());
        }
    }


}
