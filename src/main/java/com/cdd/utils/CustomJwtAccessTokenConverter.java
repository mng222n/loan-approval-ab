package com.cdd.utils;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
    private JsonParser objectMapper = JsonParserFactory.create();
    private KeyPair keyPair;
    private Map<String, String> customHeaders;
    private Signer signer;

    public CustomJwtAccessTokenConverter(KeyPair keyPair, Map<String, String> customHeaders) {
        super();
        super.setKeyPair(keyPair);
        this.customHeaders = customHeaders;
        this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(this.getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }


        String token = JwtHelper.encode(content, this.signer, this.customHeaders).getEncoded();
        return token;
    }
}
