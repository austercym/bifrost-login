package com.orwellg.bifrost.login.controllers;

import com.orwellg.bifrost.login.model.Principal;
import com.orwellg.bifrost.login.services.OauthService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    OauthService oAuthService;

    @PermitAll
    @PostMapping("bifrost/login")
    @RequestMapping(consumes = "application/json")
    @ApiOperation(
            value = "Login",
            produces = "application/json")
    public ResponseEntity<?> postLogin(@RequestBody Principal usr) {

        log.info("Login attempt for user: {}", usr.getUsr());

        final OAuth2AccessToken token = oAuthService.getTokenWithClientCredentialsGrant(usr.getUsr(), usr.getPwd());
        log.info("token requested for user: {}, received: {}", usr.getUsr(), token != null);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<Object> authResponse = new ResponseEntity<>(token, headers, HttpStatus.OK);
        return authResponse;
    }

}
