package com.reimbursement.project.exception;

import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponseDto> handleAlreadyExistsException(AlreadyExistException ex) {
        ApiResponseDto response = new ApiResponseDto();
        response.setHttpStatus(HttpStatus.CONFLICT);
        response.setMessage(ex.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setHttpStatus(HttpStatus.NOT_FOUND);
        apiResponseDto.setMessage(exception.getMessage());
        apiResponseDto.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponseDto);
    }
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponseDto> handleInvalidException(InvalidException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setHttpStatus(HttpStatus.FORBIDDEN);
        apiResponseDto.setMessage(e.getMessage());
        apiResponseDto.setData(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDto> handleMethodArgumentInValidExc(MethodArgumentNotValidException argumentNotValidException) {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            errorMap.put(((FieldError) objectError).getField(), objectError.getDefaultMessage());
            apiResponseDto.setMessage(errorMap.toString());
        }
        apiResponseDto.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponseDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDto> handleResourceNotFoundException(InvalidException exception) {
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        responseDto.setMessage(exception.getMessage());
        responseDto.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

}
