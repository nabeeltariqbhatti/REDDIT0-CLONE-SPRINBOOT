package com.redditclone.redditclone.service;


import com.redditclone.redditclone.dto.RegisterRequest;
import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.model.NotificationEmail;
import com.redditclone.redditclone.model.User;
import com.redditclone.redditclone.model.VerificationToken;
import com.redditclone.redditclone.repository.UserRepository;
import com.redditclone.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {



    private final  PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;



    @Transactional
    public void signUp(RegisterRequest registerRequest) throws SpringReddiException, Exception {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        
        String token = gegenrateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please Activate your account",user.getEmail(),"Pleas Activate Your email:"+"http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String gegenrateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;



    }
}
