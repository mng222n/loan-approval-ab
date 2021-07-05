package com.cdd.utils;

import com.cdd.dao.CustomerDAO;
import com.cdd.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class RequestUtils {
    private static CustomerDAO customerDAO;

    @Autowired
    RequestUtils(CustomerDAO customerDAO) {
        RequestUtils.customerDAO = customerDAO;
    }

    public static HttpServletRequest getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static String getRequestClientId() {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getOAuth2Request().getClientId();
    }

    public static Customer getRequestCustomer() {
        return customerDAO.getCustomerByClientId(getRequestClientId());
    }
}
