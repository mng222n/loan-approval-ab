package com.cdd.utils;

import com.cdd.model.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user_id", Optional.ofNullable(authentication.getUserAuthentication()).map(auth -> (User) auth.getPrincipal()).map(user -> user.getId()).orElse(null));
        additionalInfo.put("sub",  Optional.ofNullable(authentication.getUserAuthentication()).map(auth -> (User) auth.getPrincipal()).map(user -> user.getUsername()).orElse(authentication.getName()));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
