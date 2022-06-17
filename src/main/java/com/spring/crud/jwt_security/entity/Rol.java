package com.spring.crud.jwt_security.entity;

import com.spring.crud.jwt_security.enums.RolName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolName name;

    public Rol() {
    }

    public Rol(@NotNull  RolName rolName) {
        this.name = rolName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolName getRolName() {
        return name;
    }

    public void setRolName(RolName rolName) {
        this.name = rolName;
    }
}
