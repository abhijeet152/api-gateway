package com.example.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Component
public class JwtUtil {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
            System.out.println("Stream"+resourceAsStream.toString());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            // TODO: handle error
//            throw new RegistrationException("Exception occurred while loading keystore",null, HttpStatus.BAD_REQUEST);
        }
    }


    public Claims getAllClaimsFromToken(String token) {
        // TODO: handle error
        this.validateToken(token);
        return Jwts.parser().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            // TODO: handle error
            throw new IllegalStateException();
//            throw new RegistrationException("Exception occurred while " +
//                    "retrieving public key from keystore", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
