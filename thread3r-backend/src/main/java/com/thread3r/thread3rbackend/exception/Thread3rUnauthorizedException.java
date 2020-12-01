package com.thread3r.thread3rbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Thread3rUnauthorizedException extends RuntimeException {
}
