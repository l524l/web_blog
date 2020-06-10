package com.l524l.web_blog.models.enumes;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,GOD,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
