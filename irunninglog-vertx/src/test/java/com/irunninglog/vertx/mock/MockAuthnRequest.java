package com.irunninglog.vertx.mock;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.security.IAuthnRequest;

public class MockAuthnRequest implements IAuthnRequest {

    private int offset;
    private String token;
    private Endpoint endpoint;
    private String path;

    @Override
    public IAuthnRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public IAuthnRequest setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public IAuthnRequest setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public IAuthnRequest setPath(String path) {
        this.path = path;
        return this;
    }

}
