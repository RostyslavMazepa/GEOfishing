package com.geofishing.services;

import com.geofishing.model.auth.User;
import com.geofishing.model.social.GoogleAccount;
import com.geofishing.repository.GoogleAccountRepository;
import com.geofishing.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    GoogleAccountRepository googleAccountRepository;
    @Value("${google.clientid}")
    private String clientId;

    public AuthResult authFlow(String idTokenString, String refreshToken) {
        GoogleIdToken idToken = getIdToken(idTokenString);
        AuthResult.AuthResultType resultType = AuthResult.AuthResultType.FOUND;
        if (verifyToken(idToken)) {

            GoogleIdToken.Payload googleUser = idToken.getPayload();
            User user = userRepository.findByGoogleAccount_UserIdOrEmail(googleUser.getSubject(), googleUser.getEmail());
            if (user == null) {
                user = userService.registerNewUserAccount(googleUser.getEmail(), (String) googleUser.get("given_name"), (String) googleUser.get("family_name"));
                resultType = AuthResult.AuthResultType.REGISTRED;
            }

            if (user.getGoogleAccount() == null) {
                return new AuthResult(bindFacebookAccount(user, googleUser, refreshToken, idTokenString), resultType);
            }
            return new AuthResult(user, resultType);
        } else {
            throw new BadCredentialsException("Identity token is not valid");
        }


    }


    public User bindFacebookAccount(User user, GoogleIdToken.Payload tokenPayload, String refreshToken, String idTokenString) {
        if (tokenPayload == null) {
            tokenPayload = getIdToken(idTokenString).getPayload();
        }
        GoogleAccount googleAccount = googleAccountRepository.save(new GoogleAccount(refreshToken, tokenPayload.getEmail(), tokenPayload.getSubject(), (String) (tokenPayload.get("picture"))));
        user.setGoogleAccount(googleAccount);
        userService.saveRegisteredUser(user);
        return user;
    }


    public boolean verifyToken(String idTokenString) {
        try {
            return getIdToken(idTokenString).verify(new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId)).build());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verifyToken(GoogleIdToken idToken) {
        try {
            return idToken.verify(new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId)).build());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private GoogleIdToken getIdToken(String idTokenString) {
        try {
            return GoogleIdToken.parse(new JacksonFactory(), idTokenString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}