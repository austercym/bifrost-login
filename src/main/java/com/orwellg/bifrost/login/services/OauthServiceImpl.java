package com.orwellg.bifrost.login.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class OauthServiceImpl implements OauthService{



    private final String TOKEN_ENDPOINT="/as/token.oauth2";
    @Value("${authServer}")
    private String authServer;

    /**
     * Composes the headeres required for the token endpoint
     *
     * @return HttpHeaders
     */
    private HttpHeaders composeTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Accept", "application/json");
        return headers;
    }

    @Override
    public OAuth2AccessToken getTokenWithClientCredentialsGrant(final String user, final String credentials) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientSecret(credentials);
        details.setClientId(user);
        details.setAccessTokenUri(authServer + TOKEN_ENDPOINT);


        AccessTokenRequest request = new DefaultAccessTokenRequest();

        HttpHeaders requestHeaders = composeTokenHeaders();
        request.setHeaders(requestHeaders);
        DefaultOAuth2ClientContext ctx = new DefaultOAuth2ClientContext(request);
        OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(details, ctx);
        final OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();
        return token;

    }
}
