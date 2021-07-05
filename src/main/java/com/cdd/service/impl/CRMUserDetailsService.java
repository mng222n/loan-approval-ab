package com.cdd.service.impl;

import com.cdd.constants.RoleEnum;
import com.cdd.dao.UserDAO;
import com.cdd.model.User;
import com.cdd.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CRMUserDetailsService implements UserDetailsService {
    @Value("${crm.oauth.super-admin.username}")
    String superAdminUsername;

    @Value("${crm.oauth.super-admin.password}")
    String superAdminPassword;

    @Autowired
    UserDAO userDAO;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = RequestUtils.getCurrentRequest();
        String clientId = (String) request.getAttribute("client-id");
        logger.info("Loading user with username: \"{}\" and client id: \"{}\"", username, clientId);
        if (clientId == null) {
            if (username.equals(superAdminUsername)) {
                return getSuperAdminDetails();
            }
        }

        UserDetails user = userDAO.findByEmailAndClientIdOrNull(username, clientId);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", username));
        }
        return user;
    }

    private UserDetails getSuperAdminDetails() {
        User superAdmin = new User();
        superAdmin.setRole(RoleEnum.SUPERADMIN);
        superAdmin.setEmail(superAdminUsername);
        superAdmin.setPassword(superAdminPassword);
        return superAdmin;
    }
}
