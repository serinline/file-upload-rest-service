package com.recruit.taskapp.exceptions;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String s){
        super(s);
    }
}