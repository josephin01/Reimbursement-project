package com.reimbursement.project.exception;

import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponseDto> httpClientErrorException(HttpClientErrorException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("Http Client Exception")
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponseDto);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto> userAlreadyExists(UserAlreadyExistsException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> resourceNotFound(ResourceNotFoundException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponseDto);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponseDto> jwtException(JwtException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponseDto);
    }

    @ExceptionHandler(AlreadyLoggedOutException.class)
    public ResponseEntity<ApiResponseDto> alreadyLoggedOut(AlreadyLoggedOutException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponseDto);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiResponseDto> inValidData(InvalidDataException e){
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .data(null)
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponseDto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleMethodArgumentInValidExc(MethodArgumentNotValidException argumentNotValidException){
        ApiResponseDto responseDto=new ApiResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        String errorMessage = argumentNotValidException.getMessage();
        String defaultErrorMessage = errorMessage.substring(errorMessage.lastIndexOf("default message [") + 17, errorMessage.length() - 3);
        responseDto.setMessage(defaultErrorMessage);

        BindingResult bindingResult=argumentNotValidException.getBindingResult();
        for(FieldError fieldError:bindingResult.getFieldErrors()){
            if(fieldError.getField().equals("fromDate")){
                responseDto.setMessage(argumentNotValidException.getMessage());
            }
        }
        responseDto.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
