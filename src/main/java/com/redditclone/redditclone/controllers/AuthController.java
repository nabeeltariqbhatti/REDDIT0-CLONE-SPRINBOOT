package com.redditclone.redditclone.controllers;


import com.redditclone.redditclone.dto.RegisterRequest;
import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
