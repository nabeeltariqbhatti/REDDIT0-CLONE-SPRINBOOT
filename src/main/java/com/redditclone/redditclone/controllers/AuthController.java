package com.redditclone.redditclone.controllers;


import com.redditclone.redditclone.dto.RegisterRequest;
import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {
    private   final AuthService authService;
    @PostMapping("/signup")


    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SpringReddiException, Exception {



      authService.signUp(registerRequest);
      return ResponseEntity.ok().body("User Registration Successfull");
    }

    @GetMapping("/accountVerification/")

    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringReddiException {
        AuthService.verify(token);

        return ResponseEntity.ok().body("User Verified Successfully");
    }




}
