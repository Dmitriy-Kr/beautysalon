package com.beautysalon.security;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SecureUser {
    private String login;
    private SecureRole role;
}
