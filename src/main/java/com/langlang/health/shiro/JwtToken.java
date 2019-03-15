package com.langlang.health.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by tyj on 2019/03/07.
 *  token
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
