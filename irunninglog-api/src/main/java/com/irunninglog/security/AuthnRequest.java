package com.irunninglog.security;

import com.irunninglog.service.Endpoint;

public final class AuthnRequest {

    private String username;
    private String password;
    private Endpoint endpoint;

    public String getUsername() {
        return username;
    }

    public AuthnRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthnRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public AuthnRequest setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

}
