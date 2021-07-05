package com.cdd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.cdd.constants.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OAuthClientDetails extends BaseEntity implements ClientDetails {
    private String clientId;
    private String clientSecret;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
    @Transient
    @JsonIgnore
    private transient String authorizedGrantTypes = "password,refresh_token,client_credentials";
    @Transient
    @JsonIgnore
    private transient Set<String> redirectUri;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonIgnore
    public void setRedirectUri(String... uris) {
        this.redirectUri = new HashSet<>(Arrays.asList(uris));
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
//        this.clientSecret = PasswordUtils.getEncryptedPassword(clientSecret);
        this.clientSecret = clientSecret;
    }

    public void setAuthorizedGrantTypes(String... grantTypes) {
        this.authorizedGrantTypes = Optional.ofNullable(grantTypes).map(list -> String.join(",", list)).orElse(null);
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    @JsonIgnore
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    @JsonIgnore
    public boolean isScoped() {
        return false;
    }

    @Override
    @JsonIgnore
    public Set<String> getScope() {
        return new HashSet<String>(Arrays.asList(new String[]{"read", "write"}));
    }

    @Override
    @JsonIgnore
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(
                Arrays.asList(
                        Optional.ofNullable(authorizedGrantTypes)
                                .map(grantTypes -> grantTypes.split(",")).orElse(new String[]{})
                )
        );
    }

    @Override
    @JsonIgnore
    public Set<String> getRegisteredRedirectUri() {
        return redirectUri;
    }

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + RoleEnum.SERVICE.name();
            }
        });
    }

    @Override
    @JsonIgnore
    public Integer getAccessTokenValiditySeconds() {
        return 3600; // 1 hour
    }

    @Override
    @JsonIgnore
    public Integer getRefreshTokenValiditySeconds() {
        return 3600 * 24 * 7; // 1 week
    }

    @Override
    @JsonIgnore
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
