package com.orwellg.bifrost.login.services;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface OauthService {

    OAuth2AccessToken getTokenWithClientCredentialsGrant(final String user, final String credentials);

}
