package com.beautysalon.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Client extends BaseEntity{
    private String name;
    private String surname;
    private Account account;

    public Client setId(long Id) {
        super.setId(Id);
        return this;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", account=" + account +
                "} " + super.toString();
    }
}
