package com.reimbursement.project.exception;

public class AlreadyLoggedOutException extends RuntimeException{
    public AlreadyLoggedOutException(String error) {
        super(error);
    }

}
