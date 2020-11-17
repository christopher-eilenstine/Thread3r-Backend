package com.thread3r.thread3rbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {

    private Long id;
    private String email;
    private String username;
    private String token;
    private String tokenType;

}
