package com.jinax.hospital_management_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : chara
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ReleasePatientNotInMinorException extends RuntimeException{
    public ReleasePatientNotInMinorException(String message) {
        super(message);
    }
}
