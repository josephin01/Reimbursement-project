package com.reimbursement.project.exception;


public class JwtException extends RuntimeException{
    public JwtException(String error) {
        super(error);
    }

}
