package com.recruit.taskapp.services;

import com.recruit.taskapp.exceptions.UableToStoreDataException;
import com.recruit.taskapp.helpers.ReadFileHelper;
import com.recruit.taskapp.models.Record;
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
            throw new UableToStoreDataException("fail to store data");
        }
    }

    public Record getRecordByPrimaryKey(String primaryKey){
        return recordRepository.findById(primaryKey).orElse(null);
    }

    public void deleteRecordByPrimaryKey(String primaryKey){
        recordRepository.deleteById(primaryKey);
    }
}
