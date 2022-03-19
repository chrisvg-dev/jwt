package com.spring.crud.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class ProductoDto  {

    @NotNull
    private String nombre;
    private float precio;

    public ProductoDto() {
    }

    public ProductoDto(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

}
