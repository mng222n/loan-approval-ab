package com.cdd.config;

import com.cdd.utils.CustomJwtAccessTokenConverter;
import com.cdd.utils.CustomTokenEnhancer;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Oauth2BeanConfig {
    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("jwt-key");
        return new JWKSet(builder.build());
    }

    @Bean
    public KeyPair keyPair() {
        ClassPathResource ksFile = new ClassPathResource("cs-systems.jks");
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, "cs-systems".toCharArray());
        KeyPair keyPair = ksFactory.getKeyPair("cs-systems-jwt");
        return keyPair;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("kid", "jwt-key");
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter(keyPair(), customHeaders);
        return converter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    DataSource dataSource;

    @Bean
    AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnhancer();
    }
}
