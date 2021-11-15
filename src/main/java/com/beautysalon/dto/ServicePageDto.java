package com.beautysalon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ServicePageDto extends BaseDto{
    String ServiceName;
    Double price;
    String employeeName;
    String employeeSurname;
    Double employeeRating;
}
