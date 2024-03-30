package com.reimbursement.project.exception;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException(String error) {
        super(error);
    }

}
