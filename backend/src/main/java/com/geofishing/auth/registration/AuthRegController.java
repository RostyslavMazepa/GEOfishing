package com.geofishing.auth.registration;

import com.geofishing.auth.AuthResult;
import com.geofishing.auth.json.SerializedToken;
import com.geofishing.auth.json.SocAuthDTO;
import com.geofishing.auth.json.UserDTO;
import com.geofishing.auth.social.SocialNetwork;
import com.geofishing.model.auth.User;
import com.geofishing.model.auth.VerificationToken;
import com.geofishing.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

@Api(tags = "authentication")
@RestController
public class AuthRegController {

    @Autowired
    UserService userService;
    @Autowired
    UserDTOValidator dtoValidator;
    @Autowired
    private MessageSource messageSource;


    @InitBinder("userDTO")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(dtoValidator);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> greeting() {
        return new ResponseEntity("Hi, this is a resource server. Please go to the main page - geosportfishing.top", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<OAuth2AccessToken> createUser(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        User user = userService.registerNewUserAccount(userDTO);
        OAuth2AccessToken userToken = userService.getUserToken(user);
        userService.publishRegistrationComplete(user,
                request.getLocale(),
                request.getServerName() + ":" + request.getServerPort());
        return new ResponseEntity<>(userToken, HttpStatus.CREATED);

    }

    @ApiOperation(value = "check if username or email free", response = boolean.class)
    @RequestMapping(value = "/registration/checkIfCredentialAvailable", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isCredentialAvailable(@RequestParam("field") String fieldName, @RequestParam("value") String value) {
        switch (fieldName) {
            case "user_name":
                return new ResponseEntity<>(userService.isUsernameFree(value), HttpStatus.OK);
            case "email":
                return new ResponseEntity<>(userService.isEmailFree(value), HttpStatus.OK);
            default:
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        }
    }

    @ApiOperation(value = "authorize or register user",
            notes = "Authorizes or registers a user using the model network token",
            response = SerializedToken.class
    )
    @RequestMapping(value = "oauth/socialAuth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody SocAuthDTO snTokenDTO) {
        ResponseEntity responseEntity = null;
        SocialNetwork sn = SocialNetwork.valueOf(snTokenDTO.getSocialNetwork().toUpperCase(Locale.ENGLISH));
        String token = (sn == SocialNetwork.FACEBOOK ? snTokenDTO.getAccessToken() : snTokenDTO.getIdToken());
        String refreshToken = snTokenDTO.getRefreshToken();
        AuthResult authResult = userService.authFlow(token, refreshToken, sn);
        OAuth2AccessToken userToken = userService.getUserToken(authResult.getUser());
        switch (authResult.getResultType()) {
            case REGISTRED:
                responseEntity = new ResponseEntity<>(userToken, HttpStatus.CREATED);
                break;
            case FOUND:
                responseEntity = new ResponseEntity<>(userToken, HttpStatus.OK);
                break;
        }
        return responseEntity;
    }


    @RequestMapping(value = "/registration/emailConfirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, HttpServletResponse response, @RequestParam("token") String token) {

        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            return message;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = messageSource.getMessage("auth.message.expired", null, locale);
            return message;
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        try {
            response.sendRedirect("http://geosportfishing.top/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
