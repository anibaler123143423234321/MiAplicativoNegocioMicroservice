package com.dagnerchuman.miaplicativonegociomicroservice.entity;

public class Producto {

    private Long id;
    private String nombre;
    private String direccion;
    private String picture;
    private Double precio;
    private String fechaCreacion;
    private Long negocioId;

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Producto(Long id, String nombre, String direccion, String picture, Double precio, String fechaCreacion, Long negocioId) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.picture = picture;
        this.precio = precio;
        this.fechaCreacion = fechaCreacion;
        this.negocioId = negocioId;
    }

    // Getters y setters para cada atributo

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }



    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }
}
