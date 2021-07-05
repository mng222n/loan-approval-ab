package com.cdd.service.impl;

import com.cdd.dao.OAuthClientDetailsDAO;
import com.cdd.model.OAuthClientDetails;
import com.cdd.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class CRMClientDetailsService implements ClientDetailsService {
    @Value("${crm.oauth.default-client.frontend.client-id}")
    private String defaultFEClientId;

    @Value("${crm.oauth.default-client.backend.client-id}")
    private String defaultBEClientId;

    @Value("${crm.oauth.default-client.backend.client-secret}")
    private String defaultBEClientSecret;

    @Value("${crm.oauth.default-client.redirect-uri}")
    private String redirectUri;

    private Map<String, ClientDetails> defaultClients = new HashMap<>();

    @Autowired
    OAuthClientDetailsDAO oAuthClientDetailsDAO;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (defaultClients.containsKey(clientId)) {
            // if client id match default client, return it
            return defaultClients.get(clientId);
        } // else, find from DB

        OAuthClientDetails client = oAuthClientDetailsDAO.findByClientId(clientId);
        if (client == null) {
            throw new ClientRegistrationException("Unable to find client id: " + clientId);
        }
        HttpServletRequest request = RequestUtils.getCurrentRequest();
        request.setAttribute("client-id", clientId);
        return client;
    }

    @PostConstruct
    private void initDefaultClient() {
        // Client details for FE
        OAuthClientDetails defaultFEClient = new OAuthClientDetails();
        defaultFEClient.setClientId(defaultFEClientId);
        defaultFEClient.setClientSecret("");
        defaultFEClient.setRedirectUri(redirectUri);
        defaultFEClient.setAuthorizedGrantTypes("authorization_code", "refresh_token", "password");

        // Client details for BE
        OAuthClientDetails defaultBEClient = new OAuthClientDetails();
        defaultBEClient.setClientId(defaultBEClientId);
        defaultBEClient.setClientSecret(defaultBEClientSecret);
        defaultBEClient.setAuthorizedGrantTypes("client_credentials");

        // Add clients to map
        defaultClients.put(defaultFEClientId, defaultFEClient);
        defaultClients.put(defaultBEClientId, defaultBEClient);
    }
}
