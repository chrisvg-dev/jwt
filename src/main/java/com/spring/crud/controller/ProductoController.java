package com.spring.crud.controller;

import com.spring.crud.dto.Mensaje;
import com.spring.crud.dto.ProductoDto;
import com.spring.crud.entity.Producto;
import com.spring.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/list")
    public ResponseEntity<List<Producto>> list() {
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id")  int id) {
        if (!productoService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }
        Producto p = productoService.getOne(id).get();
        return new ResponseEntity(p, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByName(@PathVariable("nombre")  String nombre) {
        if (!productoService.existsByNombre(nombre)) {
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        } else {
            Producto p = productoService.getByName(nombre).get();
            return new ResponseEntity(p, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto) {
        if (StringUtils.isBlank(productoDto.getNombre())) {
            return new ResponseEntity("El nombre es Obligatorio", HttpStatus.BAD_REQUEST);
        }

        if (productoDto.getPrecio() < 0) {
            return new ResponseEntity("El precio debe ser mayor que 0", HttpStatus.BAD_REQUEST);
        }

        if (productoService.existsByNombre(productoDto.getNombre())) {
            return new ResponseEntity("El nombre ya existe", HttpStatus.BAD_REQUEST);
        }
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductoDto productoDto) {

        if(!productoService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El registro no existe"), HttpStatus.NOT_FOUND);
        }

        if (productoService.existsByNombre(productoDto.getNombre()) && productoService.getByName(productoDto.getNombre()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(productoDto.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es Obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (productoDto.getPrecio() < 0) {
            return new ResponseEntity(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        }

        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Producto> delete(@PathVariable("id")  int id) {
        if (!productoService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        } else {
            productoService.delete(id);
            return new ResponseEntity(new Mensaje("Registro eliminado"), HttpStatus.OK);
        }
    }
}
