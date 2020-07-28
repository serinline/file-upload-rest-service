package com.recruit.taskapp.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String primaryKey){
        super("Could not find record with PK: " + primaryKey);
    }
}
