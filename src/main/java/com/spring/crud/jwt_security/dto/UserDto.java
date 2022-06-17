package com.spring.crud.jwt_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String pwd;
    private Set<String> roles = new HashSet<>();
}
