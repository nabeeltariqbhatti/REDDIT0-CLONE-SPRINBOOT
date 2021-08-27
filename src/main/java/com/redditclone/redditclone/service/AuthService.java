package com.redditclone.redditclone.service;


import com.redditclone.redditclone.dto.AuthtenticationResponse;
import com.redditclone.redditclone.dto.LoginRequest;
import com.redditclone.redditclone.dto.RegisterRequest;
import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.model.NotificationEmail;
import com.redditclone.redditclone.model.User;
import com.redditclone.redditclone.model.VerificationToken;
import com.redditclone.redditclone.repository.UserRepository;
import com.redditclone.redditclone.repository.VerificationTokenRepository;
import com.redditclone.redditclone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {


    private static UUID uuid;
    private final VerificationTokenRepository verificationTokenRepository;
    private final  PasswordEncoder passwordEncoder;


    private final   UserRepository userRepository;

    private final JwtProvider jwtProvider;
    private final MailService mailService;
    private  final AuthenticationManager authenticationManager;

    public  void verify(String token) throws SpringReddiException {


        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringReddiException("Invalid Token"));
fetchUserAndEnable(verificationToken.get());


    }


    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) throws SpringReddiException {

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

    public AuthtenticationResponse login(LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, SpringReddiException {
       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
       String token = jwtProvider.generateToken(authentication);

    return new AuthtenticationResponse(token, loginRequest.getUsername());
    }
}
