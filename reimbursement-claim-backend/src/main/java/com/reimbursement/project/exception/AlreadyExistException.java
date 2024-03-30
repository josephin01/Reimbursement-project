package com.reimbursement.project.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String error) {
        super(error);
    }
}
