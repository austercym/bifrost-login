package com.orwellg.bifrost.login.services;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;

import java.util.Arrays;

import static com.orwellg.bifrost.login.configuration.LoginConfig.keyStore;

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

        oAuthRestTemplate.setRequestFactory(getRequestFactory());
        oAuthRestTemplate.setAccessTokenProvider(getAccessTokenProvider());

        final OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();
        return token;

    }


    private HttpComponentsClientHttpRequestFactory getRequestFactory(){
        char[] password = "Tempo.99".toCharArray();

        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(keyStore("bifrost-truststore.jks", password), password)
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return  new HttpComponentsClientHttpRequestFactory(client);
    }

    private AccessTokenProvider getAccessTokenProvider() {
        AuthorizationCodeAccessTokenProvider authorizationCodeAccessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        authorizationCodeAccessTokenProvider.setRequestFactory(getRequestFactory());
        ImplicitAccessTokenProvider implicitAccessTokenProvider = new ImplicitAccessTokenProvider();
        implicitAccessTokenProvider.setRequestFactory(getRequestFactory());
        ResourceOwnerPasswordAccessTokenProvider resourceOwnerPasswordAccessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
        resourceOwnerPasswordAccessTokenProvider.setRequestFactory(getRequestFactory());
        ClientCredentialsAccessTokenProvider clientCredentialsAccessTokenProvider = new ClientCredentialsAccessTokenProvider();
        clientCredentialsAccessTokenProvider.setRequestFactory(getRequestFactory());
        AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(
                authorizationCodeAccessTokenProvider
                , implicitAccessTokenProvider,
                resourceOwnerPasswordAccessTokenProvider, clientCredentialsAccessTokenProvider));

        return accessTokenProvider;
    }
}
