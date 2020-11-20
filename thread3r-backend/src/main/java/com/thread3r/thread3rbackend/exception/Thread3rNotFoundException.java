package com.thread3r.thread3rbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class Thread3rNotFoundException extends RuntimeException {
}
