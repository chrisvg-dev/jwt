package com.spring.crud.jwt_security.repository;

import com.spring.crud.jwt_security.entity.Rol;
import com.spring.crud.jwt_security.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByName(RolName rolname);
}
