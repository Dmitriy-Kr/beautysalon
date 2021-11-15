package com.beautysalon.dto;

import com.beautysalon.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterClientDto extends BaseDto{
    String login;
    String password;
    String clientName;
    String clientSurname;
    RoleEnum role;
}
