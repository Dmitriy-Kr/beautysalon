package com.beautysalon.security;

import com.beautysalon.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SecureUser {
    private long accountId;
    private String login;
    private RoleEnum role;
}
