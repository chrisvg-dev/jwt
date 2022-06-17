package com.spring.crud.jwt_security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    private String username;
    @NotBlank
    private String pwd;
}
