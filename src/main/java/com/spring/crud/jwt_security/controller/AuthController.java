package com.spring.crud.jwt_security.controller;

import com.spring.crud.dto.Message;
import com.spring.crud.jwt_security.dto.JwtDto;
import com.spring.crud.jwt_security.dto.Login;
import com.spring.crud.jwt_security.dto.UserDto;
import com.spring.crud.jwt_security.entity.Rol;
import com.spring.crud.jwt_security.entity.User;
import com.spring.crud.jwt_security.enums.RolName;
import com.spring.crud.jwt_security.jwt.JwtProvider;
import com.spring.crud.jwt_security.service.RolService;
import com.spring.crud.jwt_security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RolService rolService;

    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService, RolService rolService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.rolService = rolService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> nuevo(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return validate( bindingResult );

        if (userService.existsByEmail(user.getLastName())) {
            return ResponseEntity.badRequest().body(new Message("Ese nombre ya existe"));
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("Ese email ya existe"));
        }
        User usuario = new User(
            user.getName(),
            user.getLastName(),
            user.getEmail(), 
            passwordEncoder.encode(user.getPassword()));
        Set<Rol> roles = new HashSet<>();

        roles.add(rolService.findByRolName(RolName.ROLE_USER).get());
        if (user.getRoles().contains("admin"))
            roles.add(rolService.findByRolName(RolName.ROLE_ADMIN).get());

        usuario.setRoles(roles);
        userService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Usuario guardado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return validate(bindingResult);

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginUsuario.getEmail(), loginUsuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.tokenRefresh(jwtDto);
        JwtDto jwt = new JwtDto(token);
        return ResponseEntity.ok(jwt);
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
