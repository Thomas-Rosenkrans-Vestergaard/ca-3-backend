package com.group3.ca3.logic.jwt;

public class AuthenticationContext {


    private Role role;
    private Long id;

    public AuthenticationContext( Role role, Long id) {

        this.role = role;
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
