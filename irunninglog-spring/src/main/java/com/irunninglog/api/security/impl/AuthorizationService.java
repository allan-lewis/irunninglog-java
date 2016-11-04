package com.irunninglog.api.security.impl;

import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.IAuthorizationService;
import com.irunninglog.api.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.text.MessageFormat;

@Service
@Transactional
public class AuthorizationService implements IAuthorizationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationService.class);
    private static final String PATTERN = "/profiles/{0}/**";

    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public User authorize(User user, String resource) throws AuthzException {
        if (hasRole(user, "MYPROFILE") && matcher.match(MessageFormat.format(PATTERN, user.getId()), resource)) {
            LOG.info("Match based on profile id {} {}", user.getUsername(), resource);
            return user;
        } else if (hasRole(user, "ADMIN")) {
            LOG.info("Match based on admin role {} {}", user.getUsername(), resource);
            return user;
        } else {
            LOG.error("No match for {} {}", user.getUsername(), resource);
            throw new AuthzException("Not authorized");
        }
    }

    private boolean hasRole(User user, String role) {
        for (String string : user.getAuthorities()) {
            if (string.equals(role)) {
                return true;
            }
        }

        return false;
    }

}