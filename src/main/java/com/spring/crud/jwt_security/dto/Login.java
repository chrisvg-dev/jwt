package com.spring.crud.jwt_security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
