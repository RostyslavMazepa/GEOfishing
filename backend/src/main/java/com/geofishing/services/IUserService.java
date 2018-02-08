package com.geofishing.services;

import com.geofishing.auth.UserAlreadyExistException;
import com.geofishing.dto.UserDTO;
import com.geofishing.model.auth.PasswordResetToken;
import com.geofishing.model.auth.User;
import com.geofishing.model.auth.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {
    User registerNewUserAccount(UserDTO accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    List<String> getUsersFromSessionRegistry();

}
