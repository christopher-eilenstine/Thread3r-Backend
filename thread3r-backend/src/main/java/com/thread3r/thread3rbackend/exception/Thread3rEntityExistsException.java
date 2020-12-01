package com.thread3r.thread3rbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Thread3rEntityExistsException extends RuntimeException {
}
