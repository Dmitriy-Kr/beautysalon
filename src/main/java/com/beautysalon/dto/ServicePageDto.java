package com.beautysalon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ServicePageDto extends BaseDto{
    long serviceId;
    String serviceName;
    Double price;
    long employeeId;
    String employeeName;
    String employeeSurname;
    Double employeeRating;
}
