package com.geofishing.services;

import com.geofishing.auth.UserAlreadyExistException;
import com.geofishing.dto.UserDTO;
import com.geofishing.model.auth.PasswordResetToken;
import com.geofishing.model.auth.User;
import com.geofishing.model.auth.VerificationToken;
import com.geofishing.model.social.ISocialAccount;
import com.geofishing.repository.PasswordResetTokenRepository;
import com.geofishing.repository.RoleRepository;
import com.geofishing.repository.UserRepository;
import com.geofishing.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "GeoFishing";
    @Autowired
    AuthorizationServerTokenServices tokenServices;
    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    FacebookService facebookService;
    @Autowired
    private UserRepository repository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    @Qualifier("encoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    GoogleService googleService;

    // API

    public User registerNewUserAccount(final UserDTO accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        String pass = accountDto.getPassword();
        if (pass == null || pass.isEmpty()) {
            pass = new RandomValueStringGenerator().generate();
        }
        user.setPassword(passwordEncoder.encode(pass));
        user.setEmail(accountDto.getEmail());
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_TEMP_USER"))));
        return repository.save(user);
    }

    public User registerNewUserAccount(String email, String firstName, String lastName) {

        final User user = new User();
        user.setUsername(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(new RandomValueStringGenerator().generate()));
        user.setEmail(email);
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("USER"))));
        return repository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }


    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }


    public void saveRegisteredUser(final User user) {
        repository.save(user);
    }

    public void deleteUser(final User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        repository.delete(user);
    }

    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public User findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    public User findUserByFacebookAccount(final String userId) {
        return repository.findByFacebookAccount_UserId(userId);
    }

    public User findBySocialAccount(ISocialAccount sa, SocialNetwork sn) {
        User user = null;
        switch (sn) {
            case FACEBOOK:
                user = repository.findByFacebookAccount_UserIdOrEmail(sa.getUserId(), sa.getEmail());
                break;
            case GOOGLE:
                user = repository.findByGoogleAccount_UserIdOrEmail(sa.getUserId(), sa.getEmail());
                break;
        }
        return user;

    }

    public User bindSocialAccount(User user, String accessToken, String refreshToken, SocialNetwork sn) {
        switch (sn) {
            case FACEBOOK:
                user = facebookService.bindFacebookAccount(user, null, accessToken, refreshToken);
                break;
            case GOOGLE: //TODO implement google binding;
                break;
        }
        return user;
    }

    public AuthResult authFlow(String accessToken, String refreshToken, SocialNetwork sn) {
        switch (sn) {
            case FACEBOOK:
                return facebookService.authFlow(accessToken, refreshToken);
            case GOOGLE:
                return googleService.authFlow(accessToken, refreshToken);
        }

        return null;
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(final long id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        repository.save(user);
        return TOKEN_VALID;
    }

    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
    }

//    @Override
//    public User updateUser2FA(boolean use2FA) {
//        final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) curAuth.getPrincipal();
//        currentUser.setUsing2FA(use2FA);
//        currentUser = repository.save(currentUser);
//        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        return currentUser;
//    }

    private boolean emailExist(final String email) {
        return repository.findByEmail(email) != null;
    }

    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals().stream().filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(Object::toString).collect(Collectors.toList());
    }

    public OAuth2AccessToken getUserToken(User user) {
        AuthorizationRequest authorizationRequest =
                new AuthorizationRequest();
        authorizationRequest.setApproved(true);
        authorizationRequest.setClientId("geofappid");
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("geofappid");
        authorizationRequest.setResourceIdsAndAuthoritiesFromClientDetails(clientDetails);
        UserDetails userPrincipal = userDetailsService.getUserDetails(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest.createOAuth2Request(), authenticationToken);
        authenticationRequest.setAuthenticated(true);

        return tokenServices.createAccessToken(authenticationRequest);
    }

    public boolean isUsernameFree(String username) {
        return !repository.existsByUsername(username);
    }

    public boolean isEmailFree(String email) {
        return !repository.existsByEmail(email);
    }

}
