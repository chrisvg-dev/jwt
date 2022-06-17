package com.spring.crud.util;

import com.spring.crud.jwt_security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

// @Component
public class CreateRoles implements CommandLineRunner {
    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        /**Rol rolAdmin = new Rol(RolName.ROL_ADMIN);
        Rol rolUser = new Rol(RolName.ROL_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);**/
    }
}
