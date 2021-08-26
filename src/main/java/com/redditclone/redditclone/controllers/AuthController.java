package com.redditclone.redditclone.controllers;


import com.redditclone.redditclone.dto.RegisterRequest;
import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.repository.UserRepository;
import com.redditclone.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")




    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SpringReddiException, Exception {


       if(userRepository.findByUsername(registerRequest.getUsername()).isEmpty()==false){
          return ResponseEntity.unprocessableEntity().body("User Already exists");
       }


      authService.signUp(registerRequest);
      return ResponseEntity.ok().body("User Registration Successfull");
    }

    @GetMapping("/accountVerification/{token}")

    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringReddiException {
        authService.verify(token);

        return ResponseEntity.ok().body("User Verified Successfully");
    }




}
