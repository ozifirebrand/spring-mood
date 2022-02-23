package com.phoenix.phoenix.data.dto;

import lombok.Data;

@Data
public class AppUserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
}
