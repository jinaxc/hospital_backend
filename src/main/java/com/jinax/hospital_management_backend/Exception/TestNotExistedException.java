package com.jinax.hospital_management_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : chara
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TestNotExistedException extends RuntimeException{
    public TestNotExistedException(String message) {
        super(message);
    }
}
