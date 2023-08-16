package com.gsc.programaavisos.exceptions;

import com.gsc.programaavisos.constants.ApiErrorConstants;
import com.sc.commons.exceptions.SCErrorException;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Log4j
@ControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(value = {ProgramaAvisosException.class})
    public ResponseEntity<ApiError> programaAvisosException(ProgramaAvisosException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(ApiErrorConstants.ERROR_PROCESSING_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), request.getDescription(false), ex.getCause().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(value = {SCErrorException.class})
    public ResponseEntity<ApiError> sqlQueryException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(ApiErrorConstants.ERROR_PROCESSING_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), request.getDescription(false), ex.getCause().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}
