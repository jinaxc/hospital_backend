package com.jinax.hospital_management_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : chara
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddPatientFailedException extends RuntimeException{
    public AddPatientFailedException(String message) {
        super(message);
    }
}
