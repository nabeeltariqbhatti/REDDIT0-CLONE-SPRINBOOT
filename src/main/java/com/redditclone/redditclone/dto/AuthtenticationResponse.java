package com.redditclone.redditclone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthtenticationResponse {

    private String authenticateToken;
    private String username;
}
