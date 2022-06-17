package com.spring.crud.jwt_security.service;

import com.spring.crud.jwt_security.entity.Rol;
import com.spring.crud.jwt_security.enums.RolName;
import com.spring.crud.jwt_security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> findByRolName(RolName rol){
        return rolRepository.findByRolName(rol);
    }

    public void save(Rol rol) {
        rolRepository.save(rol);
    }
}
