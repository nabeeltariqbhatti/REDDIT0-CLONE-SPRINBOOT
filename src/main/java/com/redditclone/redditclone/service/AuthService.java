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

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {


    private static UUID uuid;
    private static VerificationTokenRepository verificationTokenRepository;
    private final  PasswordEncoder passwordEncoder;


    private static  UserRepository userRepository;


    private final MailService mailService;

    public static void verify(String token) throws SpringReddiException {


        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringReddiException("Invalid Token"));
fetchUserAndEnable(verificationToken.get());


    }


    @Transactional
    private static void fetchUserAndEnable(VerificationToken verificationToken) throws SpringReddiException {

   String username =  verificationToken.getUser().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(()->new SpringReddiException("Invalid User -" + username));
        user.setEnabled(true);

        userRepository.save(user);


    }


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
