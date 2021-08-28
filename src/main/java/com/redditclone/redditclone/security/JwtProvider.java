package com.redditclone.redditclone.security;



import com.redditclone.redditclone.exceptions.SpringReddiException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @PostConstruct
    public void init() throws SpringReddiException {
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream,"secret".toCharArray());

        } catch (CertificateException | KeyStoreException e) {
            throw new SpringReddiException("Exception occurred while loading keystore");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    public String generateToken(Authentication authentication) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, SpringReddiException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .compact();
    }




    private PrivateKey getPrivateKey() throws SpringReddiException {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringReddiException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) throws SpringReddiException {
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
  return true;
    }

    private PublicKey getPublickey() throws SpringReddiException {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringReddiException("Exception occured while " +"retrieving public key from keystore");
        }
    }

}
