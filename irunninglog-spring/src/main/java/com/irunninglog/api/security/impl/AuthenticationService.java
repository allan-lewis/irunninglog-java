package com.irunninglog.api.security.impl;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationService implements IAuthenticationService {

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(IUserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User authenticate(String username, String password) throws AuthnException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if (userEntity == null) {
            throw new AuthnException("User not found: " + username);
        } else if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new AuthnException("Passwords don't match!");
        } else {
            List<String> authorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList());

            String[] array = authorities.toArray(new String[authorities.size()]);
            return new User(userEntity.getId(), userEntity.getUsername(), array);
        }
    }

}