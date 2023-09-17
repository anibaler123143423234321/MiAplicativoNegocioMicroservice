package com.dagnerchuman.miaplicativonegociomicroservice.entity;

import java.time.LocalDateTime;

public class Negocio {
    private Long id;
    private String nombre;
    private String direccion;
    private String fechaCreacion; // Cambiado a String

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Sobrescribe el método toString para mostrar el nombre del negocio en el Spinner
    @Override
    public String toString() {
        return nombre;
    }
}
